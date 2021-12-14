package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepo extends JpaRepository<FeedEntity, String> {
    int countByIdAndStudentid(Long id,String studentid);
    List<FeedEntity> findByOnlyfriend(boolean onlyfriend);
    List<FeedEntity> findByOnlyfriendAndStudentid(boolean onlyfriend,String studentid);//주용도 onlyf로 내가 쓴글 찾기
    List<FeedEntity> findByStudentid(String studentid);
    List<FeedEntity> findByOnlyfriendAndAndCat(boolean onlyfriend,String cat);

    Optional<FeedEntity> findById(Long id);
}
