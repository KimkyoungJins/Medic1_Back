package org.lion.medicapi.repository;

import org.lion.medicapi.util.HealthTag;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.domain.UserHealthTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHealthTagRepository extends JpaRepository<UserHealthTag, Long> {

    Optional<UserHealthTag> findByUserAndHealthTag(User user, HealthTag healthTag);

    Boolean existsByUserAndHealthTag(User user, HealthTag healthTag);

    List<UserHealthTag> findByUser(User user);
}
