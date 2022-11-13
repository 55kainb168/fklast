package com.example.fklast.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author 卢本伟牛逼
 * 放置用户信息
 */
@Data
public class Payload<T>
{
    private String id;
    private T userInfo;
    private Date expiration;
}
