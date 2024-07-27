package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lion.medicapi.util.HealthTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"reviewList"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName; // 제품명
    private String manufacturerName; // 제조사명
    private Long normalPrice; // 정상 가격
    private Long salePrice; // 판매가

    @Enumerated(EnumType.STRING)
    private HealthTag healthTag; // 건강 태그

    private String reportNumber; // 신고 번호
    private String registerDate; // 등록 일자 (yyyyMMdd 꼴)
    private String expirationDate; // 유통기한 (ex. 제조일로부터 24개월)
    private String intakeMethod; // 섭취량/섭취 방법
    private String ingestPrecaution; // 섭취 시 주의사항
    private String functionallyContents; // 기능성 내용
    private String otherMaterials; // 기타 원료

    @OneToOne
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;

    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList<>();
}
