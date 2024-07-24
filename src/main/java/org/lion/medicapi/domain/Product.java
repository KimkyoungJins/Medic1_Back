package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.lion.medicapi.util.HealthTag;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Enumerated(EnumType.STRING)
    private HealthTag healthTag;

    // TODO : 컬럼 추가 예정
}
