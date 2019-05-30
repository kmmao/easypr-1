package com.jxut.easypr.controller;

import com.jxut.easypr.core.*;
import com.jxut.easypr.entity.User;
import com.jxut.easypr.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 15:30 2019/3/14
 */
@RestController
@RequestMapping("/easy")
@Slf4j

public class FileUploadController {

    //private PlateRecogniseServiceImp plateRecogniseServiceImp;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")

    public String upload(@RequestParam(value = "file")MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            log.error("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://EasyPR//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Mat src=opencv_imgcodecs.imread(filePath+fileName);
//        String[] test = plateRecogniseServiceImp.mutiPlateRecognise(src);
//        String ret = plateRecogniseServiceImp.plateRecognise(src);
        //String ret=AppCodeDemo.plateOutput(filePath+fileName); // 阿里云接口,因限制500次数,已弃用
        String ret=BaiduPlate.plate(filePath+fileName);//一天200次

        log.info("plate={}",ret);

        if(ret==null) {
            return "fail";
        }
        User result=userRepository.findOneByUserPlate(ret);

        if(result==null) {
            return "fail";
        }

        log.info(request.getRemoteUser());
        return "success";
    }
}
