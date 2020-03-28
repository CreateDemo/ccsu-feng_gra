package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.base.BaseNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-23-13:32
 */
@Repository
public interface BaseNodeRepository extends Neo4jRepository<BaseNode,Long> {

    @Query("MATCH p=(n:BaseNode) where n.type={type} RETURN n")
    List<BaseNode> getBaseNodeName(@Param("type") String type);


    BaseNode getBaseNodeByName(String name);
}
