package com.springapi.springapi.configuration.JpaAuditing;

import com.springapi.springapi.model.entities.User;
import com.springapi.springapi.model.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private UserLoginBean loginBean;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long id = null;
//        System.out.println(loginBean.userAuthentication().isPresent());
        try{
//            id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName().split(",")[0]);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(user.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
