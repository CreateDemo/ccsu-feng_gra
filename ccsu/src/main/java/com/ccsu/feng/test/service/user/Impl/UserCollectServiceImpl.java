package com.ccsu.feng.test.service.user.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccsu.feng.test.dao.UserCollectMapper;
import com.ccsu.feng.test.entity.UserCollect;
import com.ccsu.feng.test.entity.vo.UserCollectVO;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-16-11:09
 */
@Service
public class UserCollectServiceImpl implements UserCollectService {

    @Autowired
    UserCollectMapper userCollectMapper;



    @Transactional(rollbackFor = BaseException.class)
    @Override
    public Boolean addUserCollect(UserCollectVO userCollectVO) {
        UserCollect userCollect =new UserCollect();
        userCollect.setUserId(userCollectVO.getUserId());
        userCollect.setLinkUrl(userCollectVO.getLinkUrl());
        userCollect.setTitle(userCollectVO.getTitle());
        userCollectMapper.insert(userCollect);
        return true;
    }


    @Transactional(rollbackFor = BaseException.class)
    @Override
    public Boolean cancelUserCollect(UserCollectVO userCollectVO) {
        QueryWrapper<UserCollect> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_id",userCollectVO.getUserId())
                    .eq("link_url",userCollectVO.getLinkUrl());
        userCollectMapper.delete(queryWrapper);
        return true;
    }


    @Override
    public Boolean selectUserCollect(UserCollectVO userCollectVO) {
        QueryWrapper<UserCollect> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_id",userCollectVO.getUserId())
                .eq("link_url",userCollectVO.getLinkUrl());
        UserCollect userCollect = userCollectMapper.selectOne(queryWrapper);
        if (userCollect==null){
            return false;
        }
        return true;
    }


    @Override
    public List<UserCollect> selectUserCollectByUserId(Integer userId) {
        QueryWrapper<UserCollect> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<UserCollect> userCollect = userCollectMapper.selectList(queryWrapper);
        return userCollect;
    }


    @Override
    public List<UserCollect> selectLikeByTitle(String title) {
        QueryWrapper<UserCollect> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("title",title);
        List<UserCollect> userCollects = userCollectMapper.selectList(queryWrapper);
        return userCollects;
    }
}
