package com.ccsu.feng.test.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccsu.feng.test.annotation.AccessLimit;
import com.ccsu.feng.test.dao.UserAuthsMapper;
import com.ccsu.feng.test.entity.UserAuths;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.filter.RequestWrapper;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author leo-bin
 * @date 2019/12/23 18:41
 * @apiNote 自定义接口防刷拦截器
 */
@Slf4j
@Component
public class AntiBrushInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    UserAuthsMapper userAuthsMapper;

    private final static String ACCESS_LITMIT_KEY = "access_limit_key:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求的对象是否是方法
        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法中的注解，判断方式是否加了注解
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }

            log.info("[preHandle] executing... request uri is {}", request.getRequestURI());
            int second = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            //限制用户登录次数
            if (login) {
                if (isJson(request)) {
                    JSONObject jsonObject = JSON.parseObject(new RequestWrapper(request).getBodyString());
                    QueryWrapper<UserAuths> queryWrapper = new QueryWrapper<>();
                    String username = String.valueOf(jsonObject.get("username"));
                    queryWrapper.eq("identifier", username);
                    UserAuths userAuths = userAuthsMapper.selectOne(queryWrapper);
                    if (userAuths == null) {
                        throw new BaseException(ResultEnum.USER_NOT.getMsg());
                    }
                    String key = ACCESS_LITMIT_KEY + username;
                    Integer counts = (Integer) redisUtil.get(key);
                    if (counts != null) {
                        if (counts < maxCount) {
                            //设置key-value的过期时间，并且将value+1
                            redisUtil.incr(key, 1);
                        } else {
                            //超出访问次数
                            String mess = "当前访问次数:" + counts + "；限制的最大访问次数：" + maxCount;
                            log.info("超出限制范围了：{}", mess);
                            //返回错误信息
                            throw new BaseException(ResultEnum.ACCESS_LIMIT_REACHED.getMsg() + ":登录错误次数已达5次，请30分钟后再试！");
                        }
                    } else {
                        //第一次访问，默认的递增因子设置为 "1",并设置过期时间
                        redisUtil.set(key, 1, second);
                    }

                }
            } else {
                //其他接口限制
                //todo
            }
        }
        return true;
    }


    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJson(HttpServletRequest request) {
        if (request.getContentType() != null) {
            return request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ||
                    request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
