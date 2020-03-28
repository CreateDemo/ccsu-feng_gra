package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.domain.vo.DeedsTreeVO;
import com.ccsu.feng.test.domain.vo.NodeRelationsListVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IDeedsNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-15-15:23
 */
@RestController
@RequestMapping("/page/deeds")
public class PageDeedsNodeController {

    @Autowired
    IDeedsNodeService iDeedsNodeService;

    @Autowired
    IBaseRelationshipService iBaseRelationshipService;


    @GetMapping("/getDeedsNodeByType")
    public Result<List<String>> getDeedsNodeByType(String type) {
        List<String> node = iDeedsNodeService.getDeedsNodeByType(type);
        if (!ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getDeedsNodRelationByName")
    public Result<List<NodeRelationsListVO>> getDeedsNodRelationByName(String name) {
        List<NodeRelationsListVO> node = iBaseRelationshipService.getDeedsNodRelationByName(name);
        if ( !ListUtils.isEmpty(node)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getTreeDeedsNodRelationByName")
    public Result<DeedsTreeVO> getTreeDeedsNodRelationByName(String name) {
        DeedsTreeVO node = iBaseRelationshipService.getTreeDeedsNodRelationByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


}
