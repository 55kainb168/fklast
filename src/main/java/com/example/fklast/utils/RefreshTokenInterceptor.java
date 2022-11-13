package com.example.fklast.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.example.fklast.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 卢本伟牛逼
 */
public class RefreshTokenInterceptor implements HandlerInterceptor
{
    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor ( StringRedisTemplate stringRedisTemplate )
    {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle ( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception
    {
        //获取请求头中的token
        String token = request.getHeader("authorization");
        if ( StrUtil.isBlank(token) )
        {
            return true;
        }
        String key = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);

        //将查询到的Hash数据转换为userDTO对象
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), true);
        //判断用户是否存在
        if ( userMap.isEmpty() )
        {
            return true;
        }
        //刷新token有效期
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //保存，放行
        UserHolder.saveUser(userDTO);
        return true;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion ( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) throws Exception
    {
        //移除用户
        UserHolder.removeUser();
    }
}