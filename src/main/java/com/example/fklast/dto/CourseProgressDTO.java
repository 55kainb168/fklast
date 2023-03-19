package com.example.fklast.dto;

import com.example.fklast.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressDTO implements Serializable
{

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 学过的课程集合
     */
    private List<CourseDTO> courses;

    /**
     * 学过的视频集合
     */
    private List<VideoDTO> videos;

    /**
     * 做过的习题组集合
     */
    private List<Exam> exams;

    /**
     * 创建时间
     */
    private LocalDateTime creatTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
