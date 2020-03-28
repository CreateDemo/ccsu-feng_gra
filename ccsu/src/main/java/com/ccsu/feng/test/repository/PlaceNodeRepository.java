package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.PlaceNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地点 DTO
 *
 * @author admin
 * @create 2020-02-11-21:41
 */
@Repository
public interface PlaceNodeRepository extends Neo4jRepository<PlaceNode, Long> {
    PlaceNode getPlaceNodeByName(String name);

    @Query("MATCH p=(n:PlaceNode) where n.type={type} RETURN p")
    List<PlaceNode> getListPlaceNodeByType(@Param("type") String type);


    @Query("MATCH (n:PlaceNode) where n.type={type}  RETURN count(n)")
    Long getPlaceNodeCount(@Param("type")String type);

    @Query("MATCH (n:PlaceNode)  where n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<PlaceNode> getListPlaceNodeByPage(@Param("pageIndex") int pageIndex,
                                             @Param("pageSize") int pageSize,
                                             @Param("type") String type);


    @Query("MATCH (n:PlaceNode)  where n.name Contains {name} and n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<PlaceNode> getListPlaceNodeByPageAndName(@Param("name") String name,
                                                    @Param("pageIndex") int pageIndex,
                                                    @Param("pageSize") int pageSize,
                                                    @Param("type") String type);


    @Query("MATCH (n:PlaceNode) where n.type={type} and n.name Contains {name}  RETURN count(n)")
    Long getPlaceNodeCountByName(@Param("type")String type,@Param("name") String name);


    @Query("MATCH p=(n:PlaceNode)  where n.name Contains {name} and n.type={type} RETURN n ")
    List<PlaceNode> getPlaceNodeLikeByName(@Param("name") String name,
                                             @Param("type") String type);

}
