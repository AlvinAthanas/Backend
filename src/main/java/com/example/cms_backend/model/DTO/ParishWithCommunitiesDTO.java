package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParishWithCommunitiesDTO {
    private Long id;
    private String name;
    // ... other basic parish fields

    private List<CommunityDTO> communities; // only included if requested

    // inner DTO
    @Setter
    @Getter
    public static class CommunityDTO {
        private Long id;
        private String name;
        // Add other community info if needed
    }
}

