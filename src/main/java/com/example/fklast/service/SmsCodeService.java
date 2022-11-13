package com.example.fklast.service;

import com.example.fklast.domain.SmsCode;

/**
 * @author 卢本伟牛逼
 */
public interface SmsCodeService
{
    public String sendCodeToSms ( String mail);
    public boolean checkCode( SmsCode smsCode);
}
