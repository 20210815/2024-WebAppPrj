package com.example.demo.service;

import com.example.demo.model.knittingNeedleEntity;
import com.example.demo.repository.KNRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class KNService {

    @Autowired
    private KNRepository knRepository;


    public List<knittingNeedleEntity> create(final knittingNeedleEntity entity) {
        //맞게 들어온 정보들인지 확인 //각 컬럼에 null이 없는지 확인
        validate(entity);
        
        //후에, repository에 저장
        knRepository.save(entity);

        //저장되었음을 로그에 출력
        log.info("Entity id: {} is saved", entity.getId());
        
        //여태 저장된 모든 상품들을 list로 보내줌
        return knRepository.findByUserId(entity.getUserId());
    }

    public List<knittingNeedleEntity> searchByTitle(String title) {
        return knRepository.findAllByTitleContaining(title);
    }

    public List<knittingNeedleEntity> retrieve (final String userId) {
        return knRepository.findByUserId(userId);
    }

    public List<knittingNeedleEntity> updateKN(final knittingNeedleEntity entity) {
        validate(entity);

        final Optional<knittingNeedleEntity> original = knRepository.findById(entity.getId());
        log.info("Entity id: {} is get", entity.getId());

        original.ifPresent( kn -> {
            kn.setTitle(entity.getTitle());
            kn.setPrice(entity.getPrice());

            knRepository.save(kn);
        });

        return retrieve(entity.getUserId());
    }

    public List<knittingNeedleEntity> deleteKN(final knittingNeedleEntity entity) {
        validate(entity);

        try {
            knRepository.delete(entity);
        }
        catch (Exception e) {
            log.error("error deleting entity  ", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    public void validate(final knittingNeedleEntity entity) {
        if(entity.getTitle() == null) {
            log.warn("Entity title cannot be null");
            throw new RuntimeException("Entity title cannot be null");
        }

        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }


        if(entity.getPrice() == null) {
            log.warn("Entity price cannot be null");
            throw new RuntimeException("Entity price cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
