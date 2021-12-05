package com.yoongspace.demo.persistence;

import com.yoongspace.demo.model.TodaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodaysRepo extends JpaRepository<TodaysEntity, String> {
}
