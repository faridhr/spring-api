package com.springapi.springapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.springapi.springapi.configuration.bean.UserBean;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.model.repos.UserRepo;
import com.springapi.springapi.utils.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Qualifier("currentUserLogin")
//    private UserBean userBean;

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
            try {
                String email = JWT.require(Algorithm.HMAC512(JwtProperties.KEY.getBytes())).build().verify(token.replace(JwtProperties.TOKEN_PREFIX, "")).getSubject();
                if (email != null){
                    User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found"));
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);
                    return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
                }
            }catch (TokenExpiredException |JWTDecodeException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
        return null;
    }
}
