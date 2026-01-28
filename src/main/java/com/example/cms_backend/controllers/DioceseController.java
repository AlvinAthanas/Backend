package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.model.Commands.UpdateDioceseCommand;
import com.example.cms_backend.services.DioceseServices.*;
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
    private final SearchDioceseService searchDioceseService;
    private final CreateDiocesesService createDiocesesService;

    public DioceseController(CreateDioceseService createDioceseService,
                             DeleteDioceseService deleteDioceseService,
                             UpdateDioceseService updateDioceseService,
                             GetDioceseService getDioceseService,
                             GetDiocesesService getDiocesesService,
                             SearchDioceseService searchDioceseService,
                             CreateDiocesesService createDiocesesService) {
        this.createDioceseService = createDioceseService;
        this.deleteDioceseService = deleteDioceseService;
        this.updateDioceseService = updateDioceseService;
        this.getDioceseService = getDioceseService;
        this.getDiocesesService = getDiocesesService;
        this.searchDioceseService = searchDioceseService;
        this.createDiocesesService = createDiocesesService;
    }

    @PostMapping("/diocese")
    public ResponseEntity<Diocese> createDiocese(@RequestBody Diocese diocese) {
        return createDioceseService.execute(diocese);
    }

    @PostMapping("/dioceses")
    public ResponseEntity<List<Diocese>> createDioceses(@RequestBody List<Diocese> dioceses) {
        return createDiocesesService.execute(dioceses);
    }

    @GetMapping("/diocese/{id}")
    public ResponseEntity<Diocese> getDiocese(@PathVariable Long id) {
        return getDioceseService.execute(id);
    }

    @GetMapping("/dioceses")
    public ResponseEntity<List<Diocese>> getDioceses() {
        return getDiocesesService.execute(null);
    }

    @GetMapping("/diocese/search")
    public ResponseEntity<List<Diocese>> searchDioceseByName(String name) {
        return searchDioceseService.execute(name);
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
