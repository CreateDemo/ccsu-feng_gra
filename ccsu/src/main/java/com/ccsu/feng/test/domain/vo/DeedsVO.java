package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.DeedsNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-21-12:17
 */
@Data
public class DeedsVO  {
    private Long id;
    private String content;
    private String name;
    private String origin;
    private String url;
    private String result;
    private String picture;
    private String type;

    private List<String> pictures =new ArrayList<>(1);

    public DeedsVO(DeedsNode deedsNode){
        this.setId(deedsNode.getId());
        this.setContent(deedsNode.getContent());
        this.setName(deedsNode.getName());
        this.setOrigin(deedsNode.getOrigin());
        this.setType(deedsNode.getType());
        this.setUrl(deedsNode.getUrl());
        this.setResult(deedsNode.getResult());
        this.setPicture(deedsNode.getPicture());
        this.pictures.add(deedsNode.getPicture());
    }

    public DeedsVO(){

    }
}
