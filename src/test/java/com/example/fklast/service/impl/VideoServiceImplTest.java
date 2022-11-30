package com.example.fklast.service.impl;

import com.example.fklast.domain.Video;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.service.VideoService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class VideoServiceImplTest
{

    @Resource
    private VideoService videoService;

    @Test
    void videoInsert ()
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid("f97d08813b6f4ee5afc01aaa8cbc1cc4");
        userDTO.setUsername("nihao");
        UserHolder.saveUser(userDTO);

        Video video = new Video();
        video.setVTitle("fuckingShit");
        video.setVDescription("fuck you");
        video.setVType("gay");
        video.setVCover("***");
        video.setVUrl("*****");

        System.out.println(videoService.videoInsert(video));
    }

    @Test
    void videoDelete ()
    {
        System.out.println(videoService.videoDelete("34555c19049247ff83c332e2ebb7aab3"));
    }

    @Test
    void findVideoByPage ()
    {
        Result page = videoService.findVideoByPage(10, 1, null, null, null);
        System.out.println(page.getData());
    }

    @Test
    void findVideoById ()
    {
        System.out.println(videoService.findVideoById("6db9665d6e7546398ecd0884ef33c432"));
    }
}