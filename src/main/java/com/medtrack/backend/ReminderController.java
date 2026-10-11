
package com.medtrack.backend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public ReminderController(
            ReminderRepository reminderRepository,
            UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @GetMapping
    public List<Reminder> getAllReminders(Authentication auth) {
        return reminderRepository.findAllByUser_Email(auth.getName());
    }

    @PostMapping
    public Reminder addReminder(
            @RequestBody Reminder reminder,
            Authentication auth) {
        reminder.setUser(getUser(auth.getName()));
        return reminderRepository.save(reminder);
    }

    @PutMapping("/{id}")
    public Reminder updateReminder(
            @PathVariable Long id,
            @RequestBody Reminder updated,
            Authentication auth) {

        Reminder reminder = reminderRepository
                .findByIdAndUser_Email(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reminder not found"));

        reminder.setMedicineName(updated.getMedicineName());
        reminder.setReminderTime(updated.getReminderTime());
        reminder.setFrequency(updated.getFrequency());

        return reminderRepository.save(reminder);
    }

    @DeleteMapping("/{id}")
    public void deleteReminder(
            @PathVariable Long id, Authentication auth) {

        Reminder reminder = reminderRepository
                .findByIdAndUser_Email(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reminder not found"));

        reminderRepository.delete(reminder);
    }
}