package com.ccsu.feng.test.service.user;

import com.ccsu.feng.test.entity.UserCollect;
import com.ccsu.feng.test.entity.vo.UserCollectVO;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-16-11:09
 */
public interface UserCollectService {

    Boolean addUserCollect(UserCollectVO userCollectVO);

    Boolean cancelUserCollect(UserCollectVO userCollectVO);

    Boolean selectUserCollect(UserCollectVO userCollectVO);

    List<UserCollect> selectUserCollectByUserId(Integer id);

    List<UserCollect> selectLikeByTitle(String title);
}
