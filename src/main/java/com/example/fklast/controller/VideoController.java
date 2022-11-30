package com.example.fklast.controller;

import com.example.fklast.domain.Video;
import com.example.fklast.service.VideoService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/video")
public class VideoController
{
    @Resource
    private VideoService videoService;


    @PostMapping ("/insert")
    public Result videoInsert ( @RequestBody Video video )
    {
        return new Result(videoService.videoInsert(video));
    }

}
