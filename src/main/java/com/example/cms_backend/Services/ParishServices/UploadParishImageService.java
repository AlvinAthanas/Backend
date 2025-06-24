package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.UploadParishImageCommand;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadParishImageService implements Command<UploadParishImageCommand, String> {

    private final ParishRepository parishRepository;
    private final String UPLOAD_DIR = "C:/xampp/htdocs/Church Project/Church-Management-System-repo/img/";

    public UploadParishImageService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<String> execute(UploadParishImageCommand command) {
        Optional<Parish> optionalParish = parishRepository.findById(command.getParishId());
        if (optionalParish.isEmpty()) return ResponseEntity.notFound().build();

        MultipartFile file = command.getFile();

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Parish parish = optionalParish.get();
            String relativePath = "/Church Project/Church-Management-System-repo/img/" + filename;
            parish.setImageUrl(relativePath);
            parishRepository.save(parish);

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
}
