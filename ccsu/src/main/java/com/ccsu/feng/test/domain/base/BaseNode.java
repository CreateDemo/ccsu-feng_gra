package com.ccsu.feng.test.domain.base;


import com.ccsu.feng.test.domain.relation.RelationType;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * @author admin
 * @create 2020-02-10-15:03
 */


public class BaseNode extends AllBaseEntity {
    /**
     * 名称，基本上所有的Node都会有
     * 在此建立了唯一索引
     */
    @Index
    private String name;

    /**
     * 简介
     */
    private String content;
    /**
     * 图片
     */
    private String picture;

    /**
     * 关系列表
     */
    @Relationship(type = RelationType.REF)
    private Set<BaseRelationship> relationships;

    @Index
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BaseRelationship> name() {
        return relationships;
    }

    public Set<BaseRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(Set<BaseRelationship> relationships) {
        this.relationships = relationships;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
