package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.entity.UserCollect;
import com.ccsu.feng.test.entity.vo.UserCollectVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.UserCollectService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * @author admin
 * @create 2020-03-16-11:01
 */
@RestController
@RequestMapping("/page/collect")
public class PageCollectController {

    @Autowired
    UserCollectService userCollectService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result<Boolean>  addCollect(@Valid @RequestBody UserCollectVO userCollectVO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw  new BaseException("传入参数有误！");
        }
        Boolean aBoolean = userCollectService.addUserCollect(userCollectVO);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }


    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public Result<Boolean>  cancelCollect(@Valid @RequestBody UserCollectVO userCollectVO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw  new BaseException("传入参数有误！");
        }
        Boolean aBoolean = userCollectService.cancelUserCollect(userCollectVO);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public Result<Boolean>  selectCollect(@Valid @RequestBody UserCollectVO userCollectVO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw  new BaseException("传入参数有误！");
        }
        Boolean aBoolean = userCollectService.selectUserCollect(userCollectVO);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.NOT_COLLECT.getCode(),ResultEnum.NOT_COLLECT.getMsg());
        }
    }

    @RequestMapping("/selectByUserId")
    public Result<List<UserCollect>>  selectByUserId(Integer userId){
        List<UserCollect> userCollects = userCollectService.selectUserCollectByUserId(userId);
        if (!ListUtils.isEmpty(userCollects)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userCollects);
        } else {
            return Result.error(ResultEnum.COLLECT_IS_EMPTY.getCode(),ResultEnum.COLLECT_IS_EMPTY.getMsg());
        }
    }

    @RequestMapping("/selectLikeByTitle")
    public Result<List<UserCollect>>  selectLikeByTitle(String title){
        List<UserCollect> userCollects = userCollectService.selectLikeByTitle(title);
        if (!ListUtils.isEmpty(userCollects)) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userCollects);
        } else {
            return Result.error(ResultEnum.COLLECT_IS_EMPTY.getCode(),ResultEnum.COLLECT_IS_EMPTY.getMsg());
        }
    }

}
