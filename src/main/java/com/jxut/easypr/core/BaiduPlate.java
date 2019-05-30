package com.jxut.easypr.core;
import com.baidu.aip.ocr.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 9:16 2019/5/16
 */
@Slf4j
public class BaiduPlate {
    public static final String APP_ID = "16262933";
    public static final String API_KEY = "y60u20GyD1fq1hF31kcG8Riv";
    public static final String SECRET_KEY = "fLlSjkR42o5o1UrHow5G00nHSEEkyhp4";

    public static String plate(String image) {

        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "true");


        // 参数为本地图片路径
        JSONObject res = client.plateLicense(image, options);
        log.info("message={}",res.toString());
        JSONArray result=res.getJSONArray("words_result");
        JSONObject result1=result.getJSONObject(0);
        String plate=result1.getString("number");

        return plate;


    }
}
