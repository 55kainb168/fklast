package com.example.fklast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO implements Serializable
{
    /**
     * 视频id
     */
    private String vid;

    /**
     * 视频标题
     */
    private String vTitle;

    /**
     * 视频描述（简介）
     */
    private String vDescription;

    /**
     * 视频分类（分区）
     */
    private String vType;

    /**
     * 视频标签
     */
    private String vLabel;

    /**
     * 视频封面（图片）
     */
    private String vCover;

    /**
     * 上传的视频地址url
     */
    private String vUrl;

    /**
     * 上传人的id
     */
    private String vUid;

    /**
     * 上传人用户名
     */
    private String vUsername;

    /**
     * 上传时间
     */
    private LocalDateTime creatTime;

    /**
     * 审核状态  0,：上传成功（待提交审核）,1：审核不通过，2：审核通过，3：发布，4：下架，5：删除
     */
    private String vState;

    /**
     * 是否删除 0：正常 1：删除
     */
    private String vDelete;

    /**
     * 审核人id
     */
    private String auditUid;

    /**
     * 审核人用户名
     */
    private String auditUsername;

    /**
     * 视频审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 观看数
     */
    private Integer videoCount;
}
