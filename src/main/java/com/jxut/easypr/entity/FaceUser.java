package com.jxut.easypr.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:34 2019/4/12
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class FaceUser {
    @Id
    @GeneratedValue
    //Id
    private Long faceId;

    //该脸对应的名字 中文
    private String faceName;

    //对应的百度云userId
    private String faceBaiduId;

    //用户手机号
    private String faceNumber;

    //最后一次进入的时间
    private Date enterTime;

    //用户组
    private String groupId;


}
