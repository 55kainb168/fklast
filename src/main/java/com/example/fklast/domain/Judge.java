package com.example.fklast.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName fk_judge
 */
@TableName (value = "fk_judge")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judge implements Serializable
{
    @Serial
    @TableField (exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 判断题id
     */
    @TableId
    private String jid;
    /**
     * 上传人id
     */
    private String uid;
    /**
     * 视频id
     */
    private String vid;
    /**
     * 题目内容
     */
    @TableField (value = "judge_content")
    private String judgeContent;
    /**
     * 答案
     */
    @TableField (value = "judge_answer")
    private String judgeAnswer;
    /**
     * 标签
     */
    @TableField (value = "judge_label")
    private String judgeLabel;
    /**
     * 是否删除 （1正常，0删除）
     */
    @TableField (value = "judge_delete")
    @TableLogic (value = "1", delval = "0")
    private String judgeDelete;
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