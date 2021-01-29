package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderSumSorter extends SortSqlQuery implements OrderSorter {

    public OrderSumSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public StringBuilder sort(StringBuilder query) {
        query.append("order by totalamount ");
        query.append(option);
        return query;
    }
}
