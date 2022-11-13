package com.example.fklast.utils.component;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author 卢本伟牛逼
 */
@Component
public class CodeUtil
{
    /**
     * 验证的工具类
     */
    @Cacheable (value = "smsCode", key = "#mail")
    public String get ( String mail )
    {
        return null;
    }

}
