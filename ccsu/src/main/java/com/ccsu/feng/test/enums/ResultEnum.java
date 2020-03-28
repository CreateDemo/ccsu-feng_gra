package com.ccsu.feng.test.enums;


/**
 * @author LX
 * @date 2020/1/29 - 21:43
 */
public enum ResultEnum {

    SUCCESS("200", "成功"),
    ERROR("500", "服务器出错"),
    WAIT("1111", "正在处理结果"),
    USER_NOT("10000","该用户名不存！"),
    PASSWORD_ERROR("10001","密码错误!"),
    NICK_NAME_ARLEALY("10002","用户名已存在,请重新输入"),
    PHONE_AREALDRY("10003","手机已经注册!"),
    REGISTER_FAIL("10004","注册失败"),
    PHONE_NOT_RIGISTER("10005","该手机未注册！"),
    PHONE_CODE_LOGTIME("10006","验证码已过期！"),
    PHONE_CODE_FAIL("10007","验证码错误"),
    USER_NOT_LOGIN("10008","用户没有登录"),
    ACCESS_LIMIT_REACHED("10009","使用次数已达上线"),
    USER_AND_PASSWORD_FAIL("10010","用户名或密码错误！"),
    NOT_DATA("10011","数据为空!"),
    COLLECT_IS_EMPTY("10012","收藏夹为空"),
    NOT_COLLECT("10013","未收藏");

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
