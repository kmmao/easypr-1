package com.jxut.easypr.controller;

import com.jxut.easypr.VO.ResultVO;
import com.jxut.easypr.VO.UserVO;
import com.jxut.easypr.converter.UserVO2User;
import com.jxut.easypr.entity.User;
import com.jxut.easypr.exception.UserException;
import com.jxut.easypr.service.UserService;
import com.jxut.easypr.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:00 2019/4/11
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    //新增用户
    @PostMapping("/create")
    public ResultVO create(@Valid UserVO userVO,
                           BindingResult bingingResult) throws UserException {
        if(bingingResult.hasErrors()) {
            log.error("创建订单 参数不正确 userVO={}",userVO);

            throw new UserException(500,"参数错误",userVO);
        }

        User user=UserVO2User.converter(userVO);

        User result=userService.save(user);

        if (result==null) {
            log.error("添加失败");
            throw new UserException(500,"添加失败",userVO);
        }

        return ResultVOUtil.success(userVO);
    }

    //获取所有用户列表
    @GetMapping("/listAll")
    public ResultVO listAll(){
        List<User> list=userService.findAll();
        return ResultVOUtil.success(list);
    }

    //删除用户
    @PostMapping("/delete")
    public ResultVO delete( Long userId) throws UserException {
        User result = userService.findOne(userId);

        if (result==null) {
            log.error("用户未找到");
            throw new UserException(500,"用户未找到");
        }

        userService.delete(userId);

        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(result,userVO);

         return ResultVOUtil.success(userVO);
    }

    //更新用户
    @PostMapping("/update")
    @Modifying
    public ResultVO update(@Valid UserVO userVO,
                           BindingResult bingingResult) throws UserException {
        if(bingingResult.hasErrors()) {
            log.error("创建订单 参数不正确 userVO={}",userVO);

            throw new UserException(500,"参数错误");
        }

        User user=UserVO2User.converter(userVO);

        User result=userService.update(user);

        if (result==null) {
            throw new UserException(500,"添加失败");
        }


        //TODO 覆盖问题待解决
        log.info("userVO={}",userVO);
        return ResultVOUtil.success(result);

        
    }


}
