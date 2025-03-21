package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.AttendanceNotFoundException;
import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteAttendanceService implements Command<Long,Void> {
    private final AttendanceRepository attendanceRepository;

    public DeleteAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        if (attendanceOptional.isPresent()) {
            attendanceRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new AttendanceNotFoundException();
    }
}
