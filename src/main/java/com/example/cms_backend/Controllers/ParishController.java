package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.Commands.UpdateParishCommand;
import com.example.cms_backend.Services.ParishServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParishController {
    private final CreateParishService createParishService;
    private final DeleteParishService deleteParishService;
    private final UpdateParishService updateParishService;
    private final GetParishService getParishService;
    private final GetParishesService  getParishesService;
    private final SearchParishService searchParishService;

    public ParishController(CreateParishService createParishService,
                            DeleteParishService deleteParishService,
                            UpdateParishService updateParishService,
                            GetParishService getParishService,
                            GetParishesService getParishesService,
                            SearchParishService searchParishService) {
        this.createParishService = createParishService;
        this.deleteParishService = deleteParishService;
        this.updateParishService = updateParishService;
        this.getParishService = getParishService;
        this.getParishesService = getParishesService;
        this.searchParishService = searchParishService;
    }

    @PostMapping("/parish")
    public ResponseEntity<Parish> createParish(@RequestBody Parish parish){
        return createParishService.execute(parish);
    }

    @GetMapping("/parish/{id}")
    public ResponseEntity<Parish> getParish(@PathVariable Long id){
        return getParishService.execute(id);
    }

    @GetMapping("/parishes")
    public ResponseEntity<List<Parish>> getParishes(){
        return getParishesService.execute(null);
    }

    @GetMapping("/parish/search")
    public ResponseEntity<List<Parish>> getParishesSearch(@RequestParam String name){
        return searchParishService.execute(name);
    }

    @PutMapping("/parish/{id}")
    public ResponseEntity<Parish> updateParish(@PathVariable Long id, @RequestBody Parish parish){
        return updateParishService.execute(new UpdateParishCommand(id, parish));
    }

    @DeleteMapping("/parish/{id}")
    public ResponseEntity<Void> deleteParish(@PathVariable Long id){
        return deleteParishService.execute(id);
    }


}
