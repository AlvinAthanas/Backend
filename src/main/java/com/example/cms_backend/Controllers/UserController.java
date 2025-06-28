package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.*;
import com.example.cms_backend.Model.DTO.ChangePasswordDTO;
import com.example.cms_backend.Model.DTO.UpdateUserDTO;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.DTO.UserLeaderDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Services.AdminServices.CreateAdminUserService;
import com.example.cms_backend.Services.UserServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

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
    private final CreateAdminUserService createAdminUserService;
    private final GetUserLeadersService  getUserLeadersService;

    public UserController(CreateUserService createUserService,
                          GetUserService getUserService,
                          UpdateUserService updateUserService,
                          DeleteUserService deleteUserService,
                          GetUsersService getUsersService,
                          SearchUserService searchUserService,
                          CreateUsersService createUsersService,
                          CountUsersService countUsersService,
                          ChangePasswordService changePasswordService,
                          UploadProfilePictureService uploadProfilePictureService,
                          CreateAdminUserService createAdminUserService,
                          GetUserLeadersService getUserLeadersService) {
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
        this.createAdminUserService = createAdminUserService;
        this.getUserLeadersService = getUserLeadersService;
    }

    //TODO: Differentiate endpoints e.g updating user details and creating new user

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user, HttpServletRequest request){
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            // Password was not provided → set a default password
            String defaultPassword = user.getEmail();
            user.setPassword(defaultPassword);
        }
        return  createUserService.execute(user, request);
    }

    @PreAuthorize("hasRole('COMMITTEE_CHAIRPERSON')")
    @PostMapping("/users")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<User> users, HttpServletRequest request){
        return createUsersService.execute(users, request);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) String email) {
        return getUserService.execute(new GetUserCommand(id, email));
    }


    @PreAuthorize("hasAuthority('READ_MEMBERS')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpServletRequest request){
        return getUsersService.execute(null, request);
    }

//    @PreAuthorize("hasAuthority('WRITE_MEMBERS')")
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        return updateUserService.execute(new UpdateUserCommand(id, dto));
    }

    @PutMapping("/user/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO dto) {
        return changePasswordService.execute(new ChangePasswordCommand(id, dto));
    }


    @PreAuthorize("hasAuthority('WRITE_MEMBERS')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return deleteUserService.execute(id);
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam String name, HttpServletRequest request) {
        SearchUserCommand command = new SearchUserCommand(name, request);
        return searchUserService.execute(command);
    }


    @PreAuthorize("hasAuthority('READ_MEMBERS')")
    @GetMapping("/user/count")
    public ResponseEntity<Long> countUser(HttpServletRequest request){
        return countUsersService.execute(null, request);
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

    @PostMapping("/admin/register")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody User user) {
        return createAdminUserService.execute(user);
    }


    @GetMapping("/user/leaders")
    public ResponseEntity<List<UserLeaderDTO>> getUserLeaders(HttpServletRequest request) {
        return getUserLeadersService.execute(request);
    }

}
