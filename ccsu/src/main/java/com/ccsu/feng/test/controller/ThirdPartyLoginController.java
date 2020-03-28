package com.ccsu.feng.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.ThirdLoginService;
import com.ccsu.feng.test.utils.CookieUtil;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.QQHttpClient;
import com.ccsu.feng.test.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 第三方登录
 * @author admin
 * @create 2020-02-29-22:02
 */
@Controller
@RequestMapping("/third")
public class ThirdPartyLoginController {

    private static final String QQ_STATA="qq_state";
    @Value("${qq.oauth.http}")
    private String httpQQ;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ThirdLoginService thirdLoginService;

    @GetMapping("/qq/login")
    public String qq(HttpSession session) throws UnsupportedEncodingException {
        //QQ互联中的回调地址
        String backUrl = httpQQ + "/third/index";
        //用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        redisUtil.set(QQ_STATA,uuid);
        session.setAttribute("state",uuid);
        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code"+
                "&client_id=" + QQHttpClient.APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl, "utf-8") +
                "&state=" + uuid;
        return "redirect:" + url;
    }

    @GetMapping("/index")
    public String qqcallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        //qq返回的信息
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String uuid = (String) redisUtil.get(QQ_STATA);
        if(uuid != null){
            if(!uuid.equals(state)){
                throw new BaseException("QQ,state错误");
            }
        }
        //Step2：通过Authorization Code获取Access Token
        String backUrl = httpQQ + "/index";
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code"+
                "&client_id=" + QQHttpClient.APPID +
                "&client_secret=" + QQHttpClient.APPKEY +
                "&code=" + code +
                "&redirect_uri=" + backUrl;

        String access_token = QQHttpClient.getAccessToken(url);

        //Step3: 获取回调后的 openid 值
        url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
        String openid = QQHttpClient.getOpenID(url);
        //Step4：获取QQ用户信息
        url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key="+ QQHttpClient.APPID +
                "&openid=" + openid;
        JSONObject jsonObject = QQHttpClient.getUserInfo(url);
        String ticket = thirdLoginService.saveThirdUserInfo(openid, jsonObject);
        if (!StringUtils.isEmpty(ticket)) {
            String cookie = EncryptUtil.getInstance().AESencode(ticket, "feng");
            CookieUtil.set(response, "ticket", cookie, true);
        }
        //返回主页
        return "redirect:/";
    }
}
