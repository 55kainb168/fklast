package com.example.fklast.controller;

import com.example.fklast.domain.Video;
import com.example.fklast.service.VideoService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/video")
public class VideoController
{
    @Resource
    private VideoService videoService;


    /**
     * 添加视频
     */
    @PostMapping ("/insert")
    public Result videoInsert ( @RequestBody Video video )
    {
        return new Result(videoService.videoInsert(video));
    }

    /**
     * 逻辑删除视频
     */
    @DeleteMapping ("/delete/{vid}")
    public Result videoDelete ( @PathVariable String vid )
    {
        return new Result(videoService.videoDelete(vid));
    }


    /**
     * 分页展示选择题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getVideoByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @PathVariable (value = "uid", required = false) String uid, @RequestParam (required = false) String keyWord, @RequestParam (required = false) String sort )
    {
        return videoService.findVideoByPage(currentPage, pageSize, uid, keyWord, sort);
    }


    /**
     * 查询单个视频
     */
    @GetMapping ("/findOne/{vid}")
    public Result getVideoById ( @PathVariable String vid )
    {
        return videoService.findVideoById(vid);
    }


    /**
     * 审核员审核视频
     */
    @PutMapping ("/check/{vid}")
    public Result checkVideo ( @PathVariable String vid, @RequestParam String state )
    {
        return new Result(videoService.checkVideo(vid, state));
    }


    /**
     * 查询个人观看视频进度
     */
    @GetMapping ("/watch/{vid}")
    public Result findUserVideoWatch ( @PathVariable String vid )
    {
        return videoService.findUserVideoWatch(vid);
    }


    /**
     * 播放量增加
     */
    @GetMapping ("/videoCountUp/{vid}")
    public Result videoWatchCountUp ( @PathVariable String vid )
    {
        return new Result(videoService.videoWatchCountUp(vid));
    }


    /**
     * 封禁视频
     */
    @PutMapping ("/ban")
    public Boolean banVideo ( String vid )
    {
        return videoService.banVideo(vid);
    }


}
