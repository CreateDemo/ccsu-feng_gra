package com.ccsu.feng.test.domain.node;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.relation.RelationType;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.Index;

/**
 * @author admin
 * @create 2020-02-13-13:42
 */
@Data
@ToString
public class XiBaseNode extends BaseNode {

    @Index
    private  String type= RelationType.XI_REF;
}
