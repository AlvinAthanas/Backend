package com.example.cms_backend.Services.AttendanceServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.AttendanceNotFoundException;
import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Model.UpdateCommands.UpdateAttendanceCommand;
import com.example.cms_backend.Repositories.AttendanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAttendanceService implements Command<UpdateAttendanceCommand, Attendance> {
    private final AttendanceRepository attendanceRepository;

    public UpdateAttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public ResponseEntity<Attendance> execute(UpdateAttendanceCommand command) {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(command.getId());
        if (attendanceOptional.isPresent()) {
            Attendance attendance = command.getAttendance();
            attendance.setId(command.getId());
            attendanceRepository.save(attendance);
            return ResponseEntity.ok(attendance);
        }
         throw new AttendanceNotFoundException();
    }
}
