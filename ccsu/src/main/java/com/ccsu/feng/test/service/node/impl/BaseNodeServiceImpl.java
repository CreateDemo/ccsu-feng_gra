package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.PlaceNode;
import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.vo.*;
import com.ccsu.feng.test.enums.LoginTime;
import com.ccsu.feng.test.repository.BaseNodeRepository;
import com.ccsu.feng.test.service.node.IBaseNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author admin
 * @create 2020-02-23-13:34
 */
@Service
@Slf4j
public class BaseNodeServiceImpl implements IBaseNodeService {
    @Autowired
    BaseNodeRepository baseNodeRepository;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Map<String, String>> findBaseNodeName(String nodeType,String type) {
        List<Map<String, String>> list = new ArrayList<>();
        List<BaseNode> baseNodeName = baseNodeRepository.getBaseNodeName(type);
        for (BaseNode baseNode : baseNodeName) {
            Map<String, String> map = new HashMap<>();
            map.put("value", baseNode.getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public BaseNode getBaseNodeByName(String name) {
        return baseNodeRepository.getBaseNodeByName(name);
    }


    @Override
    public BaseRelationVO findBaseRelationshipByName(String name) {
        BaseNode node = baseNodeRepository.getBaseNodeByName(name);
        BaseRelationVO baseRelationVO = new BaseRelationVO(node);
        List<RelationVO> relationVOs = new ArrayList<>();
        Set<BaseRelationship> relationships = node.getRelationships();
        if (relationships.size() > 0) {
            for (BaseRelationship baseRelationship : relationships) {
                RelationVO relationVO = new RelationVO();
                relationVO.setName(baseRelationship.getName());
                relationVO.setId(baseRelationship.getEnd().getId());
                relationVOs.add(relationVO);
            }
        }
        baseRelationVO.setRelationVOs(relationVOs);
        return baseRelationVO;
    }

    @Override
    public PageResult<ListRelationVO> findBaseRelationshipByNameByPage(String name, int pageIndex, int pageSize) {
        BaseNode node = baseNodeRepository.getBaseNodeByName(name);
        if (node==null){
            return null;
        }
        List<ListRelationVO> list = new ArrayList<>();
        Set<BaseRelationship> relationships = node.getRelationships();
        if (relationships.size() > 0) {
            for (BaseRelationship baseRelationship : relationships) {
                ListRelationVO listRelationVO = new ListRelationVO(baseRelationship);
                list.add(listRelationVO);
            }
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, (long) list.size(), list);
    }


    @Override
    public Object getShowBaseNodeByName(String name) {
        Object hget = redisUtil.hget("showBaseNode:", name);
        if (hget!=null){
            return hget;
        }
        Object object =null;
        BaseNode baseNodeByName = baseNodeRepository.getBaseNodeByName(name);
        if (baseNodeByName instanceof PersonNode){
            PersonVO personVO =new PersonVO((PersonNode) baseNodeByName);
            object=personVO;
        }
        if (baseNodeByName instanceof WeaponNode){
            WeaponVO weaponVO =new WeaponVO((WeaponNode) baseNodeByName);
            object=weaponVO;
        }

        if(baseNodeByName instanceof DeedsNode){
            DeedsVO deedsVO =new DeedsVO((DeedsNode) baseNodeByName);
            object = deedsVO;
        }

        if(baseNodeByName instanceof PlaceNode){
            PlaceVO placeVO =new PlaceVO((PlaceNode) baseNodeByName);
            object = placeVO;
        }
        redisUtil.hset("showBaseNode:",name,object, LoginTime.SAVE_LOGIN_TIME.getTime());
        return object;
    }

}
