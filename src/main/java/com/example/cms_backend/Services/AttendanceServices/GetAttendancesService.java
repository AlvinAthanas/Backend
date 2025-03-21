package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAttendancesService implements Query<Void, List<Attendance>> {
    private final AttendanceRepository attendanceRepository;

    public GetAttendancesService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<List<Attendance>> execute(Void input) {
        List<Attendance> attendances = attendanceRepository.findAll();
        return ResponseEntity.ok(attendances);
    }
}
