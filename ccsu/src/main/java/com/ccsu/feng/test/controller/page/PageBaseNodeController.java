package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @create 2020-03-13-16:58
 */
@RestController
@RequestMapping("/page/base")
public class PageBaseNodeController {

    @Autowired
    IBaseNodeService baseNodeService;


    @GetMapping("/findBaseNodeByName")
    public Result<Object> findBaseNodeByName(String name) {
        Object node = baseNodeService.getShowBaseNodeByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


}
