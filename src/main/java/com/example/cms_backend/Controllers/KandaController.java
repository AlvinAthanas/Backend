package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.SearchKandaCommand;
import com.example.cms_backend.Model.Commands.UpdateKandaCommand;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Services.KandaServices.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private final SearchKandaService searchKandaService;


    public KandaController(CreateKandaService createService, GetKandaService getService,
                           GetAllKandaService getAllKandaService, UpdateKandaService updateService,
                           DeleteKandaService deleteService,
                           CreateManyKandaService createManyKandaService,
                           SearchKandaService searchKandaService) {
        this.createService = createService;
        this.getService = getService;
        this.getAllKandaService = getAllKandaService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.createManyKandaService = createManyKandaService;
        this.searchKandaService = searchKandaService;
    }

    @PostMapping("/kanda")
    public ResponseEntity<Kanda> create(@RequestBody Kanda kanda) {
        return createService.execute(kanda);
    }

    @PostMapping("/kandas")
    public ResponseEntity<List<Kanda>> createMany(@RequestBody List<Kanda> kandas) {
        return createManyKandaService.execute(kandas);
    }

    @GetMapping("/kanda/search")
    public ResponseEntity<List<Kanda>> searchKandas(
            @RequestParam(required = false) String name,
            HttpServletRequest request
    ) {
        SearchKandaCommand command = new SearchKandaCommand(name, request);
        return searchKandaService.execute(command);
    }


    @GetMapping("/kanda/{id}")
    public ResponseEntity<Kanda> getKandaById(@PathVariable Long id) {
        return getService.execute(id);
    }

    @GetMapping("/kandas")
    public ResponseEntity<List<Kanda>> getKandas(HttpServletRequest request) {
        return getAllKandaService.execute(request);
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

