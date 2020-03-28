package com.ccsu.feng.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @create 2020-02-29-15:23
 */
@Data
@TableName("user_auths")
public class UserAuths {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 标识符，如果是用户名或者手机注册，存储用户名和手机
     * 第三方 村唯一标识符
     */

    private String identifier;

    /**
     * 密码
     */
    private String credential;
    private Date createTime;
    private Date updateTime;
}
