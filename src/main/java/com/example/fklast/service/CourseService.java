package com.example.fklast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fklast.domain.Course;
import com.example.fklast.utils.Result;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_course】的数据库操作Service
 * @createDate 2023-03-08 10:19:43
 */
public interface CourseService extends IService<Course>
{

    /**
     * 添加课程
     */
    Result insertCourse ( Course course );

    /**
     * 审核
     */
    Boolean checkCourse ( String cid, String state );

    /**
     * 查询单个
     */
    Result findCourseById ( String cid );

    /**
     * 分页查询
     */
    Result findCourseByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort, boolean flag );


    /**
     * 更新课程信息
     */
    Boolean updateCourse ( Course course );


}
