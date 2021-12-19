package com.springapi.springapi.configuration.bean;

import com.springapi.springapi.model.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserBean {

    private User user;

    @Primary
    @Bean
    public User user(){
        return new User();
    }

    @Bean
    public User currentUserLogin(User user){
        if (user != null){
            return user;
        }
        return this.user = new User();
    }
}
