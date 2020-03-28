package com.ccsu.feng.test.repository;


import com.ccsu.feng.test.domain.base.BaseRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-15:17
 */
@Repository
public interface BaseRelationshipRepository extends Neo4jRepository<BaseRelationship, Long> {

    @Query("MATCH p=(n:BaseNode)-[r:Ref]->(m:BaseNode) WHERE n.name={name}  RETURN r")
    BaseRelationship getBaseRelationshipByName(@Param("name") String name);


    @Query("MATCH ()-[r:Ref]->() where id(r)={id}  set r.name={name} RETURN r ")
    void updateRelationById(@Param("id") Long id, @Param("name") String name);


    @Query("MATCH p=(n:BaseNode)-[r:Ref]->(m:BaseNode) WHERE r.name={name} and n.name={startName} and m.name={endName} RETURN r")
    List<BaseRelationship> findRelationshipByStarNameAndEndName(@Param("name") String name,
                                                                @Param("startName") String startName,
                                                                @Param("endName") String endName);

    @Query("MATCH  (n:BaseNode)-[r:Ref]->(m:BaseNode) where n.type={type} and m.type={type} RETURN r skip {pageIndex} limit {pageSize}")
    List<BaseRelationship> getListRelationshipByPage(@Param("pageIndex") int pageIndex,
                                                     @Param("pageSize") int pageSize,
                                                     @Param("type") String type);

    @Query("MATCH  (n:BaseNode)-[r:Ref]->(m:BaseNode) where n.type={type} and m.type={type} RETURN count(r)")
    Long getBaseRelationshipCount(@Param("type") String type);

    @Query("MATCH p=(n:PersonNode)-[r:Ref]->(m:PersonNode) where n.type={type} and m.type={type} RETURN r")
    List<BaseRelationship> getPersonNodRelationByType(@Param("type") String type);

}
