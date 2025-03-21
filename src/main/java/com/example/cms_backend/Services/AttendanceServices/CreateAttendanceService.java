package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateAttendanceService implements Command<Attendance,Attendance> {
    private final AttendanceRepository attendanceRepository;

    public CreateAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<Attendance> execute(Attendance attendance) {
        attendanceRepository.save(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }
}
