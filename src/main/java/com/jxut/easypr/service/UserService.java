package com.jxut.easypr.service;

import com.jxut.easypr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:53 2019/4/11
 */
@Service
public interface UserService {
    User findOne(long userId);

    User save(User user);

    void delete(long userId);

    List<User> findAll();
}
