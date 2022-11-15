package com.example.fklast.filter;

import cn.hutool.core.bean.BeanUtil;
import com.example.fklast.config.RsaKeyConfig;
import com.example.fklast.domain.Payload;
import com.example.fklast.domain.User;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.utils.JwtUtils;
import com.example.fklast.utils.RedisConstants;
import com.example.fklast.utils.UserHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 卢本伟牛逼
 */
public class VerifyFilter extends BasicAuthenticationFilter
{

    private final RsaKeyConfig rsa;

    private final StringRedisTemplate stringRedisTemplate;

    public VerifyFilter ( AuthenticationManager authenticationManager, RsaKeyConfig rsa, StringRedisTemplate stringRedisTemplate )
    {
        super(authenticationManager);
        this.rsa = rsa;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     *     判断请求头是否满足格式，以及判断token和解析token
     */
    @Override
    public void doFilterInternal ( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException
    {
        String header = request.getHeader("authorization");
        if ( header == null || !header.startsWith("Bearer ") )
        {
            //如果是错误token，则给用户提示
            chain.doFilter(request,response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map<String, Object> resultMap = new HashMap<>(16);
            resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("msg", "请登录！");
            out.write(new ObjectMapper().writeValueAsString(resultMap));
            out.flush();
            out.close();
        }
        else
        {
            String token = header.replace("Bearer ", "");
            //验证token是否正确
            Payload<User> payload = JwtUtils.getInfoFromToken(token, rsa.getPublicKey(), User.class);
            User user = payload.getUserInfo();
            if ( user!=null )
            {
                UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authResult);
                //追，刷新token时间
                String key = RedisConstants.LOGIN_USER_KEY + token;
                Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
                //将查询到的Hash数据转换为userDTO对象
                UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), true);
                //刷新token有效期
                stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
                //保存，放行
                UserHolder.saveUser(userDTO);
                chain.doFilter(request, response);
            }
        }
    }
}

