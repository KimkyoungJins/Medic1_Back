package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.Product;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.domain.UserHealthTag;
import org.lion.medicapi.exception.APIException;
import org.lion.medicapi.repository.ProductRepository;
import org.lion.medicapi.repository.UserHealthTagRepository;
import org.lion.medicapi.repository.UserRepository;
import org.lion.medicapi.util.HealthTag;
import org.lion.medicapi.util.ProductSort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserHealthTagRepository userHealthTagRepository;

    public List<Product> getRecommendProducts(final User principal) {
        final User user = userRepository.findByEmail(principal.getEmail())
                .orElseThrow(() -> new APIException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        log.info("user[{}]", user);

        final List<Product> result = new ArrayList<>();

        final List<HealthTag> healthTags = userHealthTagRepository.findByUser(user).stream().map(UserHealthTag::getHealthTag).toList();
        healthTags.forEach(healthTag -> {
            log.info("healthTags[{}]", healthTags);

            final List<Product> products = productRepository.findTopByHealthTag(healthTag)
                    .stream()
                    .limit(2)
                    .toList();
            result.addAll(products);
        });

        return result;
    }

    public List<Product> getBestProducts() {
        final List<Product> products = productRepository.findAll();
        Collections.shuffle(products);

        return products.stream().limit(3).toList();
    }

    public List<Product> getProducts(final ProductSort sort) {
        final List<Product> productList = productRepository.findAll();
        log.info("productList[{}]", productList);

        switch (sort) {
            case REVIEW -> productList.sort(Comparator.comparing(product -> product.getReviewList().size()));
            case LOW_PRICE -> productList.sort(Comparator.comparing(Product::getNormalPrice));
            case HIGH_PRICE -> productList.sort(Comparator.comparing(Product::getNormalPrice).reversed());
        }

        return productList;
    }

    public Product getProductDetails(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new APIException("해당 ID의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }
}
