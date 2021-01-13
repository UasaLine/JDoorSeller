package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderSumSorter extends SortSqlQuery implements OrderSorter {

    public OrderSumSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public String sort(String query) {
        return query + "order by totalamount " + option;
    }
}
