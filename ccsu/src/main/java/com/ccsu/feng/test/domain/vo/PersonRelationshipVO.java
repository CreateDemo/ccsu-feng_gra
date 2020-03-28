package com.ccsu.feng.test.domain.vo;

import lombok.Data;

/**
 * @author admin
 * @create 2020-02-11-21:02
 */
@Data
public class PersonRelationshipVO {

    private String startName;

    private String preName; //关系名称

    private String sufName;

    private String endName;

    private String type;
}
