package com.jds.model.BackResponse;

import com.jds.dao.entity.DoorOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderResponse extends BackResponse {
    private DoorOrder data;

    public OrderResponse(DoorOrder order) {
        this.data = order;
        setSuccess(true);
        setMessage("Ok");
    }

    public OrderResponse(boolean success, String message) {
        super(success, message);
    }

    public OrderResponse(){

    }

}
