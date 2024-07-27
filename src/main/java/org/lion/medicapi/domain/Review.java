package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@ToString(exclude = {"likeList", "user", "product"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double starPoint;    // 별점
    private String purchaseDate; // 제품 구매일
    private String reviewDate;   // 후기 작성일
    private Integer ageGroup;    // 나이대 (10, 20, 30 ... 대)

    @OneToMany(mappedBy = "review")
    private List<Like> likeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * 생년월일 입력시, 나이대가 계산되어 세팅되는 메서드 <br/>
     * 리뷰 작성시, 사용하시면 됩니다.
     *
     * @param birthDt : 작성자의 생년월일
     */
    public void calculateAgeGroup(final String birthDt) {
        final int year = LocalDateTime.now().getYear() - 2000;
        final int birthYear = Integer.parseInt(birthDt.substring(0, 2));

        int age;
        if (birthYear > year) {
            age = 100 - birthYear + year + 1;
        } else {
            age = year - birthYear + 1;
        }

        this.ageGroup = (age / 10) * 10;
    }
}
