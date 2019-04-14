package com.jxut.easypr.service.Imp;

import com.jxut.easypr.entity.FaceUser;
import com.jxut.easypr.repository.FaceUserRepository;
import com.jxut.easypr.service.FaceUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 21:23 2019/4/12
 */
@Service
@Slf4j
public class FaceUserServiceImp implements FaceUserService {
    @Autowired
    private FaceUserRepository faceUserRepository;

    @Override
    public FaceUser findByBaiduId(String faceBaiduId) {
        return faceUserRepository.findOneByFaceBaiduId(faceBaiduId);
    }

    @Override
    public FaceUser save(FaceUser faceUser) {
        return faceUserRepository.save(faceUser);
    }

    @Override
    public void delete(Long faceId) {
        faceUserRepository.deleteById(faceId);
    }

    @Override
    public FaceUser findOne(Long faceId) {
        return faceUserRepository.findOneByFaceId(faceId);
    }

    @Override
    public List<FaceUser> findAll() {
        return faceUserRepository.findAll();
    }

    @Override
    @Transactional
    public FaceUser update(FaceUser faceUser) {
        if(faceUser.getFaceId() == null)
            return null;

        FaceUser source=faceUserRepository.findOneByFaceId(faceUser.getFaceId());

        source.copy(faceUser);

        return faceUserRepository.saveAndFlush(source);
    }


}
