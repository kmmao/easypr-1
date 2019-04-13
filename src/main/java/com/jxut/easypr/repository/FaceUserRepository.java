package com.jxut.easypr.repository;

import com.jxut.easypr.entity.FaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:56 2019/4/12
 */
public interface FaceUserRepository extends JpaRepository<FaceUser,Long> {
    FaceUser findOneByFaceBaiduId(String faceBaiduId);

    FaceUser findOneByFaceId(Long faceId);
}
