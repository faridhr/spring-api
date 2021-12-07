package com.springapi.springapi.services;

import com.springapi.springapi.configuration.PasswordHashConfiguration;
import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.model.entities.UserRoles;
import com.springapi.springapi.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordHashConfiguration passwordHashConfiguration;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("email '%s' not found", email)));
    }

    public User userRegister(User user){
        boolean emailExist = userRepo.findByEmail(user.getUsername()).isPresent();
        if (emailExist){
            throw new RuntimeException(String.format("email '%s' is exist", user.getUsername()));
        }
        String encode = passwordHashConfiguration.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        user.setUserRoles(UserRoles.USER);
        return userRepo.save(user);
    }
}
