package com.springapi.springapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "aware")
public class AuditableConfiguration {

    @Bean
    public AuditorAware<Long> aware(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            if (!(auth instanceof AnonymousAuthenticationToken)){
                System.out.println(auth.getPrincipal() + " | " + auth.getCredentials() + " | " + auth.getName());
            }
        }
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return () -> Optional.of(5L);
    }

//    @Bean
//    public Faker faker(){
//        return new Faker(new Locale("in", "ID"));
//    }
}
