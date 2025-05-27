package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.UpdateKandaCommand;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Services.KandaServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class KandaController {
    private final CreateKandaService createService;
    private final GetKandaService getService;
    private final GetAllKandaService getAllKandaService;
    private final UpdateKandaService updateService;
    private final DeleteKandaService deleteService;
    private final CreateManyKandaService createManyKandaService;

    public KandaController(CreateKandaService createService, GetKandaService getService,
                           GetAllKandaService getAllKandaService, UpdateKandaService updateService,
                           DeleteKandaService deleteService,
                           CreateManyKandaService createManyKandaService) {
        this.createService = createService;
        this.getService = getService;
        this.getAllKandaService = getAllKandaService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.createManyKandaService = createManyKandaService;
    }

    @PostMapping("/kanda")
    public ResponseEntity<Kanda> create(@RequestBody Kanda kanda) {
        return createService.execute(kanda);
    }

    @PostMapping("/kandas")
    public ResponseEntity<List<Kanda>> createMany(@RequestBody List<Kanda> kandas) {
        return createManyKandaService.execute(kandas);
    }

    @GetMapping("/kanda/{id}")
    public ResponseEntity<Kanda> getById(@PathVariable Long id) {
        return getService.execute(id);
    }

    @GetMapping("/kandas")
    public ResponseEntity<List<Kanda>> getAll() {
        return getAllKandaService.execute(null);
    }

    @PutMapping("/kanda/{id}")
    public ResponseEntity<Kanda> update(@PathVariable Long id, @RequestBody Kanda kanda) {
        return updateService.execute(new UpdateKandaCommand(id, kanda));
    }

    @DeleteMapping("/kanda/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return deleteService.execute(id);
    }
}

