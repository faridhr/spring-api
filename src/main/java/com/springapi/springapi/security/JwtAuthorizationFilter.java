package com.springapi.springapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.model.entities.UserRoles;
import com.springapi.springapi.model.repos.UserRepo;
import com.springapi.springapi.utils.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepo userRepo;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER);

        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request){
        String token = request.getHeader(JwtProperties.HEADER);
        if (token != null){
            String email = JWT.require(Algorithm.HMAC512(JwtProperties.KEY.getBytes())).build().verify(token.replace(JwtProperties.TOKEN_PREFIX, "")).getSubject();
            if (email != null){
                User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found"));
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
                return authenticationToken;
            }
            return null;
        }
        return null;
    }
}
