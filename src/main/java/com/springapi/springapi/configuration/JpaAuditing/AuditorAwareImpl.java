package com.springapi.springapi.configuration.JpaAuditing;

import com.springapi.springapi.model.entities.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        return Optional.of(3L);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of("rahman@mail.com");
    }
}
