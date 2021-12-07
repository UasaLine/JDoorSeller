package com.jds.model.orders.filter;

import com.jds.model.enumModels.OrderStatus;

public interface OrderFilterFactory {
    public OrderFilter filter(OrderStatus status, String partner, String ofDate, String toDate);
}
