package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.PlaceNode;
import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.relation.RelationType;
import com.ccsu.feng.test.domain.vo.DeedsTreeVO;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.enums.LoginTime;
import com.ccsu.feng.test.enums.RelationsType;
import com.ccsu.feng.test.repository.*;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author admin
 * @create 2020-02-10-15:32
 */
@Service
@Slf4j
public class BaseRelationshipServiceImpl implements IBaseRelationshipService {

    @Autowired
    BaseRelationshipRepository relationshipRepository;
    @Autowired
    PersonNodeRepository personNodeRepository;

    @Autowired
    WeaponNodeRepository weaponNodeRepository;

    @Autowired
    DeedsNodeRepository deedsNodeRepository;

    @Autowired
    PlaceNodeRepository placeNodeRepository;

    @Autowired
    BaseNodeRepository baseNodeRepository;
    
    @Autowired
    RedisUtil redisUtil;

    @Override
    public BaseRelationship addRelationship(BaseRelationship relationship) {
        if (relationship == null) {
            return null;
        }
        BaseRelationship baseRelationship = relationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public Boolean updateRelationById(Long id, String name) {
        relationshipRepository.updateRelationById(id, name);
        return true;
    }

    @Override
    public BaseRelationship addRelationship(String name, BaseNode startNode, BaseNode endNode) {

        log.info("startNode->{},endNode->{} ", startNode, endNode);
        BaseRelationship relationship = new BaseRelationship(name, startNode, endNode, startNode.getType());
        BaseRelationship baseRelationship = relationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public boolean deleteRelationshipById(Long id) {
        if (id == null) {
            return false;
        }
        relationshipRepository.deleteById(id);
        log.info("id->{} 删除成功", id);
        return true;
    }


    @Override
    public BaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName) {
        log.info("startName->{},endName->{}", startName, endName);
        List<BaseRelationship> ships = relationshipRepository.findRelationshipByStarNameAndEndName(name, startName, endName);
        log.info("查找关系为{}", ships);
        if (ships != null && ships.size() > 0) {
            return ships.get(0);
        }
        return null;
    }

    @Override
    public Optional<BaseRelationship> findRelationshipById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<BaseRelationship> baseRelationship = relationshipRepository.findById(id);
        if (!baseRelationship.isPresent()) {
            return Optional.empty();

        }
        log.info("id->{},查找结果为->{}", id, baseRelationship.get());
        return baseRelationship;
    }


    @Override
    public List<Map<String, String>> findNodeName(String nodeType, String type) {
        List<Map<String, String>> list = new ArrayList<>();
        if ("personNode".equals(nodeType)) {
            List<PersonNode> nodeName = personNodeRepository.getListPersonNodeByType(type);
            for (PersonNode personNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", personNode.getName());
                list.add(map);
            }
        }
        if ("weaponNode".equals(nodeType)) {
            List<WeaponNode> nodeName = weaponNodeRepository.getListWeaponNodeByType(type);
            for (WeaponNode weaponNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", weaponNode.getName());
                list.add(map);
            }
        }
        if ("deedsNode".equals(nodeType)) {
            List<DeedsNode> nodeName = deedsNodeRepository.getListDeedsNodeByType(type);
            for (DeedsNode deedsNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", deedsNode.getName());
                list.add(map);
            }
        }

        if ("placeNode".equals(nodeType)) {
            List<PlaceNode> nodeName = placeNodeRepository.getListPlaceNodeByType(type);
            for (PlaceNode placeNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", placeNode.getName());
                list.add(map);
            }
        }
        return list;

    }

    @Override
    public PageResult<ListRelationVO> getListRelationByPage(int pageIndex, int pageSize, String type) {
        int pageIndexNum = 0;
        if (pageIndex > 1) {
            pageIndexNum = (pageIndex - 1) * pageSize;
        }
        Iterable<BaseRelationship> all = relationshipRepository.findAll();
        List<BaseRelationship> listRelationshipByPage = relationshipRepository.getListRelationshipByPage(pageIndexNum, pageSize, type);
        List<ListRelationVO> list = new ArrayList<>();
        for (BaseRelationship relationship : listRelationshipByPage) {
            ListRelationVO listRelationVO = new ListRelationVO(relationship);
            list.add(listRelationVO);
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, relationshipRepository.getBaseRelationshipCount(type), list);

    }

    @Override
    public List<NodeRelationsListVO> getPersonNodRelationByType(String type) {
        List<NodeRelationsListVO> listVOSRedis = (List<NodeRelationsListVO>) redisUtil.hget("PersonRelation:", type);
        if (!ListUtils.isEmpty(listVOSRedis)){
            return  listVOSRedis;
        }
        List<BaseRelationship> all = (List<BaseRelationship>) relationshipRepository.findAll();
        List<BaseRelationship> collect = all.stream().filter(x -> type.equals(x.getType()) &&
                (x.getStart() instanceof PersonNode) && ( x.getEnd() instanceof PersonNode))
                .collect(Collectors.toList());
        List<NodeRelationsListVO> listVOS =getResult(collect);
        redisUtil.hset("PersonRelation:",type,listVOS, LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }

    @Override
    public List<NodeRelationsListVO> getWeaponNodRelationByType(String type) {
        List<NodeRelationsListVO> listVOSRedis = (List<NodeRelationsListVO>) redisUtil.hget("WeaponRelation:", type);
        if (!ListUtils.isEmpty(listVOSRedis)){
            return  listVOSRedis;
        }
        List<BaseRelationship> all = (List<BaseRelationship>) relationshipRepository.findAll();
        List<BaseRelationship> collect = all.stream().filter(x -> type.equals(x.getType()) &&
                (x.getStart() instanceof WeaponNode))
                .collect(Collectors.toList());
        List<NodeRelationsListVO> listVOS =getResult(collect);
        redisUtil.hset("WeaponRelation:",type,listVOS, LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }


    @Override
    public List<NodeRelationsListVO> getPlaceNodRelationByType(String type) {
        List<NodeRelationsListVO> listVOSRedis = (List<NodeRelationsListVO>) redisUtil.hget("PlaceRelation:", type);
        if (!ListUtils.isEmpty(listVOSRedis)){
            return  listVOSRedis;
        }
        List<BaseRelationship> all = (List<BaseRelationship>) relationshipRepository.findAll();
        List<BaseRelationship> collect = all.stream().filter(x -> type.equals(x.getType()) &&
                (x.getStart() instanceof PlaceNode))
                .collect(Collectors.toList());
        List<NodeRelationsListVO> listVOS =getResult(collect);
        redisUtil.hset("PlaceRelation:",type,listVOS, LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }

    private List<NodeRelationsListVO> getResult( List<BaseRelationship> collect){
        List<NodeRelationsListVO> listVOS = new ArrayList<>(collect.size());
        collect.forEach(x -> {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setSource(x.getStart().getName());
            nodeRelationsListVO.setRelationName(x.getName());
            nodeRelationsListVO.setTarget(x.getEnd().getName());
            listVOS.add(nodeRelationsListVO);
        });
        return  listVOS;
    }

    @Override
    public List<NodeRelationsListVO> getPersonNodRelationByName(String name) {
        List<NodeRelationsListVO> listVOSR = (List<NodeRelationsListVO>) redisUtil.hget("PersonRealtionByName:", name);
        if (!ListUtils.isEmpty(listVOSR)){
            return  listVOSR;
        }
        PersonNode personNodeByName = personNodeRepository.getPersonNodeByName(name);
        List<NodeRelationsListVO> listVOS = new ArrayList<>();
        if (personNodeByName.getRelationships() == null) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setSource(personNodeByName.getName());
            nodeRelationsListVO.setTarget(personNodeByName.getName());
            listVOS.add(nodeRelationsListVO);
            return listVOS;
        }
        Set<BaseRelationship> relationships = personNodeByName.getRelationships();
        for (BaseRelationship baseRelationship : relationships) {
            if (baseRelationship.getEnd() instanceof PersonNode){
                NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
                nodeRelationsListVO.setRelationName(baseRelationship.getName());
                nodeRelationsListVO.setSource(personNodeByName.getName());
                nodeRelationsListVO.setTarget(baseRelationship.getEnd().getName());
                listVOS.add(nodeRelationsListVO);
            }

        }
        redisUtil.hset("PersonRealtionByName:",name,listVOS,LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }


    @Override
    public List<NodeRelationsListVO> getWeaponNodeRelationByName(String name) {
        List<NodeRelationsListVO> listVOSR = (List<NodeRelationsListVO>) redisUtil.hget("WeaponRealtionByName:", name);
        if (!ListUtils.isEmpty(listVOSR)){
            return  listVOSR;
        }
        WeaponNode weaponByName = weaponNodeRepository.getWeaponByName(name);
        List<NodeRelationsListVO> listVOS = new ArrayList<>();
        if (weaponByName.getRelationships() == null) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setSource(weaponByName.getName());
            nodeRelationsListVO.setTarget(weaponByName.getName());
            listVOS.add(nodeRelationsListVO);
            return listVOS;
        }
        Set<BaseRelationship> relationships = weaponByName.getRelationships();
        for (BaseRelationship baseRelationship : relationships) {
                NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
                nodeRelationsListVO.setRelationName(baseRelationship.getName());
                nodeRelationsListVO.setSource(weaponByName.getName());
                nodeRelationsListVO.setTarget(baseRelationship.getEnd().getName());
                listVOS.add(nodeRelationsListVO);
        }
        redisUtil.hset("WeaponRealtionByName:",name,listVOS,LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }

    @Override
    public List<NodeRelationsListVO> getPlaceNodRelationByName(String name) {
        List<NodeRelationsListVO> listVOSR = (List<NodeRelationsListVO>) redisUtil.hget("PlaceRealtionByName:", name);
        if (!ListUtils.isEmpty(listVOSR)){
            return  listVOSR;
        }
        PlaceNode placeNode = placeNodeRepository.getPlaceNodeByName(name);
        List<NodeRelationsListVO> listVOS = new ArrayList<>();
        if (placeNode.getRelationships() == null) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setSource(placeNode.getName());
            nodeRelationsListVO.setTarget(placeNode.getName());
            listVOS.add(nodeRelationsListVO);
            return listVOS;
        }
        Set<BaseRelationship> relationships = placeNode.getRelationships();
        for (BaseRelationship baseRelationship : relationships) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setRelationName(baseRelationship.getName());
            nodeRelationsListVO.setSource(placeNode.getName());
            nodeRelationsListVO.setTarget(baseRelationship.getEnd().getName());
            listVOS.add(nodeRelationsListVO);
        }
        redisUtil.hset("PlaceRealtionByName:",name,listVOS,LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }

    @Override
    public List<NodeRelationsListVO> getDeedsNodRelationByName(String name) {

        List<NodeRelationsListVO> hget = (List<NodeRelationsListVO> )redisUtil.hget("DeedsRelationByName:",name);
        if(!ListUtils.isEmpty(hget)){
            return hget;
        }
        DeedsNode deedsNodeByName = deedsNodeRepository.getDeedsNodeByName(name);
        List<NodeRelationsListVO> listVOS = new ArrayList<>();
        //事件起因
        NodeRelationsListVO nodeRelationsListVOOrigin = new NodeRelationsListVO();
        nodeRelationsListVOOrigin.setSource(deedsNodeByName.getName());
        nodeRelationsListVOOrigin.setRelationName("事件起因");
        nodeRelationsListVOOrigin.setTarget("起因："+deedsNodeByName.getOrigin());
        listVOS.add(nodeRelationsListVOOrigin);
        //事件结果
        NodeRelationsListVO nodeRelationsListVOResult = new NodeRelationsListVO();
        nodeRelationsListVOResult.setSource(deedsNodeByName.getName());
        nodeRelationsListVOResult.setRelationName("事件结果");
        nodeRelationsListVOResult.setTarget("结果："+deedsNodeByName.getResult());
        listVOS.add(nodeRelationsListVOResult);

        if (deedsNodeByName.getRelationships() == null) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setSource(deedsNodeByName.getName());
            nodeRelationsListVO.setTarget(deedsNodeByName.getName());
            listVOS.add(nodeRelationsListVO);
            return listVOS;
        }

        //涉及人员-发生地点
        Set<BaseRelationship> relationships = deedsNodeByName.getRelationships();
        for (BaseRelationship baseRelationship : relationships) {
            NodeRelationsListVO nodeRelationsListVO = new NodeRelationsListVO();
            nodeRelationsListVO.setRelationName(baseRelationship.getName());
            nodeRelationsListVO.setSource(deedsNodeByName.getName());
            nodeRelationsListVO.setTarget(baseRelationship.getEnd().getName());
            listVOS.add(nodeRelationsListVO);
        }

      redisUtil.hset("DeedsRelationByName:",name,listVOS,LoginTime.SAVE_LOGIN_TIME.getTime());
        return listVOS;
    }

    @Override
    public DeedsTreeVO getTreeDeedsNodRelationByName(String name) {
        DeedsTreeVO deedsTreeVO =(DeedsTreeVO) redisUtil.hget("DeedsTree:",name);
        if (deedsTreeVO!=null){
            return deedsTreeVO;
        }

        DeedsNode deedsNodeByName = deedsNodeRepository.getDeedsNodeByName(name);

        // 根节点
        DeedsTreeVO treeVO =new DeedsTreeVO();
        treeVO.setName(deedsNodeByName.getName());
        //事件起因
        DeedsTreeVO treeVO1 =new DeedsTreeVO();
        treeVO1.setName("事件起因");
        treeVO1.setParent(deedsNodeByName.getName());
        DeedsTreeVO treeVO2 =new DeedsTreeVO();
        treeVO2.setName(deedsNodeByName.getOrigin());
        treeVO2.setParent("事件起因");
        treeVO1.setChildren(new ArrayList<>(Arrays.asList(treeVO2)));

        //事件结果
        DeedsTreeVO treeVO3 =new DeedsTreeVO();
        treeVO3.setName("事件结果");
        treeVO3.setParent(deedsNodeByName.getName());
        DeedsTreeVO treeVO4 =new DeedsTreeVO();
        treeVO4.setName(deedsNodeByName.getResult());
        treeVO4.setParent("事件结果");
        treeVO3.setChildren(new ArrayList<>(Arrays.asList(treeVO4)));
        //加入根节点
        treeVO.add(treeVO1);
        treeVO.add(treeVO3);
        if (deedsNodeByName.getRelationships() == null) {
            treeVO.setName(deedsNodeByName.getName());
            return treeVO;
        }
        //涉及人员-发生地点

        Set<BaseRelationship> relationships = deedsNodeByName.getRelationships();

        DeedsTreeVO treeVO7 =new DeedsTreeVO();
        treeVO7.setName(RelationsType.DEEDS_REF_PERSON.getRelation());
        for (BaseRelationship baseRelationship : relationships) {
            //涉及人员
            if (baseRelationship.getName().equals(treeVO7.getName())){
                DeedsTreeVO treeVO5 =new DeedsTreeVO();
                treeVO5.setParent(treeVO7.getName());
                treeVO5.setName(baseRelationship.getEnd().getName());
                treeVO7.add(treeVO5);
            }else {
                DeedsTreeVO treeVO5 =new DeedsTreeVO();
                treeVO5.setParent(treeVO.getName());
                treeVO5.setName(baseRelationship.getName());

                DeedsTreeVO treeVO6 =new DeedsTreeVO();
                treeVO6.setName(baseRelationship.getEnd().getName());
                treeVO6.setParent(treeVO5.getName());
                treeVO5.add(treeVO6);
                treeVO.add(treeVO5);
            }

        }
        treeVO.add(treeVO7);
        redisUtil.hset("DeedsTree:",name,treeVO, LoginTime.SAVE_LOGIN_TIME.getTime());
        return treeVO;

    }

}
