package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @create 2020-02-23-13:36
 */
@RestController
@RequestMapping("/admin/baseNode")
public class BaseNodeCotroller {

    @Autowired
    IBaseNodeService baseNodeService;

    @GetMapping("/findBaseNodeName")
    public Result<List<Map<String, String>>> findBaseNodeName(String nodeType ,String type) {
        List<Map<String, String>> node = baseNodeService.findBaseNodeName(nodeType,type);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/findBaseRelationshipByNameByPage")
    public Result<PageResult<ListRelationVO>> findBaseRelationshipByNameByPage
            (@Param("name") String name,@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize) {
        PageResult<ListRelationVO> node = baseNodeService.findBaseRelationshipByNameByPage(name,pageIndex,pageSize);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

}
