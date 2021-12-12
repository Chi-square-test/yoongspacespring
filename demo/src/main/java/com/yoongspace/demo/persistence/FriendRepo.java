package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.FriendEntity;
import com.yoongspace.demo.service.FriendService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepo extends JpaRepository<FriendEntity, String> {
    List<FriendEntity> findByFriendA(String friendA);
    Boolean existsByFriendAAndFriendB(String friendA,String friendB);
    FriendEntity findByFriendAAndFriendB(String friendA,String friendB);


}
