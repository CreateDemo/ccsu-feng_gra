package com.ccsu.feng.test.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.ConstructorArgs;

/**
 * @Author xiaofeng
 * @Description //TODO
 * @Date 21:59 2020/5/5
 * @Param
 * @return
 **/
@Getter
@AllArgsConstructor
public enum  AccessLimitType {
    LOGIN_LIMIT_TYPE(1,"登录限制"),
    REGISTER_LIMIT_TYPE(2,"注册限制"),
    INTERFACE_LIMIT_TYPE(3,"接口限制");
    private final  Integer value;
    private final  String content;

}

