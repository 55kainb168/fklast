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

    /**
     * 更新个人信息
     */
    @PutMapping ("/update")
    public Result userUpdate ( @RequestBody User user )
    {
        return new Result(userService.userInPersonalUpdate(user));
    }

    /**
     * 忘记密码
     */
    @PutMapping ("/forget")
    public Result forgetPassword ( @RequestParam String email, @RequestParam String password )
    {
        return userService.forgetPassword(email, password);
    }

    /**
     * 查询某个用户信息
     */
    @GetMapping ("/findOne/{uid}")
    public Result findOne ( @PathVariable String uid )
    {
        return userService.getUserInfo(uid);
    }

    @PostMapping ("/login")
    public Result userLogin ( @RequestParam String email, @RequestParam String password )
    {
        return userService.userLogin(email, password);
    }

    @PostMapping ("/admin/test")
    public Result adminTest ()
    {
        return new Result(true, "通过");
    }


    @PostMapping ("/user/test")
    public Result userTest ()
    {
        return new Result(true, "通过");
    }
}
