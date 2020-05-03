package com.ccsu.feng.test.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 统一404处理异常
 * @author admin
 * @create 2020-03-01-11:31
 */
@Controller
public class NotFoundExceptionErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "redirect:/error/404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


}
