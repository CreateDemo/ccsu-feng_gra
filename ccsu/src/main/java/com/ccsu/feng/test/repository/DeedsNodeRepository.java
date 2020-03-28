package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.node.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 事迹 DTO
 *
 * @author admin
 * @create 2020-02-11-21:39
 */
@Repository
public interface DeedsNodeRepository extends Neo4jRepository<DeedsNode, Long> {
    DeedsNode getDeedsNodeByName(String name);

    @Query("MATCH (n:DeedsNode) where n.type={type} RETURN count(n)")
    Long getDeedsNodeCount(@Param("type") String type);


    @Query("MATCH p=(n:DeedsNode) where n.type={type} RETURN p")
    List<DeedsNode> getListDeedsNodeByType(@Param("type") String type);



    @Query("MATCH (n:DeedsNode)  where n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<DeedsNode> getListDeedsNodeByPage(@Param("pageIndex") int pageIndex,
                                             @Param("pageSize") int pageSize,
                                             @Param("type") String type);



    @Query("MATCH (n:DeedsNode)  where n.name Contains {name} and n.type={type} RETURN n skip {pageIndex} limit {pageSize}")
    List<DeedsNode> getListDeedsNodeByPageAndName(@Param("name") String name,
                                                  @Param("pageIndex") int pageIndex,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("type") String type);


    @Query("MATCH (n:DeedsNode) where n.type={type} and n.name Contains {name}  RETURN count(n)")
    Long getDeedsNodeCountByName(@Param("type")String type,@Param("name") String name);
}
