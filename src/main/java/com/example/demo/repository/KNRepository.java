package com.example.demo.repository;

import com.example.demo.model.knittingNeedleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KNRepository extends JpaRepository<knittingNeedleEntity, String> {

    List<knittingNeedleEntity> findByUserId(String userId);

    List<knittingNeedleEntity> findAllByTitleContaining(String title);
}
