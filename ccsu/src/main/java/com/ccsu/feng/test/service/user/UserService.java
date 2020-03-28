package com.ccsu.feng.test.service.user;

import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.entity.vo.UserVO;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

/**
 * @author admin
 * @create 2020-02-26-12:44
 */
public interface UserService {
    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    Boolean adminUserLogin(String username,String password);

    /**
     * 普通用户注册
     * @param userVO
     * @return
     */
    Boolean pageUserRegister(UserVO userVO);

    UserBases getPageUser();

    /**
     * 普通用户登录
     * @param username
     * @param password
     * @return
     */
    String pageLogin(String username,String password);

    /**
     * 手机短信登录
     * @param phone
     * @param smsCode
     * @return
     */
    String smsLogin(String phone,String smsCode);

    /**
     * 发送短信接口
     * @param phone
     * @return
     */
    Boolean  sendPhoneMsg(String phone);

    /**
     * 修改密码接口
     * @param phone
     * @param password
     * @return
     */
    Boolean updatePassword(String phone,String password);


    /**
     * 修改密码前的短信验证
     * @param phone
     * @param smsCode
     * @return
     */
    Boolean smsEditPassword(String phone,String smsCode);

    /**
     *
     * @param id
     * @param imgUrl
     * @return
     */
    Boolean updatePicture(String id,String imgUrl);
}
