package nl.hsleiden.iprwc.s1132776.controller;


import nl.hsleiden.iprwc.s1132776.DAO.UserDAO;
import nl.hsleiden.iprwc.s1132776.exceptions.RecordNotFoundException;
import nl.hsleiden.iprwc.s1132776.model.database.User;
import nl.hsleiden.iprwc.s1132776.model.http.ErrorResponse;
import nl.hsleiden.iprwc.s1132776.services.ReflectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserDAO userDAO;

    private final ReflectionService reflectionService;
    public UserController(UserDAO userDAO, ReflectionService reflectionService) {
        this.userDAO = userDAO;
        this.reflectionService = reflectionService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<User>> getUserOrUsers(@AuthenticationPrincipal User user) {
        switch (user.getRole().getName()) {
            case "USER":
                return ResponseEntity.ok(List.of(userDAO.getById(user.getId()).orElseThrow(()->new RecordNotFoundException("User with id: " + user.getId() + " not found"))));
            case "ADMIN":
                return ResponseEntity.ok().body(userDAO.getAll());
            default:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<User> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userDAO.getById(user.getId()).orElseThrow(()->new RecordNotFoundException("User with id: " + user.getId() + " not found")));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<User> getUserById(@AuthenticationPrincipal User user, @PathVariable("id") String id) {

        User foundUser = userDAO.getById(id).orElseThrow(()->new RecordNotFoundException("User with id: " + user.getId() + " not found"));


        switch (user.getRole().getName()) {
            case "USER":
                if(user.getId().equals(id)) {
                    return ResponseEntity.ok().body(foundUser);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            case "ADMIN":
                return ResponseEntity.ok().body(foundUser);
            default:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @PathVariable("id") String id, @RequestBody Map<Object, Object> userToUpdate) {


        if (user.getId().equals(id)) {
            User foundUser = userDAO.getById(id).orElseThrow(()-> new RecordNotFoundException("Location with id: " + id + " not found"));
            try {
                return ResponseEntity.ok(userDAO.save(ReflectionService.patchEntity(foundUser, userToUpdate, User.class)));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid data", List.of()));
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



}
