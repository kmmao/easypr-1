package com.jxut.easypr.repository;

import com.jxut.easypr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:56 2019/3/21
 */
@RepositoryRestResource(path="user")
public interface UserRepository extends JpaRepository <User,Long>{
}
