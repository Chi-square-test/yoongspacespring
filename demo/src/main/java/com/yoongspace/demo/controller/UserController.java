package com.yoongspace.demo.controller;

import com.yoongspace.demo.DTO.ResponseDTO;
import com.yoongspace.demo.DTO.UserDTO;
import com.yoongspace.demo.model.UserEntity;
import com.yoongspace.demo.security.TokenProvider;
import com.yoongspace.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/yoongauth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            UserEntity user = UserEntity.builder()
                    .studentid(userDTO.getStudentid())
                    .grade(userDTO.getGrade())
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();
            UserEntity regiUser = userService.create(user);
            UserDTO resDTO = userDTO.builder()
                    .email(regiUser.getEmail())
                    .studentid(regiUser.getStudentid())
                    .username(regiUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(resDTO);
        }catch (Exception e){
            ResponseDTO responseDTO =ResponseDTO.builder().error(e.getMessage()).build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(
                userDTO.getStudentid(),
                userDTO.getPassword(),
                passwordEncoder);

        if(user !=null){
            final String token =tokenProvider.create(user);
            final UserDTO resuserDTO = userDTO.builder()
                    .studentid(user.getStudentid())
                    .token(token)
                    .build();
            return  ResponseEntity.ok().body(resuserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed.")
                    .build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
