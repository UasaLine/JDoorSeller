package com.jds.model.orders.filter;

import com.jds.model.enumModels.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderFilterFactoryImpl implements OrderFilterFactory {

    @Override
    public OrderFilter filter(OrderStatus status, String partner, String ofDate, String toDate) {
        return new OrderFilter(status, partner, ofDate, toDate);
    }
}
