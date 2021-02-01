package com.jds.model.backResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel<T> extends Response {
    T model;

    public ResponseModel(boolean success, String message, T model) {
        super(success, message);
        this.model = model;
    }

    public ResponseModel(T model) {
        super(true, "ok");
        this.model = model;
    }
}
