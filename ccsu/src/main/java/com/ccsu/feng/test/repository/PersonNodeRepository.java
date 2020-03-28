package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-15:19
 */
@Repository
public interface PersonNodeRepository extends Neo4jRepository<PersonNode, Long> {


    PersonNode getPersonNodeByName(String name);


    @Query("MATCH p=(n:PersonNode) where n.type={type} RETURN p")
    List<PersonNode> getListPersonNodeByType(@Param("type") String type);


    @Query("MATCH (n:PersonNode) where n.type={type}  RETURN count(n)")
    Long getPersonNodeCount(@Param("type")String type);

    @Query("MATCH (n:PersonNode)  where n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<PersonNode> getListPersonNodeByPage(@Param("pageIndex") int pageIndex,
                                             @Param("pageSize") int pageSize,
                                             @Param("type") String type);


    @Query("MATCH (n:PersonNode)  where n.name Contains {name} and n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<PersonNode> getListPersonNodeByPageAndName(@Param("name") String name,
                                                    @Param("pageIndex") int pageIndex,
                                                    @Param("pageSize") int pageSize,
                                                    @Param("type") String type);


    @Query("MATCH (n:PersonNode) where n.type={type} and n.name Contains {name}  RETURN count(n)")
    Long getPersonNodeCountByName(@Param("type")String type,@Param("name") String name);

    @Query("MATCH p=(n:PersonNode)  where n.name Contains {name} and n.type={type} RETURN n ")
    List<PersonNode> getPersonNodeLikeByName(@Param("name") String name,
                                             @Param("type") String type);


}
