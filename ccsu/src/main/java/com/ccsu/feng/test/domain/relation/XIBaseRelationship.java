package com.ccsu.feng.test.domain.relation;

import com.ccsu.feng.test.domain.base.AllBaseEntity;
import com.ccsu.feng.test.domain.node.XiBaseNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDateTime;

/**
 * @author admin
 * @create 2020-02-23-15:11
 */
@Data
@AllArgsConstructor
@RelationshipEntity(type= RelationType.REF)
@ToString
public class XIBaseRelationship <S extends XiBaseNode, E extends XiBaseNode> extends AllBaseEntity {

    /**
     * 关系名称; 例如：徒弟、师傅等
     */
    @Property
    private String name;

    /**
     * 创建时间
     */
    @Property
    private String createTime;

    /**
     * 开始节点
     */
    @JsonIgnore
    @StartNode
    private S startNode;

    /**
     * 结束节点
     */
    @JsonIgnore
    @EndNode
    private E endNode;
    public XIBaseRelationship(String name,S startNode,E endNode){
        this.name =name;
        this.createTime= LocalDateTime.now().toString();
        this.startNode=startNode;
        this.endNode=endNode;
    }
}
