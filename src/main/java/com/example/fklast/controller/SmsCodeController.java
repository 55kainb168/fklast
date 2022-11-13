package com.example.fklast.controller;

import com.example.fklast.domain.SmsCode;
import com.example.fklast.service.SmsCodeService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 卢本伟牛逼
 */
@RestController
@RequestMapping("/sms")
public class SmsCodeController
{
    @Resource
    private SmsCodeService smsCodeService;

    @PostMapping
    public Result checkCode ( SmsCode smsCode )
    {
        return new Result(smsCodeService.checkCode(smsCode));
    }
}
