package com.example.fklast.controller;


import com.example.fklast.domain.Course;
import com.example.fklast.service.CourseService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/course")
public class CourseController
{
    @Resource
    private CourseService courseService;

    @PostMapping ("/insert")
    private Result insertCourse ( @RequestBody Course course )
    {
        return courseService.insertCourse(course);
    }

    /**
     * 分页
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getCourseByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize,
                                    @PathVariable (value = "uid", required = false) String uid,
                                    @RequestParam (required = false) String keyWord,
                                    @RequestParam (required = false) String sort,
                                    @RequestParam (required = false) boolean flag )
    {
        return courseService.findCourseByPage(currentPage, pageSize, uid, keyWord, sort, flag);
    }

    /**
     * 查询单个课程具体内容
     */
    @GetMapping ("/findOne/{cid}")
    private Result getCourseById ( @PathVariable String cid )
    {
        return courseService.findCourseById(cid);
    }

    /**
     * 更新
     */
    @PostMapping ("/update")
    private Result CourseUpdate ( @RequestBody Course course )
    {
        return new Result(courseService.updateCourse(course));
    }

}
