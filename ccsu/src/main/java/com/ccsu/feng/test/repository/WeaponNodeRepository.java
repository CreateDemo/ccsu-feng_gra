package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.PlaceNode;
import com.ccsu.feng.test.domain.node.WeaponNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-16:04
 */
@Repository
public interface WeaponNodeRepository extends Neo4jRepository<WeaponNode, Long> {




    WeaponNode getWeaponByName(String name);

    @Query("MATCH p=(n:WeaponNode) where n.type={type} RETURN p")
    List<WeaponNode> getListWeaponNodeByType(@Param("type") String type);



    @Query("MATCH (n:WeaponNode) where n.type={type}  RETURN count(n)")
    Long getWeaponNodeCount(@Param("type")String type);

    @Query("MATCH (n:WeaponNode)  where n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<WeaponNode> getListWeaponNodeByPage(@Param("pageIndex") int pageIndex,
                                           @Param("pageSize") int pageSize,
                                           @Param("type") String type);


    @Query("MATCH (n:WeaponNode)  where n.name Contains {name} and n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<WeaponNode> getListWeaponNodeByPageAndName(@Param("name") String name,
                                                  @Param("pageIndex") int pageIndex,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("type") String type);


    @Query("MATCH (n:WeaponNode) where n.type={type} and n.name Contains {name}  RETURN count(n)")
    Long getWeaponNodeCountByName(@Param("type")String type,@Param("name") String name);
}
