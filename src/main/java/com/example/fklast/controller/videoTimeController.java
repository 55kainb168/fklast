package com.example.fklast.controller;


import com.example.fklast.service.VideoTimeService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/videoTime")
public class videoTimeController
{
    @Resource
    private VideoTimeService videoTimeService;

    /**
     * 查询历史观看进度
     */
    @GetMapping ("/progress/{vid}")
    public Result videoProgress ( @PathVariable String vid )
    {
        return videoTimeService.viewProgress(vid);
    }


    /**
     * 新增历史观看记录
     */
    @PostMapping ("/insert/{vid}")
    public Result viewProgressInsert ( @PathVariable String vid, @RequestParam String time )
    {
        return new Result(videoTimeService.viewProgressInsert(vid, time));
    }


    /**
     * 修改历史观看记录
     */
    @PostMapping ("/update/{vid}")
    public Result viewProgressUpdate ( @PathVariable String vid, @RequestParam String time )
    {
        return new Result(videoTimeService.viewProgressUpdate(vid, time));
    }


}
