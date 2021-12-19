package com.springapi.springapi.controllers.user;

import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controller for testing User Roles
 */

@RestController
@RequestMapping("api/v1")
public class RolesController {

    @GetMapping("/administrator/welcome")
    public Response<String, String> adminRoles(){
//        Long id = Long.parseLong(principal.getName().split(",")[0]);
//        System.out.println(principal.getName());
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
