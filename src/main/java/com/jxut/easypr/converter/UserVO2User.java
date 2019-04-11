package com.jxut.easypr.converter;

import com.jxut.easypr.VO.UserVO;
import com.jxut.easypr.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:18 2019/4/11
 */
public class UserVO2User {
    public static User converter(UserVO userVO){
        User user=new User();

        user.setUserIdNumber(userVO.getUserIdNumber());

        user.setUserId(userVO.getUserId());

        user.setUserPhone(userVO.getUserPhone());

        user.setUserName(userVO.getUserName());

        user.setUserPlate(userVO.getUserPlate());


//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        //TODO
        user.setUpdateTime(new Date());

        return user;

    }
}
