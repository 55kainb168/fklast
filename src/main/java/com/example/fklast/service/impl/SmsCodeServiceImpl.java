package com.example.fklast.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.fklast.domain.SmsCode;
import com.example.fklast.service.SmsCodeService;
import com.example.fklast.utils.component.CodeUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 卢本伟牛逼
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService
{
    @Resource
    private CodeUtil codeUtil;

    /**
     *   放入缓存
     */
    @Override
    @CachePut (value = "smsCode", key = "#mail")
    public String sendCodeToSms ( String mail )
    {
        //用hutool工具包

        return RandomUtil.randomNumbers(6);
    }

    /**
     * 校验
     */
    @Override
    public boolean checkCode ( SmsCode smsCode )
    {
        //取出内存中的验证码与传递过来的验证码比对，如果相同，返回true
        String code = smsCode.getCode();
        String cacheCode = codeUtil.get(smsCode.getMail());
        return code.equals(cacheCode);
    }
}
