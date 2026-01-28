package com.example.cms_backend.config;

import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.DioceseRepository;
import com.example.cms_backend.repositories.ParishRepository;
import com.example.cms_backend.seed.DioceseSeedDto;
import com.example.cms_backend.seed.ParishSeedDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BootstrapDataLoader implements CommandLineRunner {

    private final DioceseRepository dioceseRepository;
    private final ParishRepository parishRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        // ✅ Prevent duplicate inserts
        if (dioceseRepository.count() > 0) {
            System.out.println("ℹ️ Bootstrap data already exists. Skipping.");
            return;
        }

        // =======================
        // Load Diocese
        // =======================
        InputStream dioceseStream =
                getClass().getResourceAsStream("/bootstrap/diocese.json");

        List<DioceseSeedDto> dioceses =
                objectMapper.readValue(dioceseStream,
                        new TypeReference<>() {});

        Diocese diocese = dioceses.stream().findFirst()
                .map(d -> new Diocese(
                        d.name,
                        d.location,
                        d.bishopName
                ))
                .orElseThrow(() -> new RuntimeException("No diocese data found"));

        Diocese savedDiocese = dioceseRepository.save(diocese);

        // =======================
        // Load Parishes
        // =======================
        InputStream parishStream =
                getClass().getResourceAsStream("/bootstrap/parish.json");

        List<ParishSeedDto> parishes =
                objectMapper.readValue(parishStream,
                        new TypeReference<>() {});

        List<Parish> parishEntities = parishes.stream().map(p -> {
            Parish parish = new Parish();
            parish.setName(p.name);
            parish.setParishPriest(p.parishPriest);
            parish.setLocation(p.location);
            parish.setContactInfo(p.contactInfo);
            parish.setImageUrl(p.imageUrl);
            parish.setEmail(p.email);
            parish.setParishPhoneNumber(p.parishPhoneNumber);
            parish.setHistory(p.history);
            parish.setStreet(p.street);
            parish.setCity(p.city);
            parish.setRegion(p.region);
            parish.setPoBox(p.poBox);
            parish.setFacebookLink(p.facebookLink);
            parish.setTwitterLink(p.twitterLink);
            parish.setInstagramLink(p.instagramLink);

            // 🔑 THIS is the critical link
            parish.setDioceseId(savedDiocese.getId());

            return parish;
        }).toList();

        parishRepository.saveAll(parishEntities);

        System.out.println("✅ Bootstrap data loaded successfully");
    }
}
