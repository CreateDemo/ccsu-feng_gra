package com.ccsu.feng.test.aop;

import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.fastjson.JSON;
import com.ccsu.feng.test.controller.NotFoundExceptionErrorController;
import com.ccsu.feng.test.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.helpers.Util;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 通过spring aop实现service方法执行时间监控
 * @author admin
 * @create 2020-03-05-14:09
 */

@Aspect
@Component
@Slf4j
public class WebLogAop {

    @Pointcut("(execution (* com.ccsu.feng.test.controller..*.*(..)))")
    public void webLog(){
    }

    /**
     * 前置通知
     * @param joinPoint 切点
     * @throws BaseException 异常
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws BaseException, ClassNotFoundException, NotFoundException {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName();
        if ("handleError".equals(methodName)){
            return;
        }
        log.info(String.format(" 类名：%s", clazzName));
        log.info(String.format(" 方法名：%s", methodName));
        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
        Object[] args = joinPoint.getArgs();
        for(int k=0; k<args.length; k++){
            if (args[k] instanceof BindingResult || args[k] instanceof HttpServletResponse
            || args[k] instanceof HttpServletRequest || args[k] instanceof MultipartFile
                    || args[k] instanceof RedirectAttributes){
                continue;
            }
            log.info(" 参数名：" + paramNames[k]+",参数值：" + JSON.toJSONString(args[k]));
        }
        log.info("*****************************************");
    }

    /**
     * 后置通知
     * 打印返回值日志
     * @param ret 返回值
     * @throws Throwable 异常
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName();
        if ("handleError".equals(methodName)){
            return;
        }
        log.info(String.format("类名：%s", clazzName));
        log.info(String.format(" 方法名：%s", methodName));
        log.info(String.format(" 返回值 : %s",  JSON.toJSONString(ret)));
        log.info("*****************************************");

    }


    /**
     * 得到方法参数的名称
     * @param cls 类
     * @param clazzName 类名
     * @param methodName 方法名
     * @return 参数名数组
     * @throws NotFoundException 异常
     */
    private static String[] getFieldsName(Class<?> cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名
        }
        return paramNames;
    }
}
