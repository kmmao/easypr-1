package com.jxut.easypr.exception;

import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:10 2019/4/11
 */
@Data
public class UserException extends Exception {
    private Integer code;

    private String msg;

    private Object object;


    public UserException(Integer code , String message,Object object){
        super(message);

        msg=message;

        this.code=code;

        this.object=object;

        this.object=object;
    }

    public UserException(Integer code , String message){
        super(message);

        msg=message;

        this.code=code;
    }
}
