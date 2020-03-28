package com.ccsu.feng.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author admin
 * @create 2020-03-22-15:01
 */
@Data
@TableName("deeds_detail")
public class DeedsDetail {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private  Integer id;
    private  Integer detailId;
    private  String name;
    private  String content;
    private Date createTime;
    private Date updateTime;
    private  int readNum;
    private String preDeedsUrl;
    private String nextDeedsUrl;
    private  String preDeedsName;
    private String nextDeedsName;
}
