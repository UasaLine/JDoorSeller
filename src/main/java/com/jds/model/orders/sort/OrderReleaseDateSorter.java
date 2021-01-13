package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderReleaseDateSorter extends SortSqlQuery implements OrderSorter {

    public OrderReleaseDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public String sort(String query) {
        return query + "order by releasdate " + option;
    }
}
