package com.ccsu.feng.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @create 2020-03-16-11:04
 */
@Data
@TableName("user_collection")
public class UserCollect {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer UserId;

    private String LinkUrl;

    private String title;

    private Date createTime;

    private Date updateTime;

}
