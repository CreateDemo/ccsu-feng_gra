package com.ccsu.feng.test.task;

import com.ccsu.feng.test.dao.DeedsDetailMapper;
import com.ccsu.feng.test.entity.DeedsDetail;
import com.ccsu.feng.test.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.SetUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @create 2020-03-23-12:10
 */
@Component
@Slf4j
public class ReadNumScheduledTask {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    DeedsDetailMapper deedsDetailMapper;

    //每天凌晨4:30执行
    @Scheduled(cron = "0 30 4 ? * * ")
    public void syncPostViews() {
        log.info("======================开始 同步文章访问量======================");
        Long startTime = System.nanoTime();
        List<DeedsDetail> dtoList = new ArrayList<>();
        //从redis取值封装List
        Integer prefixLength = "read_num::detail_id_".length();
        Set<Object> keySet = redisUtil.getKeys("read_num::detail_id_*");
        if (SetUtils.isEmpty(keySet)){
            log.info("======================无文章同步======================");
              return;
        }
        for (Object key : keySet) {
            DeedsDetail deedsDetail =new DeedsDetail();
            deedsDetail.setId(Integer.valueOf(((String) key).substring(prefixLength)));
            deedsDetail.setReadNum((Integer) redisUtil.get((String) key));
            dtoList.add(deedsDetail);
        }

        //更新到数据库中
        deedsDetailMapper.batchUpdateReadNumById(dtoList);
        Long endTime = System.nanoTime();
        log.info("本次文章访问量同步成功, 总耗时: {}", (endTime - startTime) / 1000000 + "ms");
        log.info("======================结束 文章访问量结束======================");
    }
    public static void main(String[] args) {
        String key ="read_num::detail_id_666";
        Integer prefixLength = "read_num::detail_id_".length();
        System.out.println(Long.parseLong(key.substring(prefixLength)));
    }
}
