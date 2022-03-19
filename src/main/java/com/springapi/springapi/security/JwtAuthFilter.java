package com.springapi.springapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapi.springapi.model.dto.UserLoginData;
import com.springapi.springapi.utils.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginData credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), UserLoginData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword(), new ArrayList<>()
        );

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        Date expiresAt = new Date(System.currentTimeMillis() + JwtProperties.EXPIRED);

        String token = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512(JwtProperties.KEY.getBytes()));
        response.addHeader(JwtProperties.HEADER, JwtProperties.TOKEN_PREFIX + token);
//        loginResponse(JwtProperties.TOKEN_PREFIX + " " + token, expiresAt);
    }

//    public @ResponseBody LoginResponseData loginResponse(String token, Date expiresAt){
//        return new LoginResponseData(
//                "Authentication Successful", HttpStatus.OK, token, expiresAt
//        );
//    }
}
