package com.example.fklast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
     * 观看数
     */
    private Integer videoCount;
    /**
     * 审核状态  0,：上传成功（待提交审核）,1：审核不通过，2：审核通过，3：发布，4：下架，5：删除
     */
    private String vState;
}
