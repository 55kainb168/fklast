package com.example.fklast.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.fklast.config.RsaKeyConfig;
import com.example.fklast.domain.Role;
import com.example.fklast.domain.User;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.service.UserService;
import com.example.fklast.utils.JwtUtils;
import com.example.fklast.utils.RedisConstants;
import com.example.fklast.utils.UserHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 卢本伟牛逼
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter
{

    private AuthenticationManager authenticationManager;
    private final RsaKeyConfig rsaKeyConfig;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public AuthenticationManager getAuthenticationManager ()
    {
        return authenticationManager;
    }

    @Override
    public void setAuthenticationManager ( AuthenticationManager authenticationManager )
    {
        this.authenticationManager = authenticationManager;
    }

    private final UserService userService;

    public JwtLoginFilter ( AuthenticationManager authenticationManager, RsaKeyConfig rsaKeyConfig, StringRedisTemplate stringRedisTemplate, UserService userService )
    {
        this.authenticationManager = authenticationManager;
        this.rsaKeyConfig = rsaKeyConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication ( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException
    {
        try
        {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        catch ( Exception e )
        {
            try
            {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<Object, Object> resultMap = new HashMap<>(16);
                resultMap.put("code",HttpServletResponse.SC_UNAUTHORIZED);
                resultMap.put("msg","用户名或密码错误");
                ServletOutputStream out = response.getOutputStream();
                OutputStreamWriter ow = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                ow.write(new ObjectMapper().writeValueAsString(resultMap));
                ow.flush();
                ow.close();
            }
            catch ( Exception ex )
            {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 认证成功回写token
     */
    @Override
    public void successfulAuthentication ( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult )
    {
        User user = new User();
        user.setUsername(authResult.getName());
        user.setRoles((List<Role>) authResult.getAuthorities());
        //一天的时间
        String token = JwtUtils.generateTokenExpireInMinutes(user, rsaKeyConfig.getPrivateKey());
        //将token写入redis缓存，需要查询一次数据库
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,user.getUsername());
        User selectOne = userService.getOne(lqw);
        UserDTO userDTO = BeanUtil.copyProperties(selectOne, UserDTO.class);
        userDTO.setRoles((List<Role>) authResult.getAuthorities());
        UserHolder.saveUser(userDTO);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(16),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor(( fieldName, fieldValue ) -> String.valueOf(fieldValue)));
        //存入redis并设置token有效期,登录后还需要更新token
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        response.addHeader("authorization", "Bearer " + token);
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        try
        {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = response.getWriter();
            Map<Object, Object> resultMap = new HashMap<>(16);
            resultMap.put("code", HttpServletResponse.SC_OK);
            resultMap.put("user", userDTO);
            resultMap.put("msg", "认证通过");
            writer.write(new ObjectMapper().writeValueAsString(resultMap));
            writer.flush();
            writer.close();
        }
        catch ( IOException ex )
        {
            throw new RuntimeException(ex);
        }
    }
}
