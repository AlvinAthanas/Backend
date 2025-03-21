package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Model.UpdateCommands.UpdateAttendanceCommand;
import com.example.cms_backend.Services.AttendanceServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttendanceController {
    private final CreateAttendanceService createAttendanceService;
    private final UpdateAttendanceService updateAttendanceService;
    private final DeleteAttendanceService deleteAttendanceService;
    private final GetAttendanceService getAttendanceService;
    private final GetAttendancesService getAttendancesService;

    public AttendanceController(CreateAttendanceService createAttendanceService,
                                UpdateAttendanceService updateAttendanceService,
                                DeleteAttendanceService deleteAttendanceService,
                                GetAttendanceService getAttendanceService,
                                GetAttendancesService getAttendancesService) {
        this.createAttendanceService = createAttendanceService;
        this.updateAttendanceService = updateAttendanceService;
        this.deleteAttendanceService = deleteAttendanceService;
        this.getAttendanceService = getAttendanceService;
        this.getAttendancesService = getAttendancesService;
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return createAttendanceService.execute(attendance);
    }

    @GetMapping("/attendance/{id}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable Long id) {
        return getAttendanceService.execute(id);
    }

    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getAttendances() {
        return  getAttendancesService.execute(null);
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<Attendance>  updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        return updateAttendanceService.execute(new UpdateAttendanceCommand(id, attendance));
    }

    @DeleteMapping("/attendance/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        return deleteAttendanceService.execute(id);
    }
}
