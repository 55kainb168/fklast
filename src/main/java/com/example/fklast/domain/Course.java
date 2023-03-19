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
 * @TableName fk_course
 */
@TableName (value = "fk_course", autoResultMap = true)
@Data
public class Course implements Serializable
{
    @Serial
    @TableField (exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 课程id
     */
    @TableId
    private String cid;

    /**
     * 课程名字
     */
    private String course;

    /**
     * 视频id集合
     */
    @TableField (value = "video_ids", typeHandler = JacksonTypeHandler.class)
    private List<String> videoIds;

    /**
     * 习题id集合
     */
    @TableField (value = "exam_ids", typeHandler = JacksonTypeHandler.class)
    private List<String> examIds;

    /**
     * 上传人id
     */
    @TableField (value = "c_uid")
    private String cUid;

    /**
     * 上传人用户名
     */
    @TableField (value = "c_username")
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
     * 审核人id
     */
    @TableField (value = "audit_uid")
    private String auditUid;

    /**
     * 审核人用户名
     */
    @TableField (value = "audit_username")
    private String auditUsername;

    /**
     * 视频审核时间
     */
    @TableField (value = "audit_time")
    private LocalDateTime auditTime;

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

