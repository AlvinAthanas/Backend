package com.example.cms_backend.services.ParishServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.SearchParishCommand;
import com.example.cms_backend.model.DTO.ParishWithCommunitiesDTO;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchParishWithCommunitiesService implements Query<SearchParishCommand, List<ParishWithCommunitiesDTO>> {

    private final ParishRepository parishRepository;

    public SearchParishWithCommunitiesService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<List<ParishWithCommunitiesDTO>> execute(SearchParishCommand command) {
        List<Parish> parishes = parishRepository.findByNameContaining(command.getName());

        List<ParishWithCommunitiesDTO> result = parishes.stream().map(parish -> {
            ParishWithCommunitiesDTO dto = new ParishWithCommunitiesDTO();
            dto.setId(parish.getId());
            dto.setName(parish.getName());

            // ... other basic fields

            if (command.isIncludeCommunities()) {
                List<ParishWithCommunitiesDTO.CommunityDTO> communities = parish.getGroups().stream()
                        .map(group -> {
                            ParishWithCommunitiesDTO.CommunityDTO c = new ParishWithCommunitiesDTO.CommunityDTO();
                            c.setId(group.getId());
                            c.setName(group.getName());
                            return c;
                        }).toList();
                dto.setCommunities(communities);
            }

            return dto;
        }).toList();

        return ResponseEntity.ok(result);
    }
}

