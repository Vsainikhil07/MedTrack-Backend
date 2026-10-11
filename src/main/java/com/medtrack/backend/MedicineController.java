
package com.medtrack.backend;

import java.util.List;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    public MedicineController(
            MedicineRepository medicineRepository,
            UserRepository userRepository) {
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @GetMapping
    public List<Medicine> getAllMedicines(
            org.springframework.security.core.Authentication auth) {
        return medicineRepository.findAllByUser_Email(auth.getName());
    }

    @PostMapping
    public Medicine addMedicine(
            @RequestBody Medicine medicine,
            org.springframework.security.core.Authentication auth) {
        medicine.setUser(getUser(auth.getName()));
        return medicineRepository.save(medicine);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(
            @PathVariable Long id,
            @RequestBody Medicine updated,
            org.springframework.security.core.Authentication auth) {

        Medicine medicine = medicineRepository
                .findByIdAndUser_Email(id, auth.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Medicine not found"));

        medicine.setName(updated.getName());
        medicine.setDosage(updated.getDosage());
        medicine.setQuantity(updated.getQuantity());
        medicine.setFrequency(updated.getFrequency());

        return medicineRepository.save(medicine);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(
            @PathVariable Long id,
            org.springframework.security.core.Authentication auth) {

        long deleted = medicineRepository
                .deleteByIdAndUser_Email(id, auth.getName());

        if (deleted == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Medicine not found");
        }
    }
}
