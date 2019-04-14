package com.jxut.easypr.controller;

import com.baidu.aip.face.AipFace;
import com.jxut.easypr.VO.FaceUserVO;
import com.jxut.easypr.VO.ResultVO;
import com.jxut.easypr.entity.FaceUser;
import com.jxut.easypr.exception.UserException;
import com.jxut.easypr.service.FaceUserService;
import com.jxut.easypr.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.Base64.getEncoder;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 15:21 2019/4/13
 */
@RestController
@RequestMapping("/faceuser")
@Slf4j
public class FaceUserController {
    @Autowired
    private FaceUserService faceUserService;

    private static final String APP_ID = "15830188";
    private static final String API_KEY = "nHiCsVZDtKH1gIeN5GDQPwMI";
    private static final String SECRET_KEY = "MakNAT5ndMACLdUsDrxjSBT61E85Mmdw";

    AipFace client = new AipFace(APP_ID,API_KEY,SECRET_KEY);


    //新增用户
    @PostMapping("/create")
    public ResultVO create(FaceUserVO faceUserVO, @RequestParam(value = "facePic") MultipartFile facePic) throws IOException, UserException {
        if (facePic.isEmpty()) {
            log.error("文件为空");
        }

        if(faceUserVO == null) {
            log.error("NullPointError");

            throw new UserException(500,"传入参数为空");
        }

        String result=null;
       //base64编码
        try{
            byte[] faceByte=facePic.getBytes();

            result=Base64.getEncoder().encodeToString(faceByte);

        } catch (IOException ex) {
            log.error(ex.toString());
        }

        FaceUser faceUser=new FaceUser();

        BeanUtils.copyProperties(faceUserVO,faceUser);

        client = new AipFace(APP_ID,API_KEY,SECRET_KEY);
        //TODO  新增人脸
        JSONObject res=client.addUser(result,"BASE64",faceUserVO.getGroupId(),faceUserVO.getFaceBaiduId(),null);

        log.info(res.toString());

        //[判断是否上传成功
        if (res.get("error_msg") == "SUCCESS"){
            faceUser.setEnterTime(new Date());

            faceUserService.save(faceUser);

            return ResultVOUtil.success(faceUser);


        }
        return ResultVOUtil.error(500, (String) res.get("error_msg"),faceUserVO);
    }

    //删除用户
    @PostMapping("/delete")
    public ResultVO delete(Long faceId) throws UserException {
        FaceUser result=faceUserService.findOne(faceId);

        if(result == null) {
            throw new UserException(500,"用户未找到");
        }

        //从百度云中删除用户
        JSONObject res=client.deleteUser(result.getGroupId(),result.getFaceBaiduId(),null);

        log.info(res.toString());

        if (res.get("error_msg") == "SUCCESS") {
            faceUserService.delete(faceId);

            return ResultVOUtil.success(result);
        }


        FaceUserVO faceUserVO=new FaceUserVO();

        BeanUtils.copyProperties(result,faceUserVO);

        return ResultVOUtil.error(500,(String) res.get("error_msg"),result);
    }

    //获取所有用户的信息
    @GetMapping("/listall")
    public ResultVO listAll() {
        JSONObject list=client.getGroupList(null);

        log.info(list.toString());

        List<FaceUser> result=faceUserService.findAll();

        return ResultVOUtil.success(result);
    }

    @PostMapping("/update")
    public ResultVO update(FaceUserVO faceUserVO) throws UserException {
        if(faceUserVO == null) {
            log.error("NullPointError");

            throw new UserException(500,"传入参数为空");
        }
        FaceUser faceUser=new FaceUser();

        BeanUtils.copyProperties(faceUserVO,faceUser);

        FaceUser result=faceUserService.update(faceUser);
        //TODO 已解决

        return ResultVOUtil.success(result);
    }
}
