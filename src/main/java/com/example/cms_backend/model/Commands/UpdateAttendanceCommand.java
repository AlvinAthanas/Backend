package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Attendance;
import lombok.Getter;

@Getter
public class UpdateAttendanceCommand {
    private Long id;
    private Attendance attendance;

    public UpdateAttendanceCommand(Long id, Attendance attendance) {
        this.id = id;
        this.attendance = attendance;
    }

}
