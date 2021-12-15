package com.springapi.springapi.controllers;

import com.springapi.springapi.configuration.bean.MapperBean;
import com.springapi.springapi.model.dto.UserData;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class AuthController {

    private MapperBean mapperBean;
    private UserService userService;

    public AuthController(MapperBean mapperBean, UserService userService) {
        this.mapperBean = mapperBean;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Response<User, ? extends Object>> register(@Valid @RequestBody UserData userData, Errors errors){
        Response<User, String> response;
        if (errors.hasErrors()){
            List<String> listErrors = new ArrayList<>(20);
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(
                    new Response<>(
                            listErrors, HttpStatus.BAD_REQUEST.value(), null
                    )
            );
        }else {
            User user = mapperBean.modelMapper().map(userData, User.class);
            response = new Response<>(
                    "Registration Successfull", HttpStatus.CREATED.value(), userService.userRegister(user)
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
