package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.LoginTime;
import com.ccsu.feng.test.enums.RelationsType;
import com.ccsu.feng.test.repository.PersonNodeRepository;
import com.ccsu.feng.test.repository.WeaponNodeRepository;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author admin
 * @create 2020-02-10-16:02
 */
@Service
@Slf4j
public class PersonNodeServiceImpl implements IPersonNodeService {

    @Autowired
    PersonNodeRepository personNodeRepository;

    @Autowired
    WeaponNodeRepository weaponNodeRepository;

    @Autowired
    IBaseRelationshipService relationshipService;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public PersonVO addPersonNode(PersonNode person) {
        PersonNode personNode = personNodeRepository.save(person);
        PersonVO personVo = new PersonVO(personNode);
        return personVo;
    }

    @Override
    public Optional<PersonNode> findPersonNodeById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<PersonNode> personNode = personNodeRepository.findById(id);
        if (!personNode.isPresent()) {
            return null;
        }
        log.info("id->{},查找结果为->{}", id, personNode.get());
        return personNode;
    }


    @Override
    public PersonNode addPersonNodeByName(String name, String type) {
        log.info("需要添加的人物名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        PersonNode person = new PersonNode();
        person.setName(name);
        person.setType(type);
        PersonNode personNode = personNodeRepository.save(person);

        return personNode;
    }


    @Override
    public PersonNode getPersonNodeByName(String name) {

        log.info("需要查找的名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        PersonNode personNode = personNodeRepository.getPersonNodeByName(name);

        return personNode;
    }

    @Override
    public PersonVO getPersonRelationByName(String name) {
        PersonNode personNode = personNodeRepository.getPersonNodeByName(name);
        if (personNode == null) {
            return null;
        }
        PersonVO personVO = new PersonVO(personNode);
        return personVO;
    }


    @Override
    public boolean deletePersonNode(Long id) {
        if (id == null) {
            return false;
        }
        personNodeRepository.deleteById(id);
        return true;
    }


    @Override
    public BaseRelationship addPersonNodeRelationship(String name, String startName, String endName, String type) {
        //获取人物节点
        PersonNode startPerson = getPersonNodeByName(startName);
        PersonNode endPerson = getPersonNodeByName(endName);

        /**
         * 添加关系有如下几种情况：
         * 1.人物节点全部存在:
         *   1.1关系存在
         *   1.2关系不存在
         * 2.开始人物节点存在，结束人物节点不存在
         * 3.开始人物节点不存在，结束人物节点存在
         * 4.人物节点均不存在
         */
        if (startPerson != null && endPerson != null) { // 1.人物节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
            if (relationship != null) {
                return relationship;
            }
        } else if (startPerson != null && endPerson == null) { //2.开始人物节点存在，结束人物节点不存在
            endPerson = addPersonNodeByName(endName, type);
        } else if (startPerson == null && endPerson != null) { //3.开始人物节点不存在，结束人物节点存在
            startPerson = addPersonNodeByName(startName, type);
        } else if (startPerson == null && endPerson == null) { //4.人物节点均不存在
            startPerson = addPersonNodeByName(startName, type);
            endPerson = addPersonNodeByName(endName, type);
        }
        BaseRelationship relationship = relationshipService.addRelationship(name, startPerson, endPerson);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startPerson.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startPerson.setRelationships(relationships);
        personNodeRepository.save(startPerson);
        return relationship;
    }


    @Override
    public List<BaseRelationship> addTwoPersonNodeRelationship(String preName, String sufName, String startName, String endName, String type) {
        List<BaseRelationship> list = new ArrayList<>();
        //顺向关系
        BaseRelationship baseRelationship = addPersonNodeRelationship(preName, startName, endName, type);
        //逆向关系
        BaseRelationship baseRelationship1 = addPersonNodeRelationship(sufName, endName, startName, type);
        list.add(baseRelationship);
        list.add(baseRelationship1);
        return list;
    }


    @Override
    public BaseRelationship getPersonNodeRelationship(String name, String startName, String endName) {
        return relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
    }


    @Override
    public List<BaseRelationship> addPersonNodeWeapon(String startName, String endName, String type) {
        List<BaseRelationship> list = new ArrayList<>();
        PersonNode startNode = getPersonNodeByName(startName);
        WeaponNode endNode = weaponNodeRepository.getWeaponByName(endName);

        /**
         * 添加关系有如下几种情况：
         * 1.节点全部存在:
         *   1.1关系存在
         *   1.2关系不存在
         * 2.开始节点存在，结束节点不存在
         * 3.开始节点不存在，结束节点存在
         * 4.节点均不存在
         */
        if (startNode != null && endNode != null) { // 1.节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(RelationsType.PERSON_TO_WEAPON.getRelation(), startName, endName);
            if (relationship != null) {
                list.add(relationship);
                list.add(addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName, type));
                return list;
            }
        } else if (startNode != null && endNode == null) { //2.开始节点存在，结束人物节点不存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            endNode = weaponNodeRepository.save(weapon);
        } else if (startNode == null && endNode != null) { //3.开始人物节点不存在，结束人物节点存在
            startNode = addPersonNodeByName(startName, type);
        } else if (startNode == null && endNode == null) { //4.人物节点均不存在
            startNode = addPersonNodeByName(startName, type);
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            endNode = weaponNodeRepository.save(weapon);
        }

        BaseRelationship relationship = relationshipService.addRelationship(RelationsType.PERSON_TO_WEAPON.getRelation(), startNode, endNode);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startNode.setRelationships(relationships);
        personNodeRepository.save(startNode);
        list.add(relationship);
        list.add(addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName, type));
        return list;
    }

    @Override
    public PageResult<PersonVO> getListPersonNodeByPage(int pageIndex, int pageSize, String type) {
        int pageIndexNum = 0;
        if (pageIndex > 1) {
            pageIndexNum = (pageIndex - 1) * pageSize;
        }

        List<PersonNode> page = personNodeRepository.getListPersonNodeByPage(pageIndexNum, pageSize, type);
        List<PersonVO> list = new ArrayList<>();
        for (PersonNode personNode : page) {
            list.add(new PersonVO(personNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, personNodeRepository.getPersonNodeCount(type), list);
    }

    @Override
    public Long getPersonNodeCount(String type) {
        return personNodeRepository.getPersonNodeCount(type);
    }

    @Override
    public PageResult<PersonVO> getListPersonNodeByPageAndName(String name, int pageIndex, int pageSize, String type) {
        int pageIndexNum = 0;
        if (pageIndex > 1) {
            pageIndexNum = (pageIndex - 1) * pageSize;
        }
        List<PersonNode> page = personNodeRepository.getListPersonNodeByPageAndName(name, pageIndexNum, pageSize, type);
        List<PersonVO> list = new ArrayList<>();
        for (PersonNode personNode : page) {
            list.add(new PersonVO(personNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, personNodeRepository.getPersonNodeCountByName(type, name), list);
    }

    @Override
    public List<String> getPersonNodeByType(String type) {
        List<String>  hget = (List<String>) redisUtil.hget("PersonNode:", type);
        if (!ListUtils.isEmpty(hget)){
            return  hget;
        }
        List<PersonNode> listPersonNodeByType = personNodeRepository.getListPersonNodeByType(type);
        if (ListUtils.isEmpty(listPersonNodeByType)) {
            return null;
        }
        List<String> strings = listPersonNodeByType.stream().map(x -> x.getName()).collect(Collectors.toList());
        redisUtil.hset("PersonNode:",type,strings, LoginTime.SAVE_LOGIN_TIME.getTime());
        return strings;
    }

    @Override
    public List<NodeRelationsListVO> getPersonNodeLikeByName(String name, String type) {
        List<PersonNode> personNodeLikeByName = personNodeRepository.getPersonNodeLikeByName(name, type);
        if (ListUtils.isEmpty(personNodeLikeByName)) {
            return null;
        }
        List<PersonNode> personNodeList = getLikeVO(personNodeLikeByName);
        List<NodeRelationsListVO> listVOS = new ArrayList<>();
        for (PersonNode personNode : personNodeList) {
            if (personNode.getRelationships() == null) {
                NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
                nodeRelationsListVO.setSource(personNode.getName());
                nodeRelationsListVO.setTarget(personNode.getName());
                listVOS.add(nodeRelationsListVO);
                continue;
            }
            Set<BaseRelationship> relationships = personNode.getRelationships();
            for (BaseRelationship baseRelationship : relationships) {
                if (baseRelationship.getEnd() instanceof PersonNode) {
                    NodeRelationsListVO personNodeRelations = new NodeRelationsListVO();
                    personNodeRelations.setRelationName(baseRelationship.getName());
                    personNodeRelations.setSource(personNode.getName());
                    personNodeRelations.setTarget(baseRelationship.getEnd().getName());
                    listVOS.add(personNodeRelations);
                }
            }
        }
        return listVOS;
    }

    private List<PersonNode> getLikeVO(List<PersonNode> list) {
        List<PersonNode> nodeList = new ArrayList<>(list.size());
        for (PersonNode node : list) {
            PersonNode personNodeByName = personNodeRepository.getPersonNodeByName(node.getName());
            nodeList.add(personNodeByName);
        }
        return nodeList;
    }


    private BaseRelationship addWeaponPerson(String name, String startName, String endName, String type) {
        WeaponNode startNode = weaponNodeRepository.getWeaponByName(startName);
        PersonNode endNode = getPersonNodeByName(endName);

        if (startNode != null && endNode != null) { // 1.节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
            if (relationship != null) {
                addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName, type);
                return relationship;
            }
        } else if (startNode != null && endNode == null) { //2.开始节点存在，结束人物节点不存在
            endNode = addPersonNodeByName(endName, type);
        } else if (startNode == null && endNode != null) { //3.开始人物节点不存在，结束人物节点存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            startNode = weaponNodeRepository.save(weapon);
        } else if (startNode == null && endNode == null) { //4.人物节点均不存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            startNode = weaponNodeRepository.save(weapon);
            endNode = addPersonNodeByName(startName, type);
        }

        BaseRelationship relationship = relationshipService.addRelationship(name, startNode, endNode);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startNode.setRelationships(relationships);
        weaponNodeRepository.save(startNode);
        return relationship;
    }


}
