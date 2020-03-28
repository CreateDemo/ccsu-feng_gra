package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.entity.DeedsDetail;
import com.ccsu.feng.test.entity.vo.DeedsDetailVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.user.DeedsDetailService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @create 2020-03-22-22:19
 */
@RestController
@RequestMapping("/deedsDetail")
public class PageDeedsDetailController {

    @Autowired
    DeedsDetailService deedsDetailService;

    @RequestMapping("/getDeedsDetail")
    public Result<DeedsDetailVO> getDeedsDetailById(Integer detailId){
        DeedsDetailVO deedsDetail = deedsDetailService.getDeedsDetailById(detailId);
        return Result.build(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),deedsDetail);
    }
}
