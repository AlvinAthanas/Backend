package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.ChangePasswordCommand;
import com.example.cms_backend.Model.Commands.GetUserCommand;
import com.example.cms_backend.Model.Commands.UploadProfilePictureCommand;
import com.example.cms_backend.Model.DTO.ChangePasswordDTO;
import com.example.cms_backend.Model.DTO.UpdateUserDTO;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Commands.UpdateUserCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Services.UserServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class UserController {
    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;
    private final GetUsersService getUsersService;
    private final SearchUserService searchUserService;
    private final CreateUsersService createUsersService;
    private final CountUsersService countUsersService;
    private final ChangePasswordService changePasswordService;
    private final UploadProfilePictureService uploadProfilePictureService;

    public UserController(CreateUserService createUserService,
                          GetUserService getUserService,
                          UpdateUserService updateUserService,
                          DeleteUserService deleteUserService,
                          GetUsersService getUsersService,
                          SearchUserService searchUserService,
                          CreateUsersService createUsersService,
                          CountUsersService countUsersService,
                          ChangePasswordService changePasswordService,
                          UploadProfilePictureService uploadProfilePictureService) {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.updateUserService = updateUserService;
        this.deleteUserService = deleteUserService;
        this.getUsersService = getUsersService;
        this.searchUserService = searchUserService;
        this.createUsersService = createUsersService;
        this.countUsersService = countUsersService;
        this.changePasswordService = changePasswordService;
        this.uploadProfilePictureService = uploadProfilePictureService;
    }

//    @PreAuthorize("hasRole('basicuser')")
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            // Password was not provided → set a default password
            String defaultPassword = user.getEmail();
            user.setPassword(defaultPassword);
        }
        return  createUserService.execute(user);
    }

    @PreAuthorize("hasRole('COMMITTEE_CHAIRPERSON')")
    @PostMapping("/users")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<User> users){
        return createUsersService.execute(users);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String email) {
        return getUserService.execute(new GetUserCommand(id, email));
    }


//    @PreAuthorize("hasRole('ROLE_PARISH_MEMBER')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return getUsersService.execute(null);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        return updateUserService.execute(new UpdateUserCommand(id, dto));
    }

    @PutMapping("/user/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO dto) {
        return changePasswordService.execute(new ChangePasswordCommand(id, dto));
    }



    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return deleteUserService.execute(id);
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String name){
        return searchUserService.execute(name);
    }

//    @PreAuthorize("hasRole('ROLE_PARISH_MEMBER')")
    @GetMapping("/user/count")
    public ResponseEntity<Long> countUser(){
        return countUsersService.execute(null);
    }


    private final String imageDir = "uploads/profile_pictures"; // or wherever you store images

    @GetMapping("/user/profile-picture/{filename:.+}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(imageDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/user/{id}/upload-profile-picture")
    public ResponseEntity<Void> uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        UploadProfilePictureCommand command = new UploadProfilePictureCommand(id, file);
        return uploadProfilePictureService.execute(command);
    }

}
