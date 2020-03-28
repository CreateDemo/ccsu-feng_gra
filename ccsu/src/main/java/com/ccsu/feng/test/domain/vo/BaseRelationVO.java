package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.base.BaseNode;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-24-10:58
 */
@Data
public class BaseRelationVO {
    private Long id;

    private String name;
    private String alias;
    private String picture;
    private String content;
    private String origin;
    /**
     * 事件结果
     */
    private String result;

    /**
     * 事件的具体连接
     */
    private String url;
    private String character;
    private List<RelationVO> relationVOs;

    public BaseRelationVO(){

    }
    public BaseRelationVO(BaseNode baseNode){
           this.id=baseNode.getId();
           this.name=baseNode.getName();
           this.picture=baseNode.getPicture();
           this.content=baseNode.getContent();
    }


}
