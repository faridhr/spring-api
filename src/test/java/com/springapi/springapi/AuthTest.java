package com.springapi.springapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@Slf4j
@SpringBootTest
public class AuthTest {

    @BeforeEach
    public void before() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmaHJAbWFpbC5jb20iLCJleHAiOjE2Mzk4MDg5OTN9.jhcNpC1dzhceeCEnaoD20MlCMhY1jX9EtiMxKXe5ccTDAqDrKj7tuO2Lbc1IK4xomFFwJTPBOzGihv5_4iz4QQ");
    }


    @Test
    public void getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Assertions.assertNotNull(authentication);
//        log.info(Arrays.toString(authentication.getPrincipal().));
    }
}
