package com.jxut.easypr.service.Imp;

import com.jxut.easypr.core.CharsRecognise;
import com.jxut.easypr.core.PlateDetect;
import com.jxut.easypr.service.PlateRecogniseService;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Vector;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 19:00 2019/3/15
 */
@Service
public class PlateRecogniseServiceImp implements PlateRecogniseService {

//    static PlateDetect plateDetect =null;
//    static CharsRecognise cr=null;
//    static{
//        plateDetect=new PlateDetect();
//        plateDetect.setPDLifemode(true);
//        cr = new CharsRecognise();
//    }
    @Autowired
    private PlateDetect plateDetect;

    @Autowired
    private CharsRecognise cr;

    @Override
    public String plateRecognise(Mat mat) {

        plateDetect.setPDLifemode(true);
        Vector<Mat> matVector = new Vector<Mat>(1);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            if(matVector.size()>0){
                return cr.charsRecognise(matVector.get(0));
            }
        }
        return null;
    }

    @Override
    public String[] mutiPlateRecognise(Mat mat) {
        plateDetect.setPDLifemode(true);
        PlateDetect plateDetect = new PlateDetect();
        plateDetect.setPDLifemode(true);
        Vector<Mat> matVector = new Vector<Mat>(10);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            CharsRecognise cr = new CharsRecognise();
            String[] results=new String[matVector.size()];
            for (int i = 0; i < matVector.size(); ++i) {
                String result = cr.charsRecognise(matVector.get(i));
                results[i]=result;
            }
            return results;
        }
        return null;
    }

    @Override
    public String plateRecognise(String imgPath) {
        Mat src = opencv_imgcodecs.imread(imgPath);
        return plateRecognise(src);
    }

    @Override
    public String[] mutiPlateRecognise(String imgPath) {
        Mat src = opencv_imgcodecs.imread(imgPath);
        return mutiPlateRecognise(src);
    }

}
