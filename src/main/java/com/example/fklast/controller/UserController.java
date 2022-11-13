package com.example.fklast.controller;

import com.example.fklast.domain.SmsCode;
import com.example.fklast.domain.User;
import com.example.fklast.service.SmsCodeService;
import com.example.fklast.service.UserService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 卢本伟牛逼
 */
@RestController
@RequestMapping("/user")
public class UserController
{

    @Resource
    private UserService userService;
    @Resource
    private SmsCodeService smsCodeService;


    /**
     * 注册，新增一个用户
     */
    @PostMapping ("/register")
    public Result userSave (@RequestParam String code, @RequestBody User user )
    {
        SmsCode smsCode = new SmsCode();
        smsCode.setMail(user.getEmail());
        smsCode.setCode(code);
        boolean checkCode = smsCodeService.checkCode(smsCode);
        if ( ! checkCode )
        {
            return new Result(false, "验证码错误");
        }
        if ( ! userService.checkUsername(user.getUsername()) )
        {
            return new Result(false, "昵称已被注册");
        }
        if ( ! userService.checkEmail(user.getEmail()) )
        {
            return new Result(false, "邮箱已被注册");
        }
        return new Result(userService.userRegister(user));
    }


    @PostMapping("/login")
    public Result userLogin( @RequestBody User user )
    {
        return userService.userLogin(user.getEmail(),user.getPassword());
    }

}
