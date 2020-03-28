package com.ccsu.feng.test.service.node;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.vo.BaseRelationVO;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @create 2020-02-23-13:33
 */
public interface IBaseNodeService {

    List<Map<String,String>>  findBaseNodeName(String nodeType,String type);

    /**
     * @param name
     * @return
     * @description 根据名称获取人物节点
     */
    BaseNode getBaseNodeByName(String name);


    /**
     *
     * @param name
     * @return
     */
    BaseRelationVO findBaseRelationshipByName(String name);


    PageResult<ListRelationVO> findBaseRelationshipByNameByPage(String name, int pageIndex, int pageSize);

    Object getShowBaseNodeByName(String name);
}
