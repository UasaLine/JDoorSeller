package com.jds.model.BackResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResponseAction {
    private String status;

    public ResponseAction(String status) {
        this.status = status;
    }

    public ResponseAction() {
    }

}
