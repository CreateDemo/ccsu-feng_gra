package com.ccsu.feng.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.feng.test.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @create 2020-02-26-12:39
 */
@Mapper
public interface AdminUserMapper  extends BaseMapper<AdminUser> {

}
