package com.jds.model.orders.sort;

public class OrderSumSorter extends SortSqlQuery implements OrderSorter {
    @Override
    public String sort(String query) {
        return query + "order by totalamount " + option;
    }
}
