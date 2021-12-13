package com.yoongspace.demo.DTO;

import com.yoongspace.demo.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserlistDTO {
    private String username;
    private String userinfo;
    private String grade;
    private String photo;
    private String studentid;
    @Builder.Default private boolean myfriend=false;

    public UserlistDTO(final UserEntity entity){
        this.username=entity.getUsername();
        this.userinfo=entity.getUserinfo();
        this.grade=entity.getGrade();
        this.studentid=entity.getStudentid();
        this.photo=entity.getPhoto();
    }

}
