package com.ccsu.feng.test.utils;


import org.apache.http.HttpResponse;

import java.util.*;

public class MsgUtil {

    private static String getMesCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int j = 0; j < 6; j++) {
            int i = random.nextInt(9);
            sb.append(i);
        }
        return sb.toString();
    }

    public static String sendMessage(String phoneNumber) {
        String host = "http://yzxyzm.market.alicloudapi.com";
        String path = "/yzx/verifySms";
        String method = "POST";
        String appcode = "8074a7b8b477479b9def5fdcea5d052f";
        String code = getMesCode();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("phone", phoneNumber);
        querys.put("templateId", "TP18040314");
        querys.put("variable", "code:" + code);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void main(String[] args) {
        sendMessage("15115434424");
    }
}
