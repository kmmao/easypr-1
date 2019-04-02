package com.jxut.easypr.controller;

import com.jxut.easypr.core.CharsRecognise;
import com.jxut.easypr.core.PlateDetect;
import com.jxut.easypr.entity.User;
import com.jxut.easypr.repository.UserRepository;
import com.jxut.easypr.service.Imp.PlateRecogniseServiceImp;
import com.jxut.easypr.service.PlateRecogniseService;
import net.bytebuddy.asm.Advice;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.bytedeco.javacpp.opencv_imgcodecs;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 15:30 2019/3/14
 */
@RestController
@RequestMapping("/easy")

public class FileUploadController {

    @Autowired
    private PlateRecogniseServiceImp plateRecogniseServiceImp;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")

    public String upload(@RequestParam(value = "file")MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
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


        Mat src=opencv_imgcodecs.imread(filePath+fileName);
        String ret = plateRecogniseServiceImp.plateRecognise(src);
        User result=userRepository.findOneByUserPlate(ret);

        if(result==null) {
            return "fail";
        }



        return "success";
    }
}
