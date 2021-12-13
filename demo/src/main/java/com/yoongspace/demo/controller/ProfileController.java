package com.yoongspace.demo.controller;


import com.yoongspace.demo.service.ProfileService;
import com.yoongspace.demo.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/yoongfprofile")
public class ProfileController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<?> uploadProfilePicture(@AuthenticationPrincipal String studentid, MultipartFile multipartFile)throws IOException{
        log.info(String.valueOf(multipartFile));
        String imgPath = s3Service.upload(multipartFile,studentid);
        profileService.saveprofileimage(studentid,imgPath);
        return ResponseEntity.ok().body(studentid);
    }
}
