package com.springapi.springapi.model.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class LoginResponseData {

    private String message;

    private HttpStatus statusCode;

    private String token;

    private Date expiredAt;

    public LoginResponseData(String message, HttpStatus statusCode, String token, Date expiredAt) {
        this.message = message;
        this.statusCode = statusCode;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}
