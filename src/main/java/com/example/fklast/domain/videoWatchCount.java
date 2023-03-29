package com.example.fklast.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class videoWatchCount implements Serializable
{
    /**
     * 视频id
     */
    @Id
    private String vid;
    /**
     * 用户id
     */
    private String vUid;
     /**
     * 观看数
     */
    private Integer videoCount;
}
