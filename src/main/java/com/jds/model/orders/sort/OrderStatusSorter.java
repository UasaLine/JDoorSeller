package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderStatusSorter extends SortSqlQuery implements OrderSorter {
    public OrderStatusSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public StringBuilder sort(StringBuilder query) {
        query.append("order by status ");
        query.append(option);
        return query;
    }
}
