package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public interface OrderSortFactory {
    public OrderSorter sorter(OrderSortField field, SideSqlSorting side);
}
