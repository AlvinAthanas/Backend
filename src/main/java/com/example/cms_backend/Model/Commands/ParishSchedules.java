package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Entities.Parish;
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
