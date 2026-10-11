
package com.medtrack.backend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findAllByUser_Email(String email);

    Optional<Reminder> findByIdAndUser_Email(Long id, String email);
}
