/**
 * @Copyright (C) 2019 广州金鹏集团有限公司.
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 * @创建人: 赵力
 * @创建时间: 2019-01-23 10:58
 * @版本: V1.0
 */
package com.ccsu.feng.test.domain.base;


import com.ccsu.feng.test.domain.relation.RelationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDateTime;
@RelationshipEntity(type=RelationType.REF)
public class BaseRelationship<S extends BaseNode, E extends BaseNode> extends AllBaseEntity {

    @Property
    private String name; //关系名称

    @Property
    private String createTime; //创建时间

    @JsonIgnore
    @StartNode
    private S start;


    @JsonIgnore
    @EndNode
    private E end;

    private String type;

    public BaseRelationship(){
    }

    public BaseRelationship(String name,S start,E end,String type){
        this.name =name;
        this.createTime=LocalDateTime.now().toString();
        this.start=start;
        this.end=end;
        this.type=type;
    }

    @Override
    public String toString() {
        return "BaseRelationship{" +
                "name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public S getStart() {
        return start;
    }

    public void setStart(S start) {
        this.start = start;
    }

    public E getEnd() {
        return end;
    }

    public void setEnd(E end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
