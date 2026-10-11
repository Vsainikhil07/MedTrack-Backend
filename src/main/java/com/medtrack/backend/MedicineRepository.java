
package com.medtrack.backend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findAllByUser_Email(String email);

    Optional<Medicine> findByIdAndUser_Email(Long id, String email);

    @Transactional
    long deleteByIdAndUser_Email(Long id, String email);
}
