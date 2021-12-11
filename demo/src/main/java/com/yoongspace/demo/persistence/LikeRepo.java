package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<LikeEntity,String> {
    boolean existsByFeedidAndStudentid(String id,String studentid);
    LikeEntity findByFeedidAndStudentid(String id,String studentid);
    Long countByFeedidAndStudentid(String id,String studentid);
}
