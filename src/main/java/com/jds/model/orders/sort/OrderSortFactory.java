package com.jds.model.orders.sort;

public interface OrderSortFactory {
    public OrderSorter sorter(OrderSortField field);
}
