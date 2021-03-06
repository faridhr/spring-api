package com.springapi.springapi.configuration.JpaAuditing;

import com.springapi.springapi.model.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "aware")
public class AuditableConfiguration {

    @Bean
    public AuditorAware<Long> aware(){
        return new AuditorAwareImpl();
    }
}
