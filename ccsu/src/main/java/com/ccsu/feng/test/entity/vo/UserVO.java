package com.ccsu.feng.test.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author admin
 * @create 2020-02-29-15:01
 */
@Data
public class UserVO {
    /**
     * 名称
     */
    @NotNull(message = "名称不能为空")
    private String nickName;
    /**
     * 电话号码
     */
    @NotNull(message = "电话号码不能为空")
    private String phone;
    /**
     * 照片
     */
    private String picture;
    /**
     * 站内代表密码，站外
     */
    private String identifier;

    private String credential;
    /**
     * 登录类型
     */
    private String  loginType;

}
