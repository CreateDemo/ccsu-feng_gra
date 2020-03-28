package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-12-18:02
 */
@RestController
@RequestMapping("/admin/relation")
public class RelationshipController {

    @Autowired
    private IBaseRelationshipService iBaseRelationshipService;


    @PostMapping("/addRelation")
    public Result<BaseRelationship> addRelation(@RequestBody BaseRelationship baseRelationship) {
        BaseRelationship node = iBaseRelationshipService.addRelationship(baseRelationship);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @PostMapping("/updateRelationById")
    public Result<Boolean> updateRelationById(@RequestParam("id") Long id, @RequestParam("name") String name) {
        Boolean node = iBaseRelationshipService.updateRelationById(id, name);
        if (node) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/findRelationById")
    public Result<Optional<BaseRelationship>> findRelationById(@Param("id") Long id) {
        Optional<BaseRelationship> node = iBaseRelationshipService.findRelationshipById(id);
        if (node.isPresent()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @DeleteMapping("/deleteRelationshipById")
    public Result<Boolean> deleteRelationshipById(@Param("id") Long id) {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), iBaseRelationshipService.deleteRelationshipById(id));
    }


    @GetMapping("/findNodeName")
    public Result<List<Map<String, String>>> findPersonNodeName(String nodeType, String type) {
        List<Map<String, String>> node = iBaseRelationshipService.findNodeName(nodeType, type);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getBaseRelationshipByPage")
    public Result<PageResult> getBaseRelationshipByPage(@Param("pageIndex") int pageIndex,
                                                        @Param("pageSize") int pageSize,
                                                        @Param("type") String type) {
        PageResult<ListRelationVO> listRelationByPage = iBaseRelationshipService.getListRelationByPage(pageIndex, pageSize, type);
        if (listRelationByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listRelationByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }
}
