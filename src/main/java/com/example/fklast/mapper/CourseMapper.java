package com.example.fklast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.fklast.domain.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_course】的数据库操作Mapper
 * @createDate 2023-03-08 10:19:43
 * @Entity com.example.fklast.domain.Course
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course>
{

}




