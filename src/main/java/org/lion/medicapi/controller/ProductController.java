package org.lion.medicapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.service.ProductService;
import org.lion.medicapi.util.AuthenticationUtils;
import org.lion.medicapi.util.ProductSort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> getRecommendProducts() {
        final User user = AuthenticationUtils.getUser();

        return ResponseEntity.ok(productService.getRecommendProducts(user));
    }

    /**
     * BEST 추천 상품 목록 조회 -> 우선은 랜덤으로 처리 <br/>
     * 로그인하지 않아도 호출할 수 있는 API. <br/>
     * 따라서, 로그인하지 않았을 경우 메인페이지 중간에서도 이 API로 대체
     */
    @GetMapping("/best")
    public ResponseEntity<?> getBestProducts() {
        return ResponseEntity.ok(productService.getBestProducts());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getProducts(@RequestParam final ProductSort sort,
                                         @RequestParam(defaultValue = "0") final int pageNum,
                                         @RequestParam(defaultValue = "12") final int pageSize) {
        return ResponseEntity.ok(productService.getProducts(sort, pageNum, pageSize));
    }
}
