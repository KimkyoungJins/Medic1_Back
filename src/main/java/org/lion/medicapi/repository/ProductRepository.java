package org.lion.medicapi.repository;

import org.lion.medicapi.domain.Product;
import org.lion.medicapi.util.HealthTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTopByHealthTag(HealthTag healthTag);
}
