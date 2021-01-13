package com.jds.model.orders.sort;


public class OrderDateSorter extends SortSqlQuery implements OrderSorter {
    @Override
    public String sort(String query) {
        return query + "order by data " + option;
    }
}
