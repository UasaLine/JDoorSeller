package com.jds.model.orders.sort;


import com.jds.model.enumClasses.SideSqlSorting;

public class OrderDateSorter extends SortSqlQuery implements OrderSorter {
    public OrderDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public StringBuilder sort(StringBuilder query) {
        query.append("order by data ");
        query.append(option);
        return query;
    }
}
