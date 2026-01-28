package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.*;
import com.example.cms_backend.model.DTO.ParishWithCommunitiesDTO;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.services.ParishServices.*;
import com.example.cms_backend.services.UserFavParishesServices.AddOrRemoveFavoriteParishService;
import com.example.cms_backend.services.UserFavParishesServices.GetAllFavoriteParishes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class ParishController {
    private final CreateParishService createParishService;
    private final DeleteParishService deleteParishService;
    private final UpdateParishService updateParishService;
    private final GetParishService getParishService;
    private final GetParishesService  getParishesService;
    private final SearchParishService searchParishService;
    private final CreateParishesService createParishesService;
    private final AddOrRemoveFavoriteParishService addOrRemoveFavoriteParishService;
    private final GetAllFavoriteParishes getAllFavoriteParishes;
    private final SearchParishWithCommunitiesService searchParishWithCommunitiesService;
    private final UploadParishImageService uploadParishImageService;


    
    
    @PostMapping("/parish")
    public ResponseEntity<Parish> createParish(@RequestBody Parish parish){
        return createParishService.execute(parish);
    }

    @PostMapping("/parishes")
    public ResponseEntity<List<Parish>> createParishes(@RequestBody List<Parish> parishes){
        return createParishesService.execute(parishes);
    }

    @PostMapping("/parish/favorite")
    public ResponseEntity<Boolean> addOrRemoveFavoriteParish(@RequestBody FavParishCommand command) {
        return addOrRemoveFavoriteParishService.execute(command);
    }


    @GetMapping("/user/{userId}/favorite-parishes")
    public ResponseEntity<List<Parish>> getFavoriteParishes(@PathVariable Long userId) {
        return getAllFavoriteParishes.execute(userId);
    }



    @GetMapping("/parish/{id}")
    public ResponseEntity<Parish> getUserParish(HttpServletRequest request, @PathVariable(required = false) Long id) {
        return getParishService.execute(new GetParishCommand(id, request));
    }

    @GetMapping("/parish")
    public ResponseEntity<Parish> getUserParish(HttpServletRequest request) {
        return getParishService.execute(new GetParishCommand(null, request));
    }

    @GetMapping("/parishes")
    public ResponseEntity<List<Parish>> getParishes(){
        return getParishesService.execute(null);
    }

    @GetMapping("/parish/search")
    public ResponseEntity<List<Parish>> getParishesSearch(@RequestParam String name){
        return searchParishService.execute(name);
    }

    @PostMapping("/parish/search")
    public ResponseEntity<List<ParishWithCommunitiesDTO>> searchParish(@RequestBody SearchParishCommand command) {
        return searchParishWithCommunitiesService.execute(command);
    }

    @PreAuthorize("hasAnyRole('PARISHIONER', 'COMMITTEE_CHAIRPERSON', 'COMMITTEE_SECRETARY', 'COMMITTEE_TREASURER')")
    @PutMapping("/parish/{id}")
    public ResponseEntity<Parish> updateParish(@PathVariable Long id, @RequestBody Parish parish){
        return updateParishService.execute(new UpdateParishCommand(id, parish));
    }

    @DeleteMapping("/parish/{id}")
    public ResponseEntity<Void> deleteParish(@PathVariable Long id){
        return deleteParishService.execute(id);
    }


    @PreAuthorize("hasAnyRole('PARISHIONER', 'COMMITTEE_CHAIRPERSON', 'COMMITTEE_SECRETARY', 'COMMITTEE_TREASURER')")
    @PostMapping("/parish/{id}/upload-image")
    public ResponseEntity<String> uploadParishImage(@PathVariable Long id,
                                                    @RequestParam("imageFile") MultipartFile file) {
        return uploadParishImageService.execute(new UploadParishImageCommand(id, file));
    }



}