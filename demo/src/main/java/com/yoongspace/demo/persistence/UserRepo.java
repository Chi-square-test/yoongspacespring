package com.yoongspace.demo.persistence;

import com.yoongspace.demo.DTO.UserlistDTO;
import com.yoongspace.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByStudentid(String email);
    UserEntity findByStudentid(String studentid);
    List<UserEntity> findByStudentidNot(String studentid);

}
