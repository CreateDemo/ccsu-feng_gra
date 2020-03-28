package com.ccsu.feng.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.feng.test.entity.UserBases;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @create 2020-02-29-15:29
 */
@Mapper
public interface UserBasesMapper extends BaseMapper<UserBases> {
    int insertAndGetId(UserBases userBases);
}
