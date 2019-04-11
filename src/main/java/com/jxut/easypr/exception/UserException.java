package com.jxut.easypr.exception;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:10 2019/4/11
 */
public class UserException extends Exception {
    private Integer code;

    public UserException(Integer code , String message){
        super(message);

        this.code=code;
    }
}
