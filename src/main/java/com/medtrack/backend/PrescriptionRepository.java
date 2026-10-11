
package com.medtrack.backend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Long> {

    List<Prescription> findAllByUser_Email(String email);

    Optional<Prescription> findByIdAndUser_Email(
            Long id, String email);
}
