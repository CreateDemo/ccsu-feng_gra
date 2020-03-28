package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.WeaponVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import com.ccsu.feng.test.service.node.IWeaponNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-13-15:22
 */
@RestController
@RequestMapping("/page/weapon")
public class PageWeaponNodeController {
    @Autowired
    IPersonNodeService iPersonNodeService;

    @Autowired
    IBaseRelationshipService iBaseRelationshipService;

    @Autowired
    IWeaponNodeService iWeaponNodeService;


    @GetMapping("/getWeaponNodeByType")
    public Result<List<String>> getWeaponNodeByType(String type) {
        List<String> node = iWeaponNodeService.getWeaponNodeByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getWeaponNodRelationByType")
    public Result<List<NodeRelationsListVO>> getWeaponNodRelationByType(String type) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getWeaponNodRelationByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getWeaponNodRelationByName")
    public Result<List<NodeRelationsListVO>> getWeaponNodRelationByName(String name) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getWeaponNodeRelationByName(name);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getWeaponNodeLikeByName")
    public Result<List<NodeRelationsListVO>> getWeaponNodeLikeByName(String name, String relationType) {
        List<NodeRelationsListVO> node = iWeaponNodeService.getWeaponNodeLikeByName(name, relationType);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.NOT_DATA.getCode(), ResultEnum.NOT_DATA.getMsg());
        }
    }

}
