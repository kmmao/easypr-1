package com.jxut.easypr.service;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.springframework.stereotype.Service;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:33 2019/3/15
 */

@Service
public interface PlateRecogniseService {

    //单个车牌识别
    String plateRecognise(Mat mat);

    //多车牌识别
    String[] mutiPlateRecognise(Mat mat);

    //单个车牌识别
    String plateRecognise(String imgPath);

    //多个车牌识别
    String[] mutiPlateRecognise(String imgPath);




}
