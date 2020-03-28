package com.ccsu.feng.test.domain.node;

import com.ccsu.feng.test.domain.base.BaseNode;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 武器节点
 * @author admin
 * @create 2020-02-10-15:54
 */
@NodeEntity
@ToString
@Data
public class WeaponNode extends BaseNode {



    /**
     * 武器特点
     */
    private String character;

}
