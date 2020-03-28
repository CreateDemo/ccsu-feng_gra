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

//
//    APPRENTICE("徒弟"),
//    MASTER("师傅"),
//    ELDER_BROTHER_WITH_THE_SAME_MASTER("师兄"),
//    YOUNGER_BROTHER_WITH_THE_SAME_MASTER("师弟"),
//    PAST_LIFE("前世"),
//    FATHER("父亲"),
//    MATHER("母亲"),
//    ELDER_SWORN_BROTHER("义兄"),
//    ELDER_BROTHER("兄长"),
//    YOUNGER_BROTHER("弟弟"),
//    YOUNGER_SISTER("妹妹"),
//    ELDER_SISTER("姐姐"),
//    HUSBAND("丈夫"),
//    WIFE("妻子"),
//    SON("儿子"),
//    SERVANT("仆人"),
//    MOUNT("坐骑"),
//    CONCUBINE("小妾"),
//    MINISTER("臣子"),
//    BROTHER_OF_THE_FATHER("叔叔"),
//    BROTHER_OF_THE_MATHER("舅舅");

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
