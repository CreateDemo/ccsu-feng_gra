package com.ccsu.feng.test.service.user.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ccsu.feng.test.dao.AdminUserMapper;
import com.ccsu.feng.test.dao.UserAuthsMapper;
import com.ccsu.feng.test.dao.UserBasesMapper;
import com.ccsu.feng.test.entity.AdminUser;
import com.ccsu.feng.test.entity.UserAuths;
import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.entity.vo.UserVO;
import com.ccsu.feng.test.enums.LoginTime;
import com.ccsu.feng.test.enums.LoginType;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.UserService;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.MsgUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-26-12:45
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final static String PASSWORD_KEY = "feng";
    private final static String ADMIN_USER_KEY = "admin_user_key";
    private final static String PAGE_USER_KEY = "page_user_key";



    @Autowired
    UserThreadLocal userThreadLocal;

    //站内 新注册的默认头像
    private final static String PAG_USER_PICTURE = "http://www.liaoyunfeng.top/images/firstPicture—20200229154454-.png";
    @Autowired
    AdminUserMapper adminUserMapper;
    @Autowired
    UserAuthsMapper userAuthsMapper;
    @Autowired
    UserBasesMapper userBasesMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Boolean adminUserLogin(String username, String password) {
        String decrptPassword = EncryptUtil.getInstance().MD5(password, PASSWORD_KEY);
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                .eq("password", decrptPassword);
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        if (adminUser == null) {
            return false;
        }
        //管理员7天过期
        redisUtil.hset(ADMIN_USER_KEY, "adminUser", adminUser, 60 * 60 * 24 * 7);
        return true;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public Boolean pageUserRegister(UserVO userVO) {
        if (LoginType.LOCAL_LOGIN.getLoginType().equals(userVO.getLoginType())) {
            QueryWrapper<UserBases> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("nick_name", userVO.getNickName());
            UserBases bases = userBasesMapper.selectOne(queryWrapper);
            if (bases != null) {
                throw new BaseException(ResultEnum.NICK_NAME_ARLEALY.getMsg());
            }
            QueryWrapper<UserAuths> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("identifier", userVO.getPhone());
            UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper1);
            if (userAuths != null) {
                throw new BaseException(ResultEnum.PHONE_AREALDRY.getMsg());
            }
            UserBases userBases = new UserBases();
            userBases.setNickName(userVO.getNickName());
            userBases.setPicture(PAG_USER_PICTURE);
            userBasesMapper.insert(userBases);
            log.info("用户 添加成功 ->{}", userBases);

            //添加 auth
            UserAuths userAuthsPhone = new UserAuths();
            userAuthsPhone.setLoginType(LoginType.LOCAL_LOGIN.getLoginType());
            userAuthsPhone.setIdentifier(userVO.getPhone());
            userAuthsPhone.setUserId(userBases.getId());
            userAuthsPhone.setCredential(EncryptUtil.getInstance().MD5(userVO.getCredential(), PASSWORD_KEY));
            userAuthsMapper.insert(userAuthsPhone);

            UserAuths userAuthsNickName = new UserAuths();
            userAuthsNickName.setLoginType(LoginType.LOCAL_LOGIN.getLoginType());
            userAuthsNickName.setIdentifier(userVO.getNickName());
            userAuthsNickName.setUserId(userBases.getId());
            userAuthsNickName.setCredential(EncryptUtil.getInstance().MD5(userVO.getCredential(), PASSWORD_KEY));
            userAuthsMapper.insert(userAuthsNickName);
        }
        return true;
    }

    @Override
    public UserBases getPageUser() {
        if (userThreadLocal==null){
            return null;
        }
        return userThreadLocal.getUser();
    }

    @Override
    public String pageLogin(String username, String password) {
        QueryWrapper<UserAuths> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identifier", username)
                .eq("credential", EncryptUtil.getInstance().MD5(password, PASSWORD_KEY));
        UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper);
        if (userAuths == null) {
            throw new BaseException(ResultEnum.PASSWORD_ERROR.getMsg());
        }
        UserBases userBases = userBasesMapper.selectById(userAuths.getUserId());
        //用户信息保存
        redisUtil.hset(PAGE_USER_KEY,String.valueOf(userBases.getId()),userBases, LoginTime.SAVE_LOGIN_TIME.getTime());
        return String.valueOf(userBases.getId());
    }


    @Override
    public String smsLogin(String phone, String smsCode) {
        boolean hasKey = redisUtil.hasKey(phone);
        if (!hasKey) {
            throw new BaseException(ResultEnum.PHONE_CODE_LOGTIME.getMsg());
        }
        String smsCodeObject = (String) redisUtil.get(phone);
        if (!smsCodeObject.equals(smsCode)) {
            throw new BaseException(ResultEnum.PHONE_CODE_FAIL.getMsg());
        }
        QueryWrapper<UserAuths> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identifier", phone);
        UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper);
        UserBases userBases = userBasesMapper.selectById(userAuths.getUserId());
        //登录成功删除 key
//        redisUtil.del(phone);   测试环境先不浪费
        //登录成功2小时过期;
        redisUtil.hset(PAGE_USER_KEY, String.valueOf(userBases.getId()), userBases, LoginTime.SAVE_LOGIN_TIME.getTime());
        return String.valueOf(userBases.getId());
    }

    @Override
    public Boolean sendPhoneMsg(String phone) {
        QueryWrapper<UserAuths> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("identifier", phone);
        UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper1);
        if (userAuths == null) {
            throw new BaseException(ResultEnum.PHONE_NOT_RIGISTER.getMsg());
        }
        String message = MsgUtil.sendMessage(phone);
        //5分钟过期
//        redisUtil.set(phone, message, 60 * 5);
        redisUtil.set(phone, message);   //测试环境不过期
        return true;
    }

    @Override
    public Boolean updatePassword(String phone, String password) {
        QueryWrapper<UserAuths> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identifier", phone);
        UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper);

        UpdateWrapper<UserAuths> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userAuths.getUserId());
        UserAuths userAuths1 =new UserAuths();
        userAuths1.setCredential(EncryptUtil.getInstance().MD5(password, PASSWORD_KEY));
        userAuthsMapper.update(userAuths1,updateWrapper);
        return true;
    }

    @Override
    public Boolean smsEditPassword(String phone, String smsCode) {
        boolean hasKey = redisUtil.hasKey(phone);
        if (!hasKey) {
            throw new BaseException(ResultEnum.PHONE_CODE_LOGTIME.getMsg());
        }
        String smsCodeObject = (String) redisUtil.get(phone);
        if (!smsCodeObject.equals(smsCode)) {
            throw new BaseException(ResultEnum.PHONE_CODE_FAIL.getMsg());
        }
        //登录成功删除 key
//        redisUtil.del(phone); 测试环境不删除
        return true;
    }

    @Override
    public Boolean updatePicture(String id,String imgUrl) {
        UserBases userBases  =new UserBases();
        userBases.setPicture(imgUrl);
        userBases.setId(Integer.valueOf(id));
         userBasesMapper.updateById(userBases);
        UserBases userBases1 = userBasesMapper.selectById(id);
        //用户信息保存
        redisUtil.hset(PAGE_USER_KEY,String.valueOf(userBases1.getId()),userBases1,LoginTime.SAVE_LOGIN_TIME.getTime());
        return true;
    }
}
