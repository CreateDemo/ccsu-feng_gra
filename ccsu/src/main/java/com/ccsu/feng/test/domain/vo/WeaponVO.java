package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.node.WeaponNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-20-22:16
 */
@Data
public class WeaponVO{
    private Long id;
    private String name;
    private String picture;
    private String character;
    private String content;
    private String type;

    private List<String> pictures =new ArrayList<>(1);
    public WeaponVO(WeaponNode weaponNode){
        this.setId(weaponNode.getId());
        this.setName(weaponNode.getName());
        this.setType(weaponNode.getType());
        this.setPicture(weaponNode.getPicture());
        this.setCharacter(weaponNode.getCharacter());
        this.setContent(weaponNode.getContent());
        this.pictures.add(weaponNode.getPicture());
    }
    public WeaponVO(){

    }
}
