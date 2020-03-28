package com.ccsu.feng.test.service.user;

import com.ccsu.feng.test.entity.DeedsDetail;
import com.ccsu.feng.test.entity.vo.DeedsDetailVO;

import java.text.ParseException;

/**
 * @author admin
 * @create 2020-03-22-15:11
 */
public interface DeedsDetailService {

    void addDeedsDetail(DeedsDetail deedsDetail);

    DeedsDetailVO getDeedsDetailById(Integer detailId) ;

}
