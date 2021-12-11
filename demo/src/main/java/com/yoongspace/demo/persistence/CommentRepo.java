package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity,String> {
    List<CommentEntity> findByFeedid(String feedid);
    int countByIdAndStudentid(Long id,String studentid);
}
