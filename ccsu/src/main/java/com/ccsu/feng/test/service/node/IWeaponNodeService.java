package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.domain.vo.WeaponVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-10-16:42
 */
public interface IWeaponNodeService {

    /**
     * @param weapon
     * @return
     * @description 添加武器节点
     */
    WeaponVO addWeapon(WeaponNode weapon);

    /**
     * @param name
     * @return
     * @description 根据名称添加武器节点
     */
    WeaponNode addWeaponByName(String name,String type);

    /**
     * @param name
     * @return
     * @description 根据名称获取武器节点
     */
    WeaponVO getWeaponNodeByName(String name);

    /**
     * @param id
     * @return
     * @description 删除武器节点
     */
    boolean deleteWeaponById(Long id);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Optional<WeaponNode> findWeaponNodeById(Long id);


    /**
     * 无条件的分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<WeaponVO> getListWeaponNodeByPage(int pageIndex, int pageSize,String type);


    /**
     * 查询总条数
     *
     * @return
     */
    Long getWeaponNodeCount(String type);


    PageResult<WeaponVO> getListWeaponNodeByPageAndName(String name, int pageIndex, int pageSize,String type);

    /**
     *
     * @param type
     * @return
     */
    List<String> getWeaponNodeByType(String type);

    List <NodeRelationsListVO> getWeaponNodeLikeByName(String name, String type);
}
