package com.jxut.easypr.service;

import com.jxut.easypr.entity.FaceUser;
import org.springframework.stereotype.Service;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 21:00 2019/4/12
 */
@Service
public interface FaceUserService {
    //通过百度的userId查找该用户信息
    FaceUser findByBaiduId(String faceBaiduId);

    FaceUser save(FaceUser faceUser);

    void delete(Long faceId);

    FaceUser findOne(Long faceId);
}
