package com.ccsu.feng.test.domain.base;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * @author admin
 * @create 2020-02-09-14:35
 */
public  abstract  class AllBaseEntity {

    /**
     * id 必须为Long类型，而且必须提供(节点和关系都需要)。且要加这个注解。
     * id 由图数据库统一操作，所以不需要setter
     */
    @Id
    @GeneratedValue
    private Long id;


    public Long getId(){
        return id;
    }

    public void  setId(Long id){
        this.id=id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (this.id == null) {
            // For newly created entity, id will be null
            return false;
        }

        AllBaseEntity entity = (AllBaseEntity) obj;
        return this.id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }
}
