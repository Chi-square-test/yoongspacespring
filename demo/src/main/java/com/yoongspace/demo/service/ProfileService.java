package com.yoongspace.demo.service;



import com.yoongspace.demo.model.UserEntity;
import com.yoongspace.demo.persistence.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    UserRepo userRepo;

    public void saveprofileimage(String studentid,String S3url) {
        UserEntity userEntity = userRepo.findByStudentid(studentid);
        userEntity.setPhoto(S3url);
        userRepo.save(userEntity);
    }

    public void savetextinform(String studentid, String informtext) {
        UserEntity userEntity = userRepo.findByStudentid(studentid);
        userEntity.setUserinfo(informtext);
        userRepo.save(userEntity);
    }

}
