package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.XiBaseNode;
import com.ccsu.feng.test.domain.relation.XIBaseRelationship;
import com.ccsu.feng.test.repository.*;
import com.ccsu.feng.test.service.node.IXIBaseRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author admin
 * @create 2020-02-10-15:32
 */
@Service
@Slf4j
public class XIBaseRelationshipServiceImpl implements IXIBaseRelationshipService {

    @Autowired
    XIBaseRelationshipRepository xiBaseRelationshipRepository;
    @Autowired
    PersonNodeRepository personNodeRepository;

    @Autowired
    WeaponNodeRepository weaponNodeRepository;

    @Autowired
    DeedsNodeRepository deedsNodeRepository;

    @Autowired
    PlaceNodeRepository placeNodeRepository;




    @Override
    public XIBaseRelationship addRelationship(XIBaseRelationship relationship) {
        if (relationship == null) {
            return null;
        }
        XIBaseRelationship baseRelationship = xiBaseRelationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public XIBaseRelationship addRelationship(String name, XiBaseNode startNode, XiBaseNode endNode) {

        log.info("startNode->{},endNode->{} ", startNode, endNode);
        XIBaseRelationship relationship = new XIBaseRelationship(name, startNode, endNode);
        XIBaseRelationship baseRelationship = xiBaseRelationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public boolean deleteRelationshipById(Long id) {
        if (id == null) {
            return false;
        }
        xiBaseRelationshipRepository.deleteById(id);
        log.info("id->{} 删除成功", id);
        return true;
    }

    @Override
    public XIBaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName) {
        log.info("startName->{},endName->{}", startName, endName);
        List<XIBaseRelationship> ships = xiBaseRelationshipRepository.findRelationshipByStarNameAndEndName(name, startName, endName);
        log.info("查找关系为{}", ships);
        if (ships != null && ships.size() > 0) {
            return ships.get(0);
        }
        return null;
    }


    @Override
    public Optional<XIBaseRelationship> findRelationshipById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<XIBaseRelationship> baseRelationship = xiBaseRelationshipRepository.findById(id);
        if (!baseRelationship.isPresent()) {
            return Optional.empty();

        }
        log.info("id->{},查找结果为->{}", id, baseRelationship.get());
        return baseRelationship;
    }

    @Override
    public XIBaseRelationship findRelationshipByName(String name) {

        return xiBaseRelationshipRepository.getXIBaseRelationshipByName(name);
    }


    @Override
    public List<Map<String, String>> findNodeName(String nodeType, String type) {
        List<Map<String,String>> list =new ArrayList<>();
        if (nodeType.equals("personNode")){
            List<PersonNode> nodeName = personNodeRepository.getListPersonNodeByType(type);
            for(PersonNode personNode:nodeName){
                Map<String,String> map =new HashMap<>();
                map.put("value",personNode.getName());
                list.add(map);
            }
        }

        return list;
    }
}
