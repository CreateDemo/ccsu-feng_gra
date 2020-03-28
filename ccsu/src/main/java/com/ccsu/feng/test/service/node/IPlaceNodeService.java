package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.PlaceNode;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PlaceVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author admin
 * @create 2020-02-12-17:37
 */
public interface IPlaceNodeService {

    /**
     * @param placeNode
     * @return
     * @description 添加地点节点
     */
    PlaceNode addPlaceNode(PlaceNode placeNode);


    /**
     * @param name
     * @return
     * @description 根据名称添加地点节点
     */
    PlaceNode addPlaceNodeByName(String name,String type);


    /**
     * @param name
     * @return
     * @description 根据名称获取地点节点
     */
    PlaceNode getPlaceNodeByName(String name);


    /**
     * @param id
     * @return
     * @description 删除地点节点
     */
    boolean deletePlaceNodeById(Long id);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Optional<PlaceNode> findPlaceNodeById(Long id);

    /**
     * 地点添加事迹  单向关系
     *
     * @param startName 地点名称
     * @param names     事迹名称
     */
    List<BaseRelationship> addPlaceNodeDeedsNodeRelationship(String startName, Set<String> names,String type);

    /**
     * 无条件的分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<PlaceVO> getListPlaceNodeByPage(int pageIndex, int pageSize,String type);

    /**
     * 查询总条数
     *
     * @return
     */
    Long getPersonNodeCount(String type);


    PageResult<PlaceVO> getListPlaceNodeByPageAndName(String name, int pageIndex, int pageSize,String type);

    /**
     *
     * @param type
     * @return
     */
    List<String> getPlaceNodeByType(String type);


    List <NodeRelationsListVO> getPlaceNodeLikeByName(String name, String type);
}
