package com.jxut.easypr.util;

import com.jxut.easypr.VO.ResultVO;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:43 2019/4/11
 */
public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("success");
        resultVO.setCode(0);
        return  resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg,Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        resultVO.setData(object);
        return resultVO;
    }
}
