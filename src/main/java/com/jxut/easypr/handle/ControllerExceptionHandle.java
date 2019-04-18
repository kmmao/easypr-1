package com.jxut.easypr.handle;

import com.jxut.easypr.VO.ResultVO;
import com.jxut.easypr.exception.UserException;
import com.jxut.easypr.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:37 2019/4/15
 */

//这个注解是指这个类是处理其他controller抛出的异常
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandle {

    //这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
    @ExceptionHandler(UserException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleUserException(UserException e) {

        log.error(e.getMsg());
        return ResultVOUtil.error(e.getCode(),e.getMsg(),e.getObject());
    }
}
