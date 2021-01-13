package com.jds.model.orders.sort;


import com.jds.model.enumClasses.SideSqlSorting;

public class OrderDateSorter extends SortSqlQuery implements OrderSorter {
    public OrderDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public String sort(String query) {
        return query + "order by data " + option;
    }
}
