package com.example.fklast.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.fklast.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements Serializable
{
    /**
     * 课程id
     */
    private String cid;

    /**
     * 课程名字
     */
    private String course;

    /**
     * 视频集合
     */
    private List<VideoDTO> videos;

    /**
     * 习题集合
     */
    private List<Exam> exams;

    /**
     * 上传人id
     */
    private String cUid;

    /**
     * 上传人用户名
     */
    private String cUsername;

    /**
     * 审核状态 (待审核，通过)
     */
    @TableField (value = "c_state")
    private String cState;

    /**
     * 是否删除 （1正常，0删除）
     */
    @TableField (value = "c_delete")
    private String cDelete;

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
