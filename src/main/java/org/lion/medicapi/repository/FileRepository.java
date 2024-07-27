package org.lion.medicapi.repository;

import org.lion.medicapi.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
}
