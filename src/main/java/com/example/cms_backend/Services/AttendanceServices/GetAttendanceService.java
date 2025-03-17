package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAttendanceService {
    private final AttendanceRepository attendanceRepository;

    public GetAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
