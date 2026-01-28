package com.example.cms_backend;

import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.model.Entities.Authority;
import com.example.cms_backend.model.Enums.Roles;
import com.example.cms_backend.repositories.DioceseRepository;
import com.example.cms_backend.repositories.ParishRepository;
import com.example.cms_backend.repositories.RoleRepository;
import com.example.cms_backend.repositories.AuthorityRepository;
import com.example.cms_backend.seed.DioceseSeedDto;
import com.example.cms_backend.seed.ParishSeedDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class BootstrapDataLoader implements CommandLineRunner {

    private final DioceseRepository dioceseRepository;
    private final ParishRepository parishRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 BootstrapDataLoader RUNNING");

        // =======================
        // Load Diocese
        // =======================
        Diocese savedDiocese = null;
        if (dioceseRepository.count() == 0) {
            System.out.println("Diocese table is empty. Loading bootstrap dioceses...");

            InputStream dioceseStream =
                    getClass().getClassLoader().getResourceAsStream("bootstrap/diocese.json");
            if (dioceseStream == null)
                throw new RuntimeException("🚨 Diocese JSON not found!");

            List<DioceseSeedDto> dioceses =
                    objectMapper.readValue(dioceseStream, new TypeReference<>() {});

            Diocese diocese = dioceses.stream().findFirst()
                    .map(d -> new Diocese(d.name, d.location, d.bishopName))
                    .orElseThrow(() -> new RuntimeException("No diocese data found"));

            savedDiocese = dioceseRepository.save(diocese);
            System.out.println("✅ Diocese loaded successfully");
        } else {
            savedDiocese = dioceseRepository.findAll().get(0); // assume first one
            System.out.println("ℹ️ Diocese already exists, using existing record");
        }

        // =======================
        // Load Parishes
        // =======================
        if (parishRepository.count() == 0) {
            System.out.println("Parish table is empty. Loading bootstrap parishes...");

            InputStream parishStream =
                    getClass().getClassLoader().getResourceAsStream("bootstrap/parish.json");
            if (parishStream == null)
                throw new RuntimeException("🚨 Parish JSON not found!");

            List<ParishSeedDto> parishes =
                    objectMapper.readValue(parishStream, new TypeReference<>() {});

            Diocese finalSavedDiocese = savedDiocese;
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

                parish.setDioceseId(finalSavedDiocese.getId());
                return parish;
            }).toList();

            parishRepository.saveAll(parishEntities);
            System.out.println("✅ Parishes loaded successfully");
        } else {
            System.out.println("ℹ️ Parishes already exist. Skipping population");
        }

        // =======================
        // Load Roles
        // =======================
        if (roleRepository.count() == 0) {
            System.out.println("Role table is empty. Loading default roles...");

            List<Role> roles = Stream.of(Roles.values())
                    .map(r -> new Role(r.getRoleName()))
                    .toList();

            roleRepository.saveAll(roles);
            System.out.println("✅ Roles loaded successfully");
        } else {
            System.out.println("ℹ️ Roles already exist. Skipping population");
        }

        // =======================
        // Load Authorities
        // =======================
        if (authorityRepository.count() == 0) {
            System.out.println("Authority table is empty. Loading default authorities...");

            List<Authority> authorities = Stream.of(com.example.cms_backend.model.Enums.Authority.values())
                    .map(a -> {
                        Authority auth = new Authority();
                        auth.setName(a.name());
                        return auth;
                    })
                    .toList();

            authorityRepository.saveAll(authorities);
            System.out.println("✅ Authorities loaded successfully");
        } else {
            System.out.println("ℹ️ Authorities already exist. Skipping population");
        }

        System.out.println("🎉 BootstrapDataLoader finished");
    }
}
