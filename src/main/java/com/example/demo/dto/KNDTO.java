package com.example.demo.dto;

import com.example.demo.model.knittingNeedleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KNDTO {
    private String id;
    private String title;
    private Integer price;


    public KNDTO(final knittingNeedleEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
    }

    public static knittingNeedleEntity toEntity(final KNDTO dto) {
        return knittingNeedleEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .build();
    }
}
