package com.jxut.easypr.service.Imp;

import com.jxut.easypr.entity.User;
import com.jxut.easypr.repository.UserRepository;
import com.jxut.easypr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 19:25 2019/4/11
 */
@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(long userId) {
        User result=userRepository.findOneByUserId(userId);
        return result;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
