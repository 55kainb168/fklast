package com.example.fklast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 卢本伟牛逼
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable
{
    /**
     * 用户id
     */
    private String uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份
     */
    private String uIdentity;
    /**
     * 学院
     */
    private String uDepartment;
    /**
     * 状态（1正常，0封禁）
     */
    private Integer uStatus;
}
