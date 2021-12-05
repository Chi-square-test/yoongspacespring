package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepo extends JpaRepository<FriendEntity, String> {
    List<FriendEntity> findByFriendA(String friendA);
}
