package com.example.fklast.config;

import com.example.fklast.filter.JwtLoginFilter;
import com.example.fklast.filter.VerifyFilter;
import com.example.fklast.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author 卢本伟牛逼
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * 懒加载，解决循环注入问题
     */
    @Resource
    @Lazy
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RsaKeyConfig rsa;

    @Bean
    public BCryptPasswordEncoder passwordEncoder ()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 指定认证用户的来源
     */
    public void configure ( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure ( HttpSecurity http ) throws Exception
    {
//        释放静态资源,指定资源拦截规则,指定自定义认证界面,指定退出认证配置,csrf配置
        http.csrf().disable()
                .formLogin()
                .usernameParameter("email")
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager(),rsa,stringRedisTemplate, userService))
                .addFilter(new VerifyFilter(super.authenticationManager(), rsa,stringRedisTemplate, userService))
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}