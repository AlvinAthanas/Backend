package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Attendance;

public class UpdateAttendanceCommand {
    private Long id;
    private Attendance attendance;

    public UpdateAttendanceCommand(Long id, Attendance attendance) {
        this.id = id;
        this.attendance = attendance;
    }

    public Long getId() {
        return id;
    }

    public Attendance getAttendance() {
        return attendance;
    }
}
