package com.springapi.springapi.controllers;

import com.springapi.springapi.configuration.bean.MapperBean;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapperBean mapperBean;

//    @GetMapping
    public ResponseEntity<Response<UserDetails, String>> findEmail(@RequestParam String email){
        Response<UserDetails, String> response = new Response(
                "Data User", HttpStatus.OK.value(), userService.loadUserByUsername(email)
        );
        return ResponseEntity.ok(response);
    }



}
