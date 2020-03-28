package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.PersonNode;
import lombok.Data;

/**
 * @author admin
 * @create 2020-03-08-17:50
 */
@Data
public class NodeRelationsListVO {
    private String source;
    private String target;
    private String relationName;
}
