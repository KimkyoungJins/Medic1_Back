package org.lion.medicapi.repository;

import org.lion.medicapi.domain.UserHealthTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHealthTagRepository extends JpaRepository<UserHealthTag, Long> {
}
