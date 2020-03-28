package com.ccsu.feng.test.interceptor;

import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.utils.CookieUtil;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author admin
 * @create 2020-03-03-17:30
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserThreadLocal userThreadLocal;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //普通用户登录
       String ticket = CookieUtil.getValue(httpServletRequest, "ticket");
       if (!StringUtils.isEmpty(ticket)){
           String deCode = EncryptUtil.getInstance().Base64Decode(ticket);
           Object pageUserKey = redisUtil.hget("page_user_key", deCode);
           if (pageUserKey!=null){
               if ("/page/login".equals(httpServletRequest.getRequestURI())){
                   httpServletResponse.sendRedirect("/");
                   return false;
               }
               userThreadLocal.setUser((UserBases) pageUserKey);
           }
       }
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        userThreadLocal.clear();
    }

}
