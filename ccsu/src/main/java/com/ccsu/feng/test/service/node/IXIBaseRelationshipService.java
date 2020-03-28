package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.node.XiBaseNode;
import com.ccsu.feng.test.domain.relation.XIBaseRelationship;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author admin
 * @description 关系服务类接口
 * @create 2020-02-10-15:24
 */
public interface IXIBaseRelationshipService {

    /**
     * @param relationship
     * @return BaseRelationship
     * @description 添加关系
     */
    XIBaseRelationship addRelationship(XIBaseRelationship relationship);

    /**
     * 为两个节点添加指向关系，例如 name:徒弟，startNode:唐僧 ， endNode:孙悟空，
     * 那么意思是：唐僧的徒弟是孙悟空
     *
     * @param name
     * @param startNode 开始节点
     * @param endNode   结束节点
     * @return
     */
    XIBaseRelationship addRelationship(String name, XiBaseNode startNode, XiBaseNode endNode);

    /**
     * @param id
     * @return
     * @description 根据Id删除关系
     */
    boolean deleteRelationshipById(Long id);


    /**
     * @param name
     * @param startName
     * @param endName
     * @return
     * @description 获取关系 根据节点名称和关系名称
     */
    XIBaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName);

    /**
     * @param id
     * @return
     */
    Optional<XIBaseRelationship> findRelationshipById(Long id);

    /**
     *
     * @param name
     * @return
     */
    XIBaseRelationship findRelationshipByName(String name);



    /**
     *
     * @param nodeType  节点类型
     * @param type 类型，比如西游记
     * @return
     */

    List<Map<String,String>>  findNodeName(String nodeType, String type);
}
