package com.jxut.easypr.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 10:37 2019/3/21
 */

@Entity
@Data
public class User {

    //用户Id
    @Id
    private long userId;

    //用户车牌号 因为可能该用户不止一个车所以
    private String userPlate;

    //用户手机号
    private String phoneNumber;

    //用户名
    private String userName;

    //用户创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;



}
