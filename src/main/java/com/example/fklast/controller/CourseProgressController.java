package com.example.fklast.controller;


import com.example.fklast.service.CourseProgressService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/progress")
public class CourseProgressController
{

    @Resource
    private CourseProgressService courseProgressService;

    @PostMapping ("/insert")
    private Result insertCourseProgress ()
    {
        return courseProgressService.insertProgress();
    }

    /**
     * 更新
     */
    @PostMapping ("/update")
    private Result updateCourseProgress ( @RequestParam (required = false) String cid
            , @RequestParam (required = false) String vid, @RequestParam (required = false) String eid )
    {
        return new Result(courseProgressService.updateProgress(cid, vid, eid));
    }


    /**
     * 查询单个课程具体内容
     */
    @GetMapping ("/findOne/{currentPage}/{pageSize}")
    private Result getCourseById ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @RequestParam String keyword )
    {
        return courseProgressService.findProgressById(currentPage, pageSize, keyword);
    }
}
