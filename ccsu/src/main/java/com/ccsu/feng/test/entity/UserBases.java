package com.ccsu.feng.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @create 2020-02-29-15:19
 */
@Data
@TableName("user_bases")
public class UserBases {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private  Integer id;
    private  String  nickName;
    private  String picture;
    private Date createTime;
    private Date updateTime;
}
