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
 * {@code @TableName} fk_gap
 */
@TableName (value = "fk_gap")
@Data
public class Gap implements Serializable
{
    @Serial
    @TableField (exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主观题id
     */
    @TableId
    private String gid;
    /**
     * 跟随的视频id
     */
    private String vid;
    /**
     * 题目内容
     */
    @TableField (value = "gap_content")
    private String gapContent;
    /**
     * 主观题标签
     */
    @TableField (value = "gap_label")
    private String gapLabel;
    /**
     * 答案
     */
    @TableField (value = "gap_answer")
    private String gapAnswer;
    /**
     * 是否删除 （1正常，0删除）
     */
    @TableField (value = "gap_delete")
    @TableLogic (value = "1", delval = "0")
    private String gapDelete;
    /**
     * 创建时间
     */
    @TableField (value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField (value = "update_time")
    private LocalDateTime updateTime;

}