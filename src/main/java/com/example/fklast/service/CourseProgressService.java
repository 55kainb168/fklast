package com.example.fklast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fklast.domain.CourseProgress;
import com.example.fklast.utils.Result;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_course_progress】的数据库操作Service
 * @createDate 2023-03-08 21:35:26
 */
public interface CourseProgressService extends IService<CourseProgress>
{

    /**
     * 新增记录
     */
    Result insertProgress ();


    /**
     * 更新信息
     */
    Boolean updateProgress ( String cid, String vid, String eid );

    /**
     * 查询记录
     */
    Result findProgressById ( int currentPage, int pageSize, String keyword );


}
