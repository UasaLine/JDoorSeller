package com.jds.model.orders.filter;

import com.jds.model.enumModels.OrderStatus;
import lombok.Getter;

@Getter
public class OrderFilter {

    private OrderStatus status;
    private String partner;
    private String ofDate;
    private String toDate;
    private int seller;

    public OrderFilter(OrderStatus status, String partner, String ofDate, String toDate) {
        this.status = status;
        this.partner = partner;
        this.ofDate = ofDate;
        this.toDate = toDate;
    }
}
