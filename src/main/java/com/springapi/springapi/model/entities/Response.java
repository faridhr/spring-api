package com.springapi.springapi.model.entities;

public class Response<T> {

    private String message;

    private Integer responseCode;

    private T data;

    private static Response response;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Response getInstance(){
        if (response == null){
            return response = new Response();
        }
        return response;
    }
}
