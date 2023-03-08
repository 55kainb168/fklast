package com.example.fklast.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.example.fklast.config.RsaKeyConfig;
import com.example.fklast.domain.Payload;
import com.example.fklast.domain.User;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.utils.JwtUtils;
import com.example.fklast.utils.RedisConstants;
import com.example.fklast.utils.UserHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.fklast.filter.CorsFilter.OPTIONS;
import static com.example.fklast.utils.RedisConstants.LOGIN_USER_TTL;

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
     *     判断请求头是否满足格式，以及判断token和解析token
     */
    @Override
    public void doFilterInternal ( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException
    {
        String header = request.getHeader("authorization");
        if ( OPTIONS.equals(request.getMethod()) )
        {
            response.getWriter().println("ok");
            response.setStatus(HttpStatus.NO_CONTENT.value());
            chain.doFilter(request, response);
        }
        else if ( header == null || ! header.startsWith("Bearer ") )
        {
            //如果是错误token，则给用户提示
            wrongToken(request, response, chain);
        }
        else
        {
            String token = header.replace("Bearer ", "");
            //验证token是否正确
            String key = RedisConstants.LOGIN_USER_KEY + token;
            Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
            if ( CollUtil.isNotEmpty(userMap) )
            {
                Payload<User> payload = JwtUtils.getInfoFromToken(token, rsa.getPublicKey(), User.class);
                User user = payload.getUserInfo();
                UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authResult);
                //追，刷新token时间
                //将查询到的Hash数据转换为userDTO对象
                UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), true);
                //刷新token有效期
                stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
                //保存，放行
                UserHolder.saveUser(userDTO);
                chain.doFilter(request, response);
            }
            else
            {
                //如果是错误token，则给用户提示
                wrongToken(request, response, chain);
            }
        }
    }

    private void wrongToken ( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException
    {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
        resultMap.put("msg", "请重新登录！");
        ServletOutputStream out = response.getOutputStream();
        OutputStreamWriter ow = new
                OutputStreamWriter(out, StandardCharsets.UTF_8);
        ow.write(new ObjectMapper().writeValueAsString(resultMap));
        ow.flush();
        ow.close();
        chain.doFilter(request, response);
    }
}

