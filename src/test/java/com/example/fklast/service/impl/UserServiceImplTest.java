package com.example.fklast.service.impl;

import com.example.fklast.domain.User;
import com.example.fklast.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
@SpringBootTest
class UserServiceImplTest
{
    @Resource
    private UserService userService;

    @Test
    void userRegister ()
    {
        User user = new User();
        user.setUsername("lbwnb");
        user.setPassword("123");
        user.setGender("男");
        user.setEmail("1231dasdasd");
        user.setUDepartment("asdijoasiodj");
        user.setUIdentity("纯");
        System.out.println(userService.userRegister(user));


    }

    @Test
    void userLogin ()
    {
    }
}