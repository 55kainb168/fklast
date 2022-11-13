package com.example.fklast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 卢本伟牛逼
 */
@SpringBootApplication
@EnableCaching
@EnableWebSecurity
@EnableTransactionManagement
public class FklastApplication
{
    public static void main ( String[] args )
    {
        SpringApplication.run(FklastApplication.class, args);
    }

}
