package com.springapi.springapi.services;

import com.springapi.springapi.security.PasswordHash;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.model.entities.UserRoles;
import com.springapi.springapi.model.repos.UserRepo;
import com.springapi.springapi.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;
    private PasswordHash passwordHash;

    public UserService(UserRepo userRepo, PasswordHash passwordHash) {
        this.userRepo = userRepo;
        this.passwordHash = passwordHash;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("email '%s' not found", email)));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return userDetails;
    }

    public User userRegister(User user){
        boolean emailExist = userRepo.findByEmail(user.getEmail()).isPresent();
        if (emailExist){
            throw new RuntimeException(String.format("email '%s' is exist", user.getEmail()));
        }
        String encode = passwordHash.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        user.setUserRoles(user.getUserRoles());
        return userRepo.save(user);
    }
}
