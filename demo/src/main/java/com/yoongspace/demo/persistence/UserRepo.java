package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByStudentid(String email);
    UserEntity findByStudentid(String studentid);
}
