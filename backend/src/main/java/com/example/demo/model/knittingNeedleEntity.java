package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="KnittingNeedle")
public class knittingNeedleEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; //제품 아이디

    private String brand; // 뜨개 바늘 브랜드
    private String title; // 뜨개 바늘 이름
    private Integer price; // 뜨개 바늘 가격
    private String userId; // 판매인
}
