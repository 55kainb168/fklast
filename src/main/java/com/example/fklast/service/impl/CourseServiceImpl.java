package com.example.fklast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fklast.domain.Course;
import com.example.fklast.domain.Exam;
import com.example.fklast.domain.Video;
import com.example.fklast.dto.CourseDTO;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.dto.VideoDTO;
import com.example.fklast.mapper.CourseMapper;
import com.example.fklast.mapper.ExamMapper;
import com.example.fklast.mapper.VideoMapper;
import com.example.fklast.service.CourseService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_course】的数据库操作Service实现
 * @createDate 2023-03-08 10:19:43
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
        implements CourseService
{

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private ExamMapper examMapper;

    @Override
    public Result insertCourse ( Course course )
    {
        UserDTO userDTO = UserHolder.getUser();
        //TODO 检查id集合是否存在
        course.setCid(UUID.randomUUID().toString(true));
        course.setCUid(userDTO.getUid());
        course.setCUsername(userDTO.getUsername());
        course.setCDelete("1");
        course.setCState("待审核");
        course.setCreatTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        return new Result(courseMapper.insert(course) > 0, course, "");
    }

    @Override
    public Boolean checkCourse ( String cid, String state )
    {
        UserDTO userDTO = UserHolder.getUser();
        Course course = courseMapper.selectById(cid);
        course.setAuditUid(userDTO.getUid());
        course.setAuditUsername(userDTO.getUsername());
        course.setAuditTime(LocalDateTime.now());
        return courseMapper.updateById(course) > 0;
    }

    /**
     * 查询单个
     */
    @Override
    public Result findCourseById ( String cid )
    {
        Course course = courseMapper.selectById(cid);
        List<Video> videos = videoMapper.selectBatchIds(course.getVideoIds());
        List<VideoDTO> videoDTOS = BeanUtil.copyToList(videos, VideoDTO.class);
        List<Exam> exams = examMapper.selectBatchIds(course.getExamIds());
        CourseDTO courseDTO = BeanUtil.copyProperties(course, CourseDTO.class);
        courseDTO.setExams(exams);
        courseDTO.setVideos(videoDTOS);
        return new Result(true, courseDTO);
    }

    @Override
    public Result findCourseByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort, boolean flag )
    {
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<>();
        IPage<Course> page = new Page<>(currentPage, pageSize);
        lqw.eq(Course::getCDelete, "1");
        if ( ! flag )
        {
            lqw.eq(Course::getCState, "通过");
        }
        if ( Strings.isNotBlank(uid) )
        {
            lqw.eq(Course::getCUid, uid);
        }
        //查询条件
        if ( Strings.isNotBlank(keyWord) )
        {
            lqw.or();
            lqw.like(Strings.isNotBlank(keyWord), Course::getCourse, keyWord);
        }
        courseMapper.selectPage(page, lqw);
        //如果当前页码大于总页码，那么重新执行查询操作，使用最大页码值作为当前页码值
        if ( currentPage > page.getPages() )
        {
            page.setCurrent(page.getPages());
            courseMapper.selectPage(page, lqw);
        }
        for ( Course record : page.getRecords() )
        {
            if ( record.getCDelete().equals("1") )
            {
                record.setCDelete("未删除");
            }
            else
            {
                record.setCDelete("已删除");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        map.put("totalPage", page.getPages());
        map.put("total", page.getTotal());
        map.put("Courses", page.getRecords());
        return new Result(true, map);
    }


    /**
     * 更新课程信息
     */
    @Override
    public Boolean updateCourse ( Course course )
    {
        course.setUpdateTime(LocalDateTime.now());
        return courseMapper.updateById(course) > 0;
    }

}




