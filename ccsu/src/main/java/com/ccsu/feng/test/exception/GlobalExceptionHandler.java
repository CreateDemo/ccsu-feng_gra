package com.ccsu.feng.test.exception;

import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * @create 2020-02-12-22:11
 */
@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result globalExceptionHandler(BaseException e){
        log.error("相关异常-->{}",e.getMessage());
       return Result.error(ResultEnum.ERROR.getCode(),e.getMessage());
    }
}
