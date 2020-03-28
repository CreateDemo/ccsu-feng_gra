package com.ccsu.feng.test.service.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccsu.feng.test.dao.UserAuthsMapper;
import com.ccsu.feng.test.dao.UserBasesMapper;
import com.ccsu.feng.test.entity.UserAuths;
import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.enums.LoginTime;
import com.ccsu.feng.test.enums.LoginType;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.ThirdLoginService;
import com.ccsu.feng.test.utils.QQHttpClient;
import com.ccsu.feng.test.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

/**
 * @author admin
 * @create 2020-02-29-22:16
 */
@Service
public class ThirdLoginServiceImpl implements ThirdLoginService {

    @Autowired
    UserBasesMapper userBasesMapper;

    @Autowired
    UserAuthsMapper userAuthsMapper;
    @Autowired
    RedisUtil redisUtil;


    @Transactional(rollbackFor = BaseException.class)
    @Override
    public String saveThirdUserInfo(String openId, JSONObject jsonObject) {
        if (jsonObject != null && !StringUtils.isEmpty(openId)) {
            //之前已经登录过
            QueryWrapper<UserAuths> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("identifier", openId);
            UserAuths userAuths1 = userAuthsMapper.selectOne(queryWrapper);
            if (userAuths1 != null) {
                //更新基本资料
                UserBases userBases = userBasesMapper.selectById(userAuths1.getUserId());
                redisUtil.hset("page_user_key", String.valueOf(userBases.getId()), userBases, LoginTime.SAVE_LOGIN_TIME.getTime());
                return String.valueOf(userBases.getId());
            }

            UserBases userBases = new UserBases();
            //QQ名
            userBases.setNickName((String) jsonObject.get("nickname"));
            //QQ头像
            userBases.setPicture((String) jsonObject.get("figureurl_qq_2"));
            //插入基本资料信息
            userBasesMapper.insert(userBases);

            UserAuths userAuths = new UserAuths();
            userAuths.setUserId(userBases.getId());
            userAuths.setLoginType(LoginType.QQ_LOGIN.getLoginType());
            userAuths.setIdentifier(openId);
            userAuthsMapper.insert(userAuths);
            redisUtil.hset("page_user_key", String.valueOf(userBases.getId()), userBases, LoginTime.SAVE_LOGIN_TIME.getTime());
            return String.valueOf(userBases.getId());
        }
        return null;

    }
}
