package com.ccsu.feng.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义防刷接口注解
 * @author admin
 * @create 2020-01-06-15:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    /**
     * 限制访问时间，单位为秒
     */
    int seconds();

    /**
     * 在访问时间内的最大访问次数
     */
    int maxCount();

    /**
     * 这个判断有两个含义：一 ：默认是判断登录限制次数，二：判断接口限制
     */
    boolean needLogin() default true;
}
