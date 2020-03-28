package com.ccsu.feng.test.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 * @create 2020-02-26-12:29
 */
@Data
@TableName("admin_user")
public class AdminUser implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "用户密码不能为空")
    private String password;
}
