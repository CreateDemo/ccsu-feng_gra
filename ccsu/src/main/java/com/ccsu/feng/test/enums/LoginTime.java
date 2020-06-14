package com.ccsu.feng.test.enums;

/**
 * @author admin
 * @create 2020-03-11-13:47
 */
public enum  LoginTime {
    SAVE_LOGIN_TIME(60*60*24*60);

    int time;

    LoginTime(int time) {
        this.time=time;
    }
    public Integer getTime() {
        return time;
    }
    public void setTime(Integer time) {
        this.time = time;
    }
}
