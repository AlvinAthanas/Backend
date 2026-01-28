package com.example.cms_backend.services.AttendanceServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.AttendanceNotFoundException;
import com.example.cms_backend.model.Entities.Attendance;
import com.example.cms_backend.repositories.AttendanceRepository;
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
