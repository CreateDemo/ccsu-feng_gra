package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.PersonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-11-20:51
 */
@Data
public class PersonVO  {

    private Long id;
    private String alias;
    private String name;
    private String character;
    private String content;
    private String picture;
    private String type;
    private List<String> pictures=new ArrayList<>(1);

    public PersonVO(PersonNode personNode){
        this.setId(personNode.getId());
        this.setAlias(personNode.getAlias());
        this.setName(personNode.getName());
        this.setType(personNode.getType());
        this.setCharacter(personNode.getCharacter());
        this.setContent(personNode.getContent());
        this.setPicture(personNode.getPicture());
        this.pictures.add(personNode.getPicture());
    }
    public  PersonVO(){

    }

}
