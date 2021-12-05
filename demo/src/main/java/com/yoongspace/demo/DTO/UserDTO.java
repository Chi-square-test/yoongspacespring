package com.yoongspace.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String studentid;
    private String token;
    private String email;
    private String username;
    private String password;
    private String id;
    private  String userinfo;
    private  String grade;
}
