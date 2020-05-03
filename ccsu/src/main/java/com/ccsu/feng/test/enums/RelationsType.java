package com.ccsu.feng.test.enums;

/**
 * @author admin
 * @create 2020-01-06-16:12
 */
public enum RelationsType {


    /**
     *
     */
     PERSON_TO_WEAPON("拥有"),
    /**
     * 武器属于
     */
    WEAPON_REF("属于"),

    /**
     * 地点 含有些事迹  一对多
     */
    PLACE_REF("含有"),

    /**
     * 事件包含哪些人物，一对多
     */
    DEEDS_REF_PERSON("涉及人员"),

    /**
     * 事件属于那个地点的。一对一
     */
    DEEDS_REF_PLACE("发生于");

    private String relation;

    RelationsType(String relation) {
        this.relation = relation;
    }

    public static String getRelation(RelationsType index) {
        for (RelationsType c : RelationsType.values()) {
            if (c == index) {
                return c.getRelation();
            }
        }
        return null;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
