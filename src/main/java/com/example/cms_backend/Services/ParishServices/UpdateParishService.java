package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ParishNotFoundException;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.Commands.UpdateParishCommand;
import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateParishService implements Command<UpdateParishCommand, Parish> {
    private final ParishRepository parishRepository;

    public UpdateParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    @CacheEvict(value = "parishesCache", allEntries = true)
    public ResponseEntity<Parish> execute(UpdateParishCommand command) {
        Optional<Parish> parishOptional = parishRepository.findById(command.getId());
        if (parishOptional.isPresent()) {
            Parish existing = parishOptional.get();
            Parish updated = command.getParish();

            // Update only non-null or desired fields
            existing.setName(updated.getName());
            existing.setParishPriest(updated.getParishPriest());
            existing.setLocation(updated.getLocation());
            existing.setContactInfo(updated.getContactInfo());
            existing.setImageUrl(updated.getImageUrl());
            existing.setParishPhoneNumber(updated.getParishPhoneNumber());
            existing.setEmail(updated.getEmail());
            existing.setHistory(updated.getHistory());
            existing.setStreet(updated.getStreet());
            existing.setCity(updated.getCity());
            existing.setRegion(updated.getRegion());
            existing.setPoBox(updated.getPoBox());
            existing.setFacebookLink(updated.getFacebookLink());
            existing.setTwitterLink(updated.getTwitterLink());
            existing.setInstagramLink(updated.getInstagramLink());

            // Optional: dioceseId
            if (updated.getDioceseId() != null) {
                existing.setDioceseId(updated.getDioceseId());
            }

            // 🚫 DO NOT overwrite the lists (users, groups, etc.) here
            // They remain unchanged unless explicitly updated in separate services

            parishRepository.save(existing);
            return ResponseEntity.ok(existing);
        }
        throw new ParishNotFoundException();
    }

}
