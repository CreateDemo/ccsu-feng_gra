package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.vo.DeedsTreeVO;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author admin
 * @description 关系服务类接口
 * @create 2020-02-10-15:24
 */
public interface IBaseRelationshipService {

    /**
     * @param relationship
     * @return BaseRelationship
     * @description 添加关系
     */
    BaseRelationship addRelationship(BaseRelationship relationship);

    /**
     * 为两个节点添加指向关系，例如 name:徒弟，startNode:唐僧 ， endNode:孙悟空，
     * 那么意思是：唐僧的徒弟是孙悟空
     *
     * @param name
     * @param startNode 开始节点
     * @param endNode   结束节点
     * @return
     */
    BaseRelationship addRelationship(String name, BaseNode startNode, BaseNode endNode);

    /**
     * @param id
     * @return
     * @description 根据Id删除关系
     */
    boolean deleteRelationshipById(Long id);

    /**
     *
     * @param id
     * @return
     */
    Boolean updateRelationById(Long id ,String name);


    /**
     * @param name
     * @param startName
     * @param endName
     * @return
     * @description 获取关系 根据节点名称和关系名称
     */
    BaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName);

    /**
     * @param id
     * @return
     */
    Optional<BaseRelationship> findRelationshipById(Long id);


    /**
     *
     * @param nodeType  节点类型
     * @param type 类型，比如西游记
     * @return
     */

    List<Map<String,String>>  findNodeName(String nodeType, String type);


    /**
     * 无条件的分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<ListRelationVO> getListRelationByPage(int pageIndex, int pageSize,String type);

    /**
     *获取人物关系
     * @param type
     * @return
     */
    List<NodeRelationsListVO> getPersonNodRelationByType(String type);


    /**
     *获取武器关系
     * @param type
     * @return
     */
    List<NodeRelationsListVO> getWeaponNodRelationByType(String type);

    /**
     *
     * @param type
     * @return
     */
    List<NodeRelationsListVO>  getPlaceNodRelationByType(String type);

    /**
     *
     * @param name
     * @return
     */
    List<NodeRelationsListVO>  getPersonNodRelationByName(String name);


    List<NodeRelationsListVO>  getWeaponNodeRelationByName(String name);


    List<NodeRelationsListVO>  getDeedsNodRelationByName(String name);

    List<NodeRelationsListVO>  getPlaceNodRelationByName(String name);

    DeedsTreeVO getTreeDeedsNodRelationByName(String name);

}
