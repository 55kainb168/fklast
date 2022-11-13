package com.example.fklast.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 卢本伟牛逼
 */
public class LoginInterceptor implements HandlerInterceptor
{
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
//        //判断是否需要拦截 ThreadLocal中是否有用户
//        if ( UserHolder.getUser() == null )
//        {
//            //没有，需要拦截
//            response.setStatus(401);
//            return false;
//        }
//        // 放行
        return true;
    }
}
