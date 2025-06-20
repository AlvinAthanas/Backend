package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Repositories.AttendanceRepository;
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

