package com.springapi.springapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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

}
