package com.example.cms_backend.services.AttendanceServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Attendance;
import com.example.cms_backend.repositories.AttendanceRepository;
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
