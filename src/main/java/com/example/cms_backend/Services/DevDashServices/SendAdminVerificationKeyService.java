package com.example.cms_backend.Services.DevDashServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.AdminVerificationKey;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.AdminVerificationKeyRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendAdminVerificationKeyService implements Command<Long, String> {

    private final AdminVerificationKeyRepository keyRepo;
    private final JavaMailSender mailSender;

    public SendAdminVerificationKeyService(AdminVerificationKeyRepository keyRepo, JavaMailSender mailSender) {
        this.keyRepo = keyRepo;
        this.mailSender = mailSender;
    }

    @Override
    public ResponseEntity<String> execute(Long keyId) {
        AdminVerificationKey key = keyRepo.findById(keyId)
                .orElseThrow(() -> new IllegalArgumentException("Verification key not found for id: " + keyId));

        User user = key.getUser();
        if (user == null) {
            throw new IllegalStateException("No user associated with this verification key.");
        }

        String toEmail = user.getEmail();
        String subject = "Your Admin Verification Key";
        String body = "Hello " + user.getName() + ",\n\nYour verification key is: " + key.getKey();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            return ResponseEntity.ok("Verification key email sent to: " + toEmail);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send email: " + e.getMessage());
        }
    }
}
