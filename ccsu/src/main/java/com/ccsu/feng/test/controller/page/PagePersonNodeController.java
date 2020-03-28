package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-07-18:07
 */
@RestController
@RequestMapping("/page/personNode")
public class PagePersonNodeController {
    @Autowired
    IPersonNodeService iPersonNodeService;

    @Autowired
    IBaseRelationshipService iBaseRelationshipService;

    @GetMapping("/getPersonNodeByType")
    public Result<List<String>> getPersonNodeByType(String type) {
        List<String> node = iPersonNodeService.getPersonNodeByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPersonNodRelationByType")
    public Result<List<NodeRelationsListVO>> getPersonNodRelationByType(String type) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getPersonNodRelationByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPersonNodRelationByName")
    public Result<List<NodeRelationsListVO>> getPersonNodRelationByName(String name) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getPersonNodRelationByName(name);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPersonNodeByName")
    public Result<PersonVO> getPersonNodeByName(String name) {
        PersonVO node = iPersonNodeService.getPersonRelationByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPersonNodeLikeByName")
    public Result<List<NodeRelationsListVO>> getPersonNodeLikeByName(String name, String relationType) {
        List<NodeRelationsListVO> node = iPersonNodeService.getPersonNodeLikeByName(name, relationType);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.NOT_DATA.getCode(), ResultEnum.NOT_DATA.getMsg());
        }
    }


}
