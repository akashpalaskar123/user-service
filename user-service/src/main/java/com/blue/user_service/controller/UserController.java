package com.blue.user_service.controller;

import com.blue.user_service.dto.UserRequestDto;
import com.blue.user_service.entity.JwtResponse;
//import com.blue.user_service.entity.Role;
import com.blue.user_service.entity.User;
import com.blue.user_service.repository.UserRepository;
import com.blue.user_service.security.JwtHelper;
//import com.blue.user_service.service.RoleService;
import com.blue.user_service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/public")
public class UserController {



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserServiceImpl userService;

    Logger logger=Logger.getLogger(UserController.class.getName());

    @PostMapping("/login_user")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody User user) {

       logger.info("login method called");
        boolean validate = userService.loginUser(user);
        if (validate){
            User validateUser= repository.findById(user.getId()).get();
            UserDetails userDetails = userDetailsService.loadUserByUsername(validateUser.getUsername());
            String token = this.helper.generateToken(userDetails);
            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .userName(userDetails.getUsername()).build();
            return ResponseEntity.ok(response);

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register_user")
    public ResponseEntity<UserRequestDto> userSave(@RequestBody UserRequestDto userRequestDto) {

        logger.info("userSave method called");
        userService.registerUser(userRequestDto);
        return ResponseEntity.ok(userRequestDto);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
