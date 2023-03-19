package com.example.fklast.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @TableName fk_course_progress
 */
@TableName (value = "fk_course_progress", autoResultMap = true)
@Data
public class CourseProgress implements Serializable
{
    @Serial
    @TableField (exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId
    private String uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 学过的课程集合
     */
    @TableField (value = "course_ids", typeHandler = JacksonTypeHandler.class)
    private List<String> courseIds;
    /**
     * 学过的视频集合
     */
    @TableField (value = "video_ids", typeHandler = JacksonTypeHandler.class)
    private List<String> videoIds;
    /**
     * 做过的习题组集合
     */
    @TableField (value = "exam_ids", typeHandler = JacksonTypeHandler.class)
    private List<String> examIds;
    /**
     * 创建时间
     */
    @TableField (value = "creat_time")
    private LocalDateTime creatTime;
    /**
     * 更新时间
     */
    @TableField (value = "update_time")
    private LocalDateTime updateTime;

}