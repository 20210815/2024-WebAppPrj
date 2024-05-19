package com.example.demo.repository;

import com.example.demo.model.knittingNeedleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface KNRepository extends JpaRepository<knittingNeedleEntity, String> {

    List<knittingNeedleEntity> findByUserId(String userId);

    @Transactional
    void deleteByTitle(String title);
    Optional<knittingNeedleEntity> findByTitle(String title);
}
