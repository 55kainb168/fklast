package com.example.fklast.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 卢本伟牛逼
 */
@Component
@Order (Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter
{

    public static final String OPTIONS = "OPTIONS";

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter ( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
    {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Max-Age", "3600");

        // 如果是OPTIONS则结束请求
        if ( OPTIONS.equals(( (HttpServletRequest) request ).getMethod()) )
        {
            response.getWriter().println("ok");
            return;
        }

        chain.doFilter(request, response);
    }

}
