package com.ccsu.feng.test.utils;

import com.ccsu.feng.test.entity.UserBases;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @create 2020-02-26-13:47
 */
@Component
public class UserThreadLocal {
    //线程本地变量
    private static  ThreadLocal<UserBases> users = new ThreadLocal<>();

    public UserBases  getUser() {
        return users.get();
    }

    public  void setUser(UserBases user) {
        users.set(user);
    }

    public  void clear(){
        users.remove();
    }
}
