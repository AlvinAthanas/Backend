package com.example.cms_backend.Model.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private Long parishId;
    private Long organizerId;
    private String organizerName;
    private String organizerPhone;

    public EventDTO(Long id,
                    String name,
                    String description,
                    String location,
                    LocalDateTime dateTime,
                    Long parishId,
                    Long organizerId,
                    String organizerName,
                    String organizerPhone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.parishId = parishId;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.organizerPhone = organizerPhone;
    }
}
