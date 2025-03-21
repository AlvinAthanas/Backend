package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.UpdateCommands.UpdateUserCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Services.UserServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;
    private final GetUsersService getUsersService;

    public UserController(CreateUserService createUserService,
                          GetUserService getUserService,
                          UpdateUserService updateUserService,
                          DeleteUserService deleteUserService,
                          GetUsersService getUsersService) {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.updateUserService = updateUserService;
        this.deleteUserService = deleteUserService;
        this.getUsersService = getUsersService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        return  createUserService.execute(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        return  getUserService.execute(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return getUsersService.execute(null);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user){
        return updateUserService.execute(new UpdateUserCommand(id, user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return deleteUserService.execute(id);
    }

}
