package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.PlaceNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-12-18:32
 */
@Data
public class PlaceVO{
    private Long id;
    private String name;
    private String picture;
    private String content;
    private String type;

    private List<String> pictures =new ArrayList<>(1);

    public PlaceVO(PlaceNode placeNode){
        this.setId(placeNode.getId());
        this.setName(placeNode.getName());
        this.setType(placeNode.getType());
        this.setPicture(placeNode.getPicture());
        this.setContent(placeNode.getContent());
        this.pictures.add(placeNode.getPicture());
    }
    public PlaceVO(){

    }
}
