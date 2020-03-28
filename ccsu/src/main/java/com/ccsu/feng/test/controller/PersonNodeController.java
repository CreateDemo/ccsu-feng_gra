package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.vo.PersonRelationshipVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IPersonNodeService;
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
 * @create 2020-02-11-20:51
 */
@RestController
@RequestMapping("/admin/personNode")
public class PersonNodeController {

    @Autowired
    IPersonNodeService iPersonNodeService;

    @PostMapping("/addPersonNode")
    public Result<PersonVO> addPersonNode(@RequestBody PersonNode personNode) {
        PersonVO node = iPersonNodeService.addPersonNode(personNode);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPersonNodeByPage")
    public Result<PageResult> getPersonNodeByPage(@Param("pageIndex") int pageIndex,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("type")String type) {
        PageResult<PersonVO> listPersonNodeByPage = iPersonNodeService.getListPersonNodeByPage(pageIndex, pageSize,type);
        if (listPersonNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPersonNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getListPersonNodeByPageAndName")
    public Result<PageResult> getListPersonNodeByPageAndName(@Param("name") String name,
                                                             @Param("pageIndex") int pageIndex,
                                                             @Param("pageSize") int pageSize,
                                                             @Param("type") String type) {
        PageResult<PersonVO> listPersonNodeByPage = iPersonNodeService.getListPersonNodeByPageAndName(name, pageIndex, pageSize,type);
        if (listPersonNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPersonNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/findPersonNodeById")
    public Result<Optional<PersonNode>> findPersonNodeById(@Param("id") Long id) {
        Optional<PersonNode> node = iPersonNodeService.findPersonNodeById(id);
        if (node.isPresent()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @DeleteMapping("/deletePersonNodeById")
    public Result<Boolean> deletePersonNodeById(@Param("id") Long id) {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), iPersonNodeService.deletePersonNode(id));
    }


    @GetMapping("/getPersonNodeByName")
    public Result<PersonNode> getPersonNodeByName(@Param("name") String name) {
        PersonNode node = iPersonNodeService.getPersonNodeByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    /**
     * 单向关系
     *
     * @param vo
     * @return
     */
    @PostMapping("/addPersonNodeRelationship")
    public Result<BaseRelationship> addPersonNodeRelationship(@RequestBody PersonRelationshipVO vo) {
        BaseRelationship baseRelationship = iPersonNodeService
                .addPersonNodeRelationship(vo.getPreName(), vo.getStartName(), vo.getEndName(),vo.getType());
        if (baseRelationship != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), baseRelationship);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    /**
     * 添加双向关系
     *
     * @param vo
     * @return
     */
    @PostMapping("/addTwoPersonRelationship")
    public Result<List<BaseRelationship>> addTwoPersonRelationship(@RequestBody PersonRelationshipVO vo) {
        List<BaseRelationship> list = iPersonNodeService
                .addTwoPersonNodeRelationship(vo.getPreName(), vo.getSufName(), vo.getStartName(), vo.getEndName(),vo.getType());
        if (!list.isEmpty()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), list);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @PostMapping("getPersonRelationship")
    public Result<BaseRelationship> getPersonRelationship(@RequestBody PersonRelationshipVO vo) {
        BaseRelationship personNodeRelationship = iPersonNodeService
                .getPersonNodeRelationship(vo.getPreName(), vo.getStartName(), vo.getEndName());
        if (personNodeRelationship != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), personNodeRelationship);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    /**
     * 为人物添加兵器
     *
     * @param vo
     * @return
     */
    @PostMapping(path = "/addPersonNodeWeaponNode")
    public Result<List<BaseRelationship>> addPersonNodeWeaponNode(@RequestBody PersonRelationshipVO vo) {
        List<BaseRelationship> list = iPersonNodeService
                .addPersonNodeWeapon(vo.getStartName(), vo.getEndName(),vo.getType());
        if (!list.isEmpty()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), list);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }
}
