package com.example.fklast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fklast.domain.CourseProgress;
import com.example.fklast.domain.Exam;
import com.example.fklast.domain.Video;
import com.example.fklast.dto.CourseProgressDTO;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.dto.VideoDTO;
import com.example.fklast.mapper.CourseProgressMapper;
import com.example.fklast.mapper.ExamMapper;
import com.example.fklast.mapper.VideoMapper;
import com.example.fklast.service.CourseProgressService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_course_progress】的数据库操作Service实现
 * @createDate 2023-03-08 21:35:26
 */
@Service
public class CourseProgressServiceImpl extends ServiceImpl<CourseProgressMapper, CourseProgress>
        implements CourseProgressService
{


    @Resource
    private CourseProgressMapper courseProgressMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private ExamMapper examMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 新增记录
     */
    @Override
    public Result insertProgress ()
    {
        UserDTO userDTO = UserHolder.getUser();
        if ( ObjectUtil.isNotEmpty(courseProgressMapper.selectById(userDTO.getUid())) )
        {
            return new Result(true, "已存在记录");
        }
        CourseProgress courseProgress = new CourseProgress();
        courseProgress.setUid(userDTO.getUid());
        courseProgress.setUsername(userDTO.getUsername());
        courseProgress.setCreatTime(LocalDateTime.now());
        courseProgress.setUpdateTime(LocalDateTime.now());
        return new Result(courseProgressMapper.insert(courseProgress) > 0);
    }


    /**
     * 更新信息
     */
    @Override
    public Boolean updateProgress ( String cid, String vid, String eid )
    {
        UserDTO userDTO = UserHolder.getUser();
        CourseProgress courseProgress = courseProgressMapper.selectById(userDTO.getUid());
        if ( ObjectUtil.isEmpty(courseProgress) )
        {
            return false;
        }
        List<String> courseIds = courseProgress.getCourseIds();
        List<String> videoIds = courseProgress.getVideoIds();
        List<String> examIds = courseProgress.getExamIds();
        if ( Strings.isNotBlank(cid) )
        {
            if ( CollUtil.isEmpty(courseIds) )
            {
                courseIds = new ArrayList<>();
            }
            for ( String courseId : courseIds )
            {
                if ( courseId.equals(cid) )
                {
                    return true;
                }
            }
            courseIds.add(cid);
        }
        if ( Strings.isNotBlank(vid) )
        {
            if ( CollUtil.isEmpty(videoIds) )
            {
                videoIds = new ArrayList<>();
            }
            for ( String videoId : videoIds )
            {
                if ( videoId.equals(vid) )
                {
                    return true;
                }
            }
            videoIds.add(vid);
        }
        if ( Strings.isNotBlank(eid) )
        {
            if ( CollUtil.isEmpty(examIds) )
            {
                examIds = new ArrayList<>();
            }
            for ( String examId : examIds )
            {
                if ( examId.equals(eid) )
                {
                    return true;
                }
            }
            examIds.add(eid);
        }
        courseProgress.setCourseIds(courseIds);
        courseProgress.setVideoIds(videoIds);
        courseProgress.setExamIds(examIds);
        courseProgress.setUpdateTime(LocalDateTime.now());
        return courseProgressMapper.updateById(courseProgress) > 0;
    }


    /**
     * 查询记录
     */
    @Override
    public Result findProgressById ( int currentPage, int pageSize, String keyword )
    {
        UserDTO userDTO = UserHolder.getUser();
        Map<String, Object> map = new HashMap<>();
        CourseProgress courseProgress = courseProgressMapper.selectById(userDTO.getUid());
        if ( ObjectUtil.isEmpty(courseProgress) )
        {
            return new Result(false, "无记录");
        }
        CourseProgressDTO courseProgressDTO = BeanUtil.copyProperties(courseProgress, CourseProgressDTO.class);
        //分页
        if ( CollUtil.isNotEmpty(courseProgress.getVideoIds()) && keyword.equals("video") )
        {
            PageHelper.startPage(currentPage, pageSize);
            List<Video> videos = videoMapper.selectBatchIds(courseProgress.getVideoIds());
            PageInfo<Video> page = new PageInfo<>(videos);
            List<VideoDTO> videoDTOS = new ArrayList<>();
            VideoServiceImpl.insertVideoDTO(videos, videoDTOS, mongoTemplate);
            map.put("currentPage", currentPage);
            map.put("totalPage", page.getPages());
            map.put("total", page.getTotal());
            map.put("videos", videoDTOS);
        }
        if ( CollUtil.isNotEmpty(courseProgress.getExamIds()) && keyword.equals("exam") )
        {
            PageHelper.startPage(currentPage, pageSize);
            List<Exam> exams = examMapper.selectBatchIds(courseProgress.getExamIds());
            PageInfo<Exam> pageInfo = new PageInfo<>(exams);
            map.put("currentPage", currentPage);
            map.put("totalPage", pageInfo.getPages());
            map.put("total", pageInfo.getTotal());
            map.put("exams", exams);
        }
        return new Result(true, map);
    }


}




