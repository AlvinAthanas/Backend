package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Attendance;
import com.example.cms_backend.Model.Commands.UpdateAttendanceCommand;
import com.example.cms_backend.Services.AttendanceServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('COMMUNITY_CHAIRPERSON') or hasRole('COMMUNITY_SECRETARY') or hasRole('COMMUNITY_TREASURER') or hasRole('PARISHIONER')")
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

    @PreAuthorize("hasRole('PARISH_MEMBER') or hasRole('COMMUNITY_CHAIRPERSON') or hasRole('COMMUNITY_SECRETARY') or hasRole('COMMUNITY_TREASURER')")
    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(
            @RequestParam(value = "userId", required = false) Long userId) {
        return getAttendancesService.execute(userId);
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
