package com.jxut.easypr.controller;

import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import com.jxut.easypr.VO.ResultVO;
import com.jxut.easypr.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:33 2019/6/4
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class AliyunTestController {
    @GetMapping("/get")
    public ResultVO get(){
        String accessKey = "LTAIKBsADQRRC32K";
// 阿里云accessSecret
        String accessSecret = "nqNqdI8cnoZithWgQbsjjx5qzIV5TJ";
// regionId
        String regionId = "cn-shanghai";
// 阿里云uid
        String uid = "1901434819472389";
// endPoint:  https://${uid}.iot-as-http2.${region}.aliyuncs.com
        String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";

// 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);

// 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);
// 数据接收
        client.connect(messageToken -> {
            Message m = messageToken.getMessage();
            System.out.println("receive message from " + m);
            return MessageCallback.Action.CommitSuccess;
        });

        MessageCallback messageCallback = new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                log.info("receive : " + new String(messageToken.getMessage().getPayload()));
                return MessageCallback.Action.CommitSuccess;
            }
        };

        client.setMessageListener("/${a1bh2ETMztE}/#",messageCallback);

        IoTMqttClientConfig config = new IoTMqttClientConfig();
        return ResultVOUtil.success();

    }
}
