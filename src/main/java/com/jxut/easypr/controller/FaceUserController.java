package com.jxut.easypr.controller;

import com.baidu.aip.face.AipFace;
import com.jxut.easypr.VO.FaceUserVO;
import com.jxut.easypr.VO.ResultVO;
import com.jxut.easypr.entity.FaceUser;
import com.jxut.easypr.entity.User;
import com.jxut.easypr.exception.UserException;
import com.jxut.easypr.service.FaceUserService;
import com.jxut.easypr.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    @Transactional
    @PostMapping("/create")
    public ResultVO create(@Valid FaceUserVO faceUserVO, @RequestParam(value = "facePic") MultipartFile facePic) throws IOException, UserException {
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        if (facePic.isEmpty()) {
            log.error("文件为空");

            throw new UserException(500,"文件夹为空",faceUserVO,savePoint);
        }

        if(faceUserVO == null) {
            log.error("NullPointError");

            throw new UserException(500,"参数为空",faceUserVO,savePoint);
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
        if (res.get("error_msg") != "SUCCESS"){

            log.error((String)res.get("error_msg"));
            throw new UserException(500,(String) res.get("error_msg"),faceUserVO,savePoint);

        }
        return ResultVOUtil.error(500, (String) res.get("error_msg"),faceUserVO);
    }

    //删除用户
    @Transactional
    @PostMapping("/delete")
    public ResultVO delete(Long faceId) throws UserException {
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        FaceUser result=faceUserService.findOne(faceId);

        if(result == null) {
            throw new UserException(500,"用户未找到",null,savePoint);
        }

        //从百度云中删除用户
        JSONObject res=client.deleteUser(result.getGroupId(),result.getFaceBaiduId(),null);

        log.info(res.toString());

        if (res.get("error_msg") != "SUCCESS") {
            FaceUserVO faceUserVO=new FaceUserVO();

            BeanUtils.copyProperties(result,faceUserVO);

            log.error((String)res.get("error_msg"));

            throw new UserException( 500,(String) res.get("error_msg"),res,savePoint);
        }

        faceUserService.delete(faceId);

        return ResultVOUtil.success(result);



    }

    //获取所有用户的信息
    @GetMapping("/listall")
    public ResultVO listAll() {
        JSONObject list=client.getGroupList(null);

        log.info(list.toString());

        List<FaceUser> result=faceUserService.findAll();

        return ResultVOUtil.success(result);
    }
    //更新用户
    @Transactional
    @PostMapping("/update")
    public ResultVO update(@Valid FaceUserVO faceUserVO) throws UserException {
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        if(faceUserVO == null) {
            log.error("NullPointError");

            throw new UserException(500,"传入参数为空",null,savePoint);
        }
        FaceUser faceUser=new FaceUser();

        BeanUtils.copyProperties(faceUserVO,faceUser);

        FaceUser result=faceUserService.update(faceUser);
        //TODO 已解决

        return ResultVOUtil.success(result);
    }

    //查找单个用户
    @GetMapping("/search")
    public ResultVO findOne(Long faceId) throws UserException {
        if(faceId == null) {
            log.error("NullPointError");

            return ResultVOUtil.error(500,"NullPointException",null);
        }

        FaceUser result=faceUserService.findOne(faceId);

        return ResultVOUtil.success(result);
    }

    //更新人脸
    @PostMapping("/updateface")
    public ResultVO updateface(Long faceId,@RequestParam(value = "facePic") MultipartFile facePic) throws UserException {
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

        FaceUser source=faceUserService.findOne(faceId);

        String result = null;
        //base64编码
        try{
            byte[] faceByte=facePic.getBytes();

            result=Base64.getEncoder().encodeToString(faceByte);

        } catch (IOException ex) {
            log.error(ex.toString());
        }

        JSONObject res=client.updateUser(result,"BASE64",source.getGroupId(),source.getFaceBaiduId(),null);

        if(res.get("error_msg")!= "SUCCESS"){
            log.error((String) res.get("error_msg"));

            throw new UserException(500,(String) res.get("error_msg"),res,savePoint);
        }
        return ResultVOUtil.success(source);
    }

    //获取组列表
    @GetMapping("/grouplist")
    public ResultVO grouplist(){
        JSONObject res=client.getGroupList(null);
        log.info(res.toString());
        return ResultVOUtil.success(res.getJSONObject("result").get("group_id_list"));
    }
}
