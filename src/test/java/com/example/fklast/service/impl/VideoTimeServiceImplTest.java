package com.example.fklast.service.impl;

import com.example.fklast.dto.UserDTO;
import com.example.fklast.service.VideoTimeService;
import com.example.fklast.utils.UserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class VideoTimeServiceImplTest
{

    @Resource
    private VideoTimeService videoTimeService;

    @Test
    void viewProgress ()
    {
    }

    @Test
    void viewProgressInsert ()
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid("f97d08813b6f4ee5afc01aaa8cbc1cc4");
        userDTO.setUsername("nihao");
        UserHolder.saveUser(userDTO);
        videoTimeService.viewProgressInsert("43ea7ecd032a44169926717666df0683", 99L);
    }

    @Test
    void viewProgressUpdate ()
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid("f97d08813b6f4ee5afc01aaa8cbc1cc4");
        userDTO.setUsername("nihao");
        UserHolder.saveUser(userDTO);
        videoTimeService.viewProgressUpdate("43ea7ecd032a44169926717666df0683", 77L);

    }
}