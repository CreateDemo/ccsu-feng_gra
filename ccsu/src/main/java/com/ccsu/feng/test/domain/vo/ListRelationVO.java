package com.ccsu.feng.test.domain.vo;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-24-21:15
 */
@Data
public class ListRelationVO {
    private Long id;
    /**
     * 关系名称
     */
    private String relationName;

    /**
     * 前驱节点名称
     */
    private String startName;

    private String startNamePicture;

    private List<String> startNamePictures = new ArrayList<>(1);
    private int startNameNumber;

    /**
     * 后驱节点名
     */
    private String endName;


    private String endNamePicture;

    private List<String> endNamePictures = new ArrayList<>(1);

    private int endNameNumber;


    public ListRelationVO(BaseRelationship baseRelationship) {
        this.id = baseRelationship.getId();
        this.relationName = baseRelationship.getName();
        this.startName = baseRelationship.getStart().getName();
        this.startNamePicture = baseRelationship.getStart().getPicture();
        this.startNamePictures.add(baseRelationship.getStart().getPicture());
        this.endName = baseRelationship.getEnd().getName();
        this.endNamePicture = baseRelationship.getEnd().getPicture();
        this.endNamePictures.add(baseRelationship.getEnd().getPicture());

        this.setStartNameNumber(baseRelationship.getStart().getRelationships()!=null?baseRelationship.getStart().getRelationships().size():0);
        this.setEndNameNumber(baseRelationship.getEnd().getRelationships()!=null?baseRelationship.getEnd().getRelationships().size():0);
    }
}
