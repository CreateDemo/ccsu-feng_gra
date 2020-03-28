package com.ccsu.feng.test.domain.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author admin
 * @create 2020-02-12-18:22
 */
@Data
public class DeedsRelationVO {


    /**
     * 事迹名称
     */
    private String startName;

    /**
     *
     */
    private Set<String>  names;

    private String type;
}
