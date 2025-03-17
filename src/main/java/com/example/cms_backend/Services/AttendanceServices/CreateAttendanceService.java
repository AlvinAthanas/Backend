package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAttendanceService {
    private final AttendanceRepository attendanceRepository;

    public CreateAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
