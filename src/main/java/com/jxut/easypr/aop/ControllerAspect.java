package com.jxut.easypr.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 8:40 2019/4/18
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    //声明切点为controller的全体对象
    @Pointcut(value ="execution(* com.jxut.easypr.controller.*.*(..))")
    public void log() {
    }

    //在切点之前执行该点
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        // url
        log.info("url={}", request.getRequestURL());
        // port
        log.info("port={}", request.getRemotePort());
        // method
        log.info("method={}", request.getMethod());
        // ip
        log.info("ip={}", request.getRemoteAddr());
        // class_method
        log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 参数
        log.info("args={}", Arrays.toString(joinPoint.getArgs()));
    }
    //解释:returning 是指将切入的方法的返回值注入给object  该点是在方法return 之后再执行
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        log.info("response={}", object);
    }

}
