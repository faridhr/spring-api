package com.springapi.springapi.controllers;

import com.springapi.springapi.configuration.MapperConfiguration;
import com.springapi.springapi.model.dto.UserData;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapperConfiguration mapperConfiguration;

    @GetMapping
    public ResponseEntity<Response<UserDetails, String>> findEmail(@RequestParam String email){
        Response<UserDetails, String> response = new Response(
                "Data User", HttpStatus.OK.value(), userService.loadUserByUsername(email)
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<User, ? extends Object>> register(@Valid @RequestBody UserData userData, Errors errors){
        if (errors.hasErrors()){
            List<String> listErrors = new ArrayList<>();
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            ResponseEntity.badRequest().body(
                    new Response<User, List<String>>(
                            listErrors, HttpStatus.BAD_REQUEST.value(), null
                    )
            );
        }
        User user = mapperConfiguration.modelMapper().map(userData, User.class);
        Response<User, String> response = new Response<>(
                "Registration Successfull", HttpStatus.CREATED.value(), userService.userRegister(user)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}