package com.springapi.springapi.controllers;

import com.springapi.springapi.model.entities.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for testing User Roles
 */

@RestController
@RequestMapping("api/v1")
public class RolesController {

    @GetMapping("/administrator/welcome")
    public Response<String, String> adminRoles(){
        return new Response<>(
                "Test Application Admin Roles", HttpStatus.OK.value(), "Empty Result"
        );
    }

    @GetMapping("/user/welcome")
    public Response<String, String> userRoles(){
        return new Response<>(
                "Test Application User Roles", HttpStatus.OK.value(), "Empty Result"
        );
    }

}
