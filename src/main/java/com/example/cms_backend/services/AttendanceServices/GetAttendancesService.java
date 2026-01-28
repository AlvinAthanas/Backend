package com.example.cms_backend.services.AttendanceServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Attendance;
import com.example.cms_backend.repositories.AttendanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAttendancesService implements Query<Long, List<Attendance>> {

    private final AttendanceRepository attendanceRepository;

    public GetAttendancesService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<List<Attendance>> execute(Long userId) {
        List<Attendance> attendances;

        if (userId == null) {
            attendances = attendanceRepository.findAll();
        } else {
            attendances = attendanceRepository.findByUserId(userId);
        }

        return ResponseEntity.ok(attendances);
    }
}

