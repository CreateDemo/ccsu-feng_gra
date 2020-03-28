package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPlaceNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-16-19:41
 */
@RestController
@RequestMapping("/page/place")
public class PagePlaceNodeController {

    @Autowired
    IPlaceNodeService iPlaceNodeService;
    @Autowired
    IBaseRelationshipService iBaseRelationshipService;


    @GetMapping("/getPlaceNodeByType")
    public Result<List<String>> getPlaceNodeByType(String type) {
        List<String> node = iPlaceNodeService.getPlaceNodeByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPlaceNodRelationByType")
    public Result<List<NodeRelationsListVO>> getPlaceNodRelationByType(String type) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getPlaceNodRelationByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPlaceNodRelationByName")
    public Result<List<NodeRelationsListVO>> getPlaceNodRelationByName(String name) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getPlaceNodRelationByName(name);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPlaceNodeLikeByName")
    public Result<List<NodeRelationsListVO>> getPlaceNodeLikeByName(String name, String relationType) {
        List<NodeRelationsListVO> node = iPlaceNodeService.getPlaceNodeLikeByName(name, relationType);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.NOT_DATA.getCode(), ResultEnum.NOT_DATA.getMsg());
        }
    }

}
