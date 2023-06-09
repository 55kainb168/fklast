package com.example.fklast.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 卢本伟牛逼
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{

    @Value ("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.patternPath}")
    private String patternPath;

    @Override
    public void addResourceHandlers ( ResourceHandlerRegistry registry )
    {
        registry.addResourceHandler(patternPath).addResourceLocations("file:" + uploadFolder);
    }

    @Override
    public void addCorsMappings ( CorsRegistry registry )
    {
        //添加映射路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
            //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
            .allowedOriginPatterns("*")
            //放行哪些请求方式
            .allowedMethods("OPTIONS", "POST", "PUT", "DELETE", "GET")
//                .allowedMethods("*") //或者放行全部
            //放行哪些原始请求头部信息
            .allowedHeaders("*")
            //暴露哪些原始请求头部信息
            .exposedHeaders("*");
    }

}
