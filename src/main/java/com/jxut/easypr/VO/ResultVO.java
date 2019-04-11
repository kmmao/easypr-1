package com.jxut.easypr.VO;

import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:44 2019/4/11
 */
@Data
public class ResultVO {
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回的具体内容
    private Object data;
}
