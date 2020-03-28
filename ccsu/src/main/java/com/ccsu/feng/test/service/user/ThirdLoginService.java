package com.ccsu.feng.test.service.user;

import com.alibaba.fastjson.JSONObject;

/**
 * @author admin
 * @create 2020-02-29-22:14
 */
public interface ThirdLoginService {

    String  saveThirdUserInfo(String openId, JSONObject jsonObject);

}
