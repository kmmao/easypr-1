package com.jxut.easypr.repository;

import com.jxut.easypr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:56 2019/3/21
 */

public interface UserRepository extends JpaRepository <User,Long>{
    User findOneByUserPlate(String userPlate);
    User findOneByUserId(long userId);
}
