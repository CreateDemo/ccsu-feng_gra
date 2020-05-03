package com.ccsu.feng.test.service.user.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccsu.feng.test.dao.DeedsDetailMapper;
import com.ccsu.feng.test.entity.DeedsDetail;
import com.ccsu.feng.test.entity.vo.DeedsDetailVO;
import com.ccsu.feng.test.service.user.DeedsDetailService;
import com.ccsu.feng.test.utils.DateUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @create 2020-03-22-22:21
 */
@Service
public class DeedsDetailServiceImpl implements DeedsDetailService {


    @Autowired
    DeedsDetailMapper deedsDetailMapper;

    @Autowired
    RedisUtil redisUtil;

    private final  static  String READ_NUM_KEY="read_num::detail_id_";

    @Override
    public void addDeedsDetail(DeedsDetail deedsDetail) {
    }

    @Override
    public DeedsDetailVO getDeedsDetailById(Integer detailId)  {
        QueryWrapper<DeedsDetail> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("detail_id",detailId);
        List<DeedsDetail> deedsDetailList = deedsDetailMapper.selectList(queryWrapper);
        DeedsDetail deedsDetail =null;
        if (deedsDetailList!=null&&deedsDetailList.size()>0){
            deedsDetail=deedsDetailList.get(0);
        }else {
            return null;
        }
        DeedsDetailVO deedsDetailVO =new DeedsDetailVO();
        deedsDetailVO.setName(deedsDetail.getName());
        deedsDetailVO.setContent(deedsDetail.getContent());
        deedsDetailVO.setNextDeedsName(deedsDetail.getNextDeedsName());
        deedsDetailVO.setNextDeedsUrl(deedsDetail.getNextDeedsUrl());
        deedsDetailVO.setPreDeedsName(deedsDetail.getPreDeedsName());
        deedsDetailVO.setPreDeedsUrl(deedsDetail.getPreDeedsUrl());
        //读取文章访问量
        deedsDetailVO.setReadNum(getReadNum(deedsDetail.getId(),deedsDetail.getReadNum()));
        deedsDetailVO.setCreateTime(DateUtil.getDateFormat(deedsDetail.getCreateTime()));
        return  deedsDetailVO;
    }

    private int getReadNum(Integer id,int readNum){
        boolean hasKey = redisUtil.hasKey(READ_NUM_KEY + id);
        if (!hasKey){
            //保存1天：
            redisUtil.set(READ_NUM_KEY+id,readNum+1,60*60*24);
            return readNum+1;
        }else {
            redisUtil.incr(READ_NUM_KEY + id,1);
        }
        return (int)redisUtil.get(READ_NUM_KEY + id);
    }


}
