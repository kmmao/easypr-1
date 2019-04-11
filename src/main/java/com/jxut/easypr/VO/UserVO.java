package com.jxut.easypr.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:59 2019/4/11
 */
@Data
public class UserVO {
    @JsonProperty(value = "userId")
    private long userId;

    @JsonProperty(value = "userPhone")
    private String userPhone;

    @JsonProperty(value = "userPlate")
    private String userPlate;

    @JsonProperty(value = "userIdCard")
    private String userIdNumber;

    @JsonProperty(value = "userName")
    private String userName;
}
