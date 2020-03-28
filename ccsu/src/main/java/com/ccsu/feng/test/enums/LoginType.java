package com.ccsu.feng.test.enums;

/**
 * @author admin
 * @create 2020-02-29-15:10
 */
public enum LoginType {
    LOCAL_LOGIN("local"),
    QQ_LOGIN("qq"),
    WX_LOGIN("wx"),
    WB_LOGIN("wb");
    private  String loginType;
    LoginType(String loginType) {
        this.loginType = loginType;
    }
    public static String getLoginType(LoginType index) {
        for (LoginType c : LoginType.values()) {
            if (c == index) {
                return c.getLoginType();
            }
        }
        return null;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
