
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
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;

    public PrescriptionController(
            PrescriptionRepository prescriptionRepository,
            UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @GetMapping
    public List<Prescription> getAllPrescriptions(Authentication auth) {
        return prescriptionRepository.findAllByUser_Email(auth.getName());
    }

    @PostMapping
    public Prescription addPrescription(
            @RequestBody Prescription prescription,
            Authentication auth) {
        prescription.setUser(getUser(auth.getName()));
        return prescriptionRepository.save(prescription);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(
            @PathVariable Long id,
            @RequestBody Prescription updated,
            Authentication auth) {

        Prescription prescription = prescriptionRepository
                .findByIdAndUser_Email(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Prescription not found"));

        prescription.setDoctorName(updated.getDoctorName());
        prescription.setMedicineName(updated.getMedicineName());
        prescription.setInstructions(updated.getInstructions());
        prescription.setPrescriptionDate(updated.getPrescriptionDate());

        return prescriptionRepository.save(prescription);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(
            @PathVariable Long id, Authentication auth) {

        Prescription prescription = prescriptionRepository
                .findByIdAndUser_Email(id, auth.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Prescription not found"));

        prescriptionRepository.delete(prescription);
    }
}
