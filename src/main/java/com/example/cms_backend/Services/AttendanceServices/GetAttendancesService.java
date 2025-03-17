package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAttendancesService {
    private final AttendanceRepository attendanceRepository;

    public GetAttendancesService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
