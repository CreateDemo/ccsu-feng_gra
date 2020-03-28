package com.ccsu.feng.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.feng.test.entity.DeedsDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-22-15:07
 */
@Mapper
public interface DeedsDetailMapper extends BaseMapper<DeedsDetail> {
    void batchUpdateReadNumById(List<DeedsDetail> deedsDetails);
}
