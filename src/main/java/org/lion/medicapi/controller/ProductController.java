package org.lion.medicapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.service.ProductService;
import org.lion.medicapi.util.AuthenticationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/medic/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 메인페이지 중간 - 나의 건강해시태그 맞춤 추천 상품 조회 API <br/>
     * 로그인 했을 경우에만 호출해야 함. <br/>
     * 로그인하지 않았을 경우에는, 해당 페이지에 랜덤 추천 상품 조회를 호출 <br/>
     */
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendProduct() {
        final User user = AuthenticationUtils.getUser();

        return ResponseEntity.ok(productService.getRecommendProduct(user));
    }
}
