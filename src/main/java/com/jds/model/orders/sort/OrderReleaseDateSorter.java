package com.jds.model.orders.sort;

public class OrderReleaseDateSorter extends SortSqlQuery implements OrderSorter {
    @Override
    public String sort(String query) {
        return query + "order by releasdate " + option;
    }
}
