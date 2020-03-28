package com.ccsu.feng.test.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 * @create 2020-03-16-11:11
 */
@Data
public class UserCollectVO {

    @NotNull(message = "userId不能为空")
    private Integer UserId;
    @NotNull(message = "linkUrl不能为空")
    private String LinkUrl;

    private String title;
}
