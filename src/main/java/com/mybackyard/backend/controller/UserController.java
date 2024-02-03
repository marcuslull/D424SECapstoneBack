package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.dto.service.interfaces.UserDtoService;
import com.mybackyard.backend.model.User;
import com.mybackyard.backend.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserDtoService userDtoService;

    public UserController(UserService userService, UserDtoService userDtoService) {
        this.userService = userService;
        this.userDtoService = userDtoService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getUsers(Authentication authentication) {
        List<User> userList = userService.getAllUsers();
        return userDtoService.processOutgoingUserList(userList);
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUsers() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto getUser(@PathVariable String id) {
        Optional<User> optionalUser = userService.getUserById(Long.parseLong(id));
        if (optionalUser.isPresent()) {
            return userDtoService.processOutgoingUser(optionalUser.get());
        }
        else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> postUser(@RequestBody UserDto userDto) {
        User user = userDtoService.processIncomingUserDto(userDto, false);
        long userId = userService.saveUser(user);
        UserDto recreatedUserDto = userDtoService.addId(userDto, userId);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).body(recreatedUserDto);
    }

    @PatchMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> patchUser(@PathVariable String id, @RequestBody UserDto userDto) {
        User user = userDtoService.processIncomingUserDto(userDto, true);
        User returnedUser = userService.updateUser(id, user);
        UserDto returnedUserDto = userDtoService.processOutgoingUser(returnedUser);
        return ResponseEntity.ok(returnedUserDto);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUserById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
