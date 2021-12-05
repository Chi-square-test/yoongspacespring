package com.yoongspace.demo.service;

import com.yoongspace.demo.model.UserEntity;
import com.yoongspace.demo.persistence.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepo userrepo;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity==null||userEntity.getEmail()==null){
            throw new RuntimeException("INvalid arguments");
        }
        final  String email = userEntity.getEmail();
        final String studentid=userEntity.getStudentid();
        if (userrepo.existsByEmail(email)){
            log.warn("email already exists {}",email);
            throw  new RuntimeException("Email already exists");
        }
        else if (userrepo.existsByStudentid(studentid)){
            log.warn("studentid already exists {}",email);
            throw  new RuntimeException("Student already exists");
        }
        return userrepo.save(userEntity);
    }

    public UserEntity getByCredentials(final String studentid, final String password, final PasswordEncoder encoder){
        final UserEntity orginUser = userrepo.findByStudentid(studentid);
        log.info(String.valueOf(orginUser));
        if(orginUser != null && encoder.matches(password,orginUser.getPassword())){
            return orginUser;
        }
        return null;

    }
}
