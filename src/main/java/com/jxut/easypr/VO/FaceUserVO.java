package com.jxut.easypr.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 15:08 2019/4/13
 */
@Data
public class FaceUserVO {
    @JsonProperty(value = "faceId")
    private Long faceId;

    @JsonProperty(value = "faceName")
    private String faceName;

    @JsonProperty(value = "faceBaiduId")
    private String faceBaiduId;

    @JsonProperty(value = "facePhoneNumber")
    private String facePhoneNumber;

    @JsonProperty(value = "groupId")
    private String groupId;
}
