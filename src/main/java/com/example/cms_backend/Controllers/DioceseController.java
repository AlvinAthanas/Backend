package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Diocese;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.UpdateCommands.UpdateDioceseCommand;
import com.example.cms_backend.Model.UpdateCommands.UpdateEventCommand;
import com.example.cms_backend.Services.DioceseServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DioceseController {

    private final CreateDioceseService createDioceseService;
    private final DeleteDioceseService deleteDioceseService;
    private final UpdateDioceseService updateDioceseService;
    private final GetDioceseService getDioceseService;
    private final GetDiocesesService  getDiocesesService;

    public DioceseController(CreateDioceseService createDioceseService,
                             DeleteDioceseService deleteDioceseService,
                             UpdateDioceseService updateDioceseService,
                             GetDioceseService getDioceseService,
                             GetDiocesesService getDiocesesService) {
        this.createDioceseService = createDioceseService;
        this.deleteDioceseService = deleteDioceseService;
        this.updateDioceseService = updateDioceseService;
        this.getDioceseService = getDioceseService;
        this.getDiocesesService = getDiocesesService;
    }

    @PostMapping("/diocese")
    public ResponseEntity<Diocese> createDiocese(@RequestBody Diocese diocese) {
        return createDioceseService.execute(diocese);
    }

    @GetMapping("/diocese/{id}")
    public ResponseEntity<Diocese> getDiocese(@PathVariable Long id) {
        return getDioceseService.execute(id);
    }

    @GetMapping("/dioceses")
    public ResponseEntity<List<Diocese>> getDioceses() {
        return getDiocesesService.execute(null);
    }

    @PutMapping("/diocese/{id}")
    public ResponseEntity<Diocese> updateDiocese(@PathVariable Long id, @RequestBody Diocese diocese) {
        return updateDioceseService.execute(new UpdateDioceseCommand(id, diocese));
    }

    @DeleteMapping("/diocese/{id}")
    public ResponseEntity<Void> deleteDiocese(@PathVariable Long id) {
        return deleteDioceseService.execute(id);
    }
}
