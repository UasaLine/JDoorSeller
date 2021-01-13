package com.jds.model.orders.sort;

import org.springframework.stereotype.Service;

@Service
public class OrderSortFactoryImpl implements OrderSortFactory {
    @Override
    public OrderSorter sorter(OrderSortField field) {
        return new OrderDateSorter();
    }
}
