package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.relation.XIBaseRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-23-15:14
 */
@Repository
public interface XIBaseRelationshipRepository extends Neo4jRepository<XIBaseRelationship, Long> {
    @Query("MATCH p=(n:XiBaseNode)-[r:Ref]->(m:BaseNode) WHERE n.name={name}  RETURN r")
    XIBaseRelationship getXIBaseRelationshipByName(@Param("name") String name);


    @Query("MATCH p=(n:XiBaseNode)-[r:Ref]->(m:XiBaseNode) WHERE r.name={name}  and n.name={startName} and m.name={endName} RETURN r")
    List<XIBaseRelationship> findRelationshipByStarNameAndEndName(@Param("name") String name, @Param("startName") String startName, @Param("endName") String endName);


}
