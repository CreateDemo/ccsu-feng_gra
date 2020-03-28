package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.vo.DeedsVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author admin
 * @create 2020-02-11-21:43
 */

public interface IDeedsNodeService {

    /**
     * @param deedsNode
     * @return
     * @description 添加事迹节点
     */
    DeedsNode addDeedsNode(DeedsNode deedsNode);


    /**
     * @param name
     * @return
     * @description 根据名称获取事迹节点
     */
    DeedsNode getDeedsNodeByName(String name);


    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    Optional<DeedsNode> findDeedsNodeById(Long id);


    /**
     * @param name
     * @return
     * @description 根据名称添加事迹节点
     */
    DeedsNode addDeedsNodeByName(String name,String type);

    /**
     * @param id
     * @return
     * @description 根据ID删除事迹节点
     */
    boolean deleteDeedsNode(Long id);


    /**
     * 事迹添加人物  单向关系
     *
     * @param startName 事迹名称
     * @param names     人物名称
     */
    List<BaseRelationship> addDeedsPersonRelationship(String startName, Set<String> names,String type);



    /**
     * 事件添加地点  单向关系
     *
     * @param startName 事件名称
     * @param names     地点名称
     */
    List<BaseRelationship> addDeedsPlaceRelationship(String startName, Set<String> names,String type);


    /**
     * 无条件的分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<DeedsVO> getListDeedsNodeByPage(int pageIndex, int pageSize,String type);

    /**
     * 查询总条数
     *
     * @return
     */
    Long getDeedsNodeCount(String type);


    PageResult<DeedsVO> getListDeedsNodeByPageAndName(String name, int pageIndex, int pageSize,String type);

    List<String> getDeedsNodeByType(String type);
}
