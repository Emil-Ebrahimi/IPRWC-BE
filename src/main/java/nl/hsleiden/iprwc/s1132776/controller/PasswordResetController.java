package nl.hsleiden.iprwc.s1132776.controller;


import nl.hsleiden.iprwc.s1132776.DAO.PasswordResetDAO;
import nl.hsleiden.iprwc.s1132776.DAO.UserDAO;
import nl.hsleiden.iprwc.s1132776.exceptions.RecordNotFoundException;
import nl.hsleiden.iprwc.s1132776.model.database.PasswordReset;
import nl.hsleiden.iprwc.s1132776.model.http.PasswordResetRequest;
import nl.hsleiden.iprwc.s1132776.model.http.PasswordResetRequestGUI;
import nl.hsleiden.iprwc.s1132776.model.database.User;
import nl.hsleiden.iprwc.s1132776.services.EmailService;
import nl.hsleiden.iprwc.s1132776.services.RandomService;
import nl.hsleiden.iprwc.s1132776.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/password-reset")
public class PasswordResetController {

    private final UserDAO userDAO;

    private final PasswordResetDAO passwordResetDAO;
    private final EmailService emailService;

    private final UserService userService;

    public PasswordResetController(UserDAO userDAO, PasswordResetDAO passwordResetDAO, EmailService emailService, UserService userService) {
        this.userDAO = userDAO;
        this.passwordResetDAO = passwordResetDAO;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request) {

        User user = userDAO.getByEmail(request.getEmail()).orElseThrow(()-> new RecordNotFoundException("User not found"));

        String code = RandomService.getRandomNumberString();

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(request.getEmail());
        passwordReset.setCode(code);


        passwordResetDAO.saveToDatabase(passwordReset);
        emailService.sendPasswordResetEmail(user.getEmail(), code);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/code")
    public ResponseEntity<String> handlePasswordReset(@RequestBody PasswordResetRequestGUI data) {

        if(data.getCode() == null || data.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<PasswordReset> passwordReset = passwordResetDAO.getPasswordResetByCode(data.getCode());

        if(passwordReset.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!passwordReset.get().getEmail().equals(data.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optUser = userDAO.getByEmail(passwordReset.get().getEmail());

        if(optUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = optUser.get();

        user.setPassword(data.getPassword());
        userService.save(user);

        passwordResetDAO.deleteById(passwordReset.get().getId());

        return ResponseEntity.ok().build();
    }



}
