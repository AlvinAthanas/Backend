package com.example.cms_backend.services.AttendanceServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.AttendanceNotFoundException;
import com.example.cms_backend.model.Entities.Attendance;
import com.example.cms_backend.repositories.AttendanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetAttendanceService implements Query<Long, Attendance> {
    private final AttendanceRepository attendanceRepository;

    public GetAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<Attendance> execute(Long id) {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if (attendanceOptional.isPresent()) {
            return ResponseEntity.ok(attendanceOptional.get());
        }
        throw new AttendanceNotFoundException();
    }
}
