package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.annotation.AccessLimit;
import com.ccsu.feng.test.entity.AdminUser;
import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.entity.vo.LoginVO;
import com.ccsu.feng.test.entity.vo.UserVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.UserService;
import com.ccsu.feng.test.utils.CookieUtil;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author admin
 * @create 2020-02-26-12:26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Result<Boolean> adminLogin(@Valid @RequestBody AdminUser adminUser, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
             throw  new BaseException("传入参数有误！");
        }
        Boolean userLogin = userService.adminUserLogin(adminUser.getUsername(), adminUser.getPassword());
        if (userLogin) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userLogin);
        } else {
            return Result.error(ResultEnum.USER_AND_PASSWORD_FAIL.getCode(),ResultEnum.USER_AND_PASSWORD_FAIL.getMsg());
        }
    }

    @RequestMapping("/admin/loginOut")
    public String adminLoginOut(){
        redisUtil.hdel("admin_user_key","adminUser");
        return "redirect:/admin/login";
    }


    @ResponseBody
    @RequestMapping(value = "/page/register",method = RequestMethod.POST)
    public  Result<Boolean> pageRegister(@Valid @RequestBody UserVO userVO, BindingResult bindingResult)  {
        if (bindingResult.hasErrors()){
            throw  new BaseException("传入参数有误！");
        }
        Boolean aBoolean = userService.pageUserRegister(userVO);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @AccessLimit(seconds = 60*30,maxCount = 5)
    @ResponseBody
    @RequestMapping(value = "/page/login",method = RequestMethod.POST)
    public  Result<String> pageLogin(@RequestBody LoginVO loginVO, HttpServletResponse response)  {
        String userId = userService.pageLogin(loginVO.getUsername(), loginVO.getPassword());
        if (!StringUtils.isEmpty(userId)) {
            String cookie = EncryptUtil.getInstance().Base64Encode(userId);
            CookieUtil.set(response,"ticket",cookie,true);
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), cookie);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/page/getPageUser",method = RequestMethod.GET)
    public  Result<UserBases> getPageUser()  {
        UserBases userBases = userService.getPageUser();
        if (userBases!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userBases);
        } else {
            return Result.error(ResultEnum.USER_NOT_LOGIN.getCode(),ResultEnum.USER_NOT_LOGIN.getMsg());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/page/smsLogin", method = RequestMethod.POST)
    public  Result<String> smsLogin(String phone,String smsCode, HttpServletResponse response)  {
        String userBasesId = userService.smsLogin(phone, smsCode);
        if (!StringUtils.isEmpty(userBasesId)) {
            String cookie = EncryptUtil.getInstance().AESencode(userBasesId, "feng");
            CookieUtil.set(response,"ticket",cookie,true);
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userBasesId);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/sendPhoneMsg",method = RequestMethod.POST)
    public  Result<Boolean> sendPhoneMsg(String phone)  {
        Boolean aBoolean = userService.sendPhoneMsg(phone);
        if (aBoolean!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/updatePassword",method = RequestMethod.POST)
    public  Result<Boolean> updatePassword(String phone, String password)  {
        Boolean aBoolean = userService.updatePassword(phone,password);
        if (aBoolean!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/smsEditPassword", method = RequestMethod.POST)
    public  Result<Boolean> smsEditPassword(String phone,String smsCode)  {
        Boolean aBoolean = userService.smsEditPassword(phone, smsCode);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), true);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }
    }

    @RequestMapping(value = "/page/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public  Result<Boolean> pageLoginOut( String id,HttpServletRequest request,HttpServletResponse response)  {
        if (StringUtils.isEmpty(id)){
            throw new BaseException("id为空");
        }
        redisUtil.hdel("page_user_key",id);
        CookieUtil.remove(request,response,"ticket");
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), true);
    }


    @RequestMapping(value = "/page/updatePicture", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> updatePicture(String id, String imgUrl)  {
        if (StringUtils.isEmpty(imgUrl)){
            throw new BaseException("imgUrl为空");
        }
        if (StringUtils.isEmpty(id)){
            throw new BaseException("id为空");
        }
        Boolean aBoolean = userService.updatePicture(id, imgUrl);
        if (aBoolean!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

}
