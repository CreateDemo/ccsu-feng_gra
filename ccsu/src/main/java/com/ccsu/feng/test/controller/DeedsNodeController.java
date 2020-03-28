package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.vo.DeedsRelationVO;
import com.ccsu.feng.test.domain.vo.DeedsVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IDeedsNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-12-18:12
 */
@RestController
@RequestMapping("/admin/deedsNode")
public class DeedsNodeController {

    @Autowired
    IDeedsNodeService iDeedsNodeService;


    @PostMapping("/addDeedsNode")
    public Result<DeedsNode> addDeedsNode(@RequestBody DeedsNode deedsNode) {
        DeedsNode node = iDeedsNodeService.addDeedsNode(deedsNode);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getDeedsNodeByName")
    public Result<DeedsNode> getDeedsNodeByName(@Param("name") String name) {
        DeedsNode node = iDeedsNodeService.getDeedsNodeByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/findDeedsNodeById")
    public Result<Optional<DeedsNode>> findDeedsNodeById(@Param("id") Long id) {
        Optional<DeedsNode> node = iDeedsNodeService.findDeedsNodeById(id);
        if (node.isPresent()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @DeleteMapping("/deleteDeedsNodeById")
    public Result<Boolean> deleteDeedsNodeById(@Param("id") Long id) {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),
                iDeedsNodeService.deleteDeedsNode(id));
    }

    @PostMapping("/addDeedsPersonRelationship")
    public Result<List<BaseRelationship>> addDeedsPersonRelationship(@RequestBody DeedsRelationVO deedsRelationVO) {
        List<BaseRelationship> list = iDeedsNodeService
                .addDeedsPersonRelationship(deedsRelationVO.getStartName(), deedsRelationVO.getNames(),deedsRelationVO.getType());
        if (!list.isEmpty()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), list);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @PostMapping("/addDeedsPlaceRelationship")
    public Result<List<BaseRelationship>> addDeedsPlaceRelationship(@RequestBody DeedsRelationVO deedsRelationVO) {
        List<BaseRelationship> list = iDeedsNodeService
                .addDeedsPlaceRelationship(deedsRelationVO.getStartName(), deedsRelationVO.getNames(),deedsRelationVO.getType());
        if (!list.isEmpty()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), list);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getDeedsNodeByPage")
    public Result<PageResult> getDeedsNodeByPage(@Param("pageIndex") int pageIndex,
                                                 @Param("pageSize") int pageSize,
                                                 @Param("type") String type) {
        PageResult<DeedsVO> listPersonNodeByPage = iDeedsNodeService.getListDeedsNodeByPage(pageIndex, pageSize,type);
        if (listPersonNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPersonNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getListDeedsNodeByPageAndName")
    public Result<PageResult> getListDeedsNodeByPageAndName(@Param("name") String name,
                                                            @Param("pageIndex") int pageIndex,
                                                            @Param("pageSize") int pageSize,
                                                            @Param("type") String type) {
        PageResult<DeedsVO> listPersonNodeByPage = iDeedsNodeService.getListDeedsNodeByPageAndName(name, pageIndex, pageSize,type);
        if (listPersonNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPersonNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

}
