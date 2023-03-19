package com.example.fklast.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName fk_exam
 */
@TableName (value = "fk_exam")
@Data
public class Exam implements Serializable
{
    @Serial
    @TableField (exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 试卷id
     */
    @TableId
    private String eid;

    /**
     * 跟随的视频id·
     */
    private String vid;

    /**
     * 上传人id
     */
    @TableField (value = "e_uid")
    private String eUid;

    /**
     * 上传人用户名
     */
    @TableField (value = "e_username")
    private String eUsername;

    /**
     * 习题组标题
     */
    @TableField (value = "e_title")
    private String eTitle;

    /**
     * 课程名字
     */
    private String course;

    /**
     * 选择题数
     */
    @TableField (value = "option_count")
    private Integer optionCount;

    /**
     * 所有选择题的id
     */
    @TableField (value = "option_ids")
    private String optionIds;

    /**
     * 判断题数
     */
    @TableField (value = "judge_count")
    private Integer judgeCount;

    /**
     * 所有判断题目id
     */
    @TableField (value = "judge_ids")
    private String judgeIds;

    /**
     * 大题数量
     */
    @TableField (value = "gap_count")
    private Integer gapCount;

    /**
     * 所有大题id
     */
    @TableField (value = "gap_ids")
    private String gapIds;

    /**
     * 审核状态 (待审核，通过)
     */
    @TableField (value = "e_state")
    private String eState;

    /**
     * 是否删除 （1正常，0删除）
     */
    @TableField (value = "e_delete")
    @TableLogic (value = "1", delval = "0")
    private String eDelete;

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