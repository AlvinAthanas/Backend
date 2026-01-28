package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.model.Entities.Parish;
import lombok.Data;

import java.util.ArrayList;
@Data
public class ParishSchedules {
    private String parishName;
    private ArrayList<Event> schedules = new ArrayList<>();

    public ParishSchedules(Parish parish, ArrayList<Event> schedules) {
        this.parishName = parish.getName();
        this.schedules = schedules;
    }
}
