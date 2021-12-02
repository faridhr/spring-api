package com.springapi.springapi.model.entities;


public class Response<T, S> {

    private S message;

    private Integer responseCode;


    private T payloads;

    public Response() {

    }

    public Response(S message, Integer responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public Response(S message, Integer responseCode, T payloads) {
        this.message = message;
        this.responseCode = responseCode;
        this.payloads = payloads;
    }

    public S getMessage() {
        return message;
    }

    public void setMessage(S message) {
        this.message = message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public T getPayloads() {
        return payloads;
    }

    public void setPayloads(T payloads) {
        this.payloads = payloads;
    }
}
