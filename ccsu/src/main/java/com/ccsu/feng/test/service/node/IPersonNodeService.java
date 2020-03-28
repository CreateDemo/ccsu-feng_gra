package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-10-15:48
 */
public interface IPersonNodeService {


    /**
     * @param person
     * @return PersonNode
     * @description 添加人物节点
     */
    PersonVO addPersonNode(PersonNode person);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Optional<PersonNode> findPersonNodeById(Long id);

    /**
     * @param name
     * @return
     * @description 根据名称添加人物
     */
    PersonNode addPersonNodeByName(String name,String type);


    /**
     * @param name
     * @return
     * @description 根据名称获取人物节点
     */
    PersonNode getPersonNodeByName(String name);

    PersonVO getPersonRelationByName(String name);

    /**
     * @param id
     * @return
     * @description 根据ID删除人物节点
     */
    boolean deletePersonNode(Long id);

    /**
     * @param name
     * @param startName
     * @param endName
     * @return
     * @description 添加人物关系  单向关系
     */
    BaseRelationship addPersonNodeRelationship(String name, String startName, String endName,String type);


    /**
     * 添加人物关系   双向关系
     *
     * @param preName
     * @param sufName
     * @param startName
     * @param endName
     * @return
     */
    List<BaseRelationship> addTwoPersonNodeRelationship(String preName, String sufName, String startName, String endName,String type);


    /**
     * @param name
     * @param startName
     * @param endName
     * @return
     * @description 获取人物关系
     */
    BaseRelationship getPersonNodeRelationship(String name, String startName, String endName);


    /**
     *
     * @param startName
     * @param endName
     * @return
     * @description 添加武器
     */
    List<BaseRelationship> addPersonNodeWeapon(String startName, String endName,String type);


    /**
     * 无条件的分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<PersonVO> getListPersonNodeByPage(int pageIndex, int pageSize,String type);


    /**
     * 查询总条数
     *
     * @return
     */
    Long getPersonNodeCount(String type);


    /**
     * 根据名称（模糊） 分页查询
     * @param name
     * @param pageIndex
     * @param pageSize
     * @param type
     * @return
     */
    PageResult<PersonVO> getListPersonNodeByPageAndName(String name, int pageIndex, int pageSize,String type);

    /**
     *
     * @param type
     * @return
     */
    List<String> getPersonNodeByType(String type);


    List <NodeRelationsListVO> getPersonNodeLikeByName(String name, String type);

}
