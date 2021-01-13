package com.jds.model.orders.sort;

public class OrderStatusSorter extends SortSqlQuery implements OrderSorter {
    @Override
    public String sort(String query) {
        return query + "order by status " + option;
    }
}
