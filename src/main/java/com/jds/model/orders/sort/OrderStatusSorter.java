package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderStatusSorter extends SortSqlQuery implements OrderSorter {
    public OrderStatusSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public String sort(String query) {
        return query + "order by status " + option;
    }
}
