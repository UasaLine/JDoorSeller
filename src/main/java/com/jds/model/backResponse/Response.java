package com.jds.model.backResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Response {
    private Boolean success;
    private String message;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response() {
    }

}
