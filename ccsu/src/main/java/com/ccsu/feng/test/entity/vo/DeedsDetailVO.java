package com.ccsu.feng.test.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @create 2020-03-23-11:05
 */
@Data
public class DeedsDetailVO {
    private  String name;
    private  String content;
    private  int readNum;
    private String preDeedsUrl;
    private String nextDeedsUrl;
    private  String preDeedsName;
    private String nextDeedsName;
    private String createTime;

}
