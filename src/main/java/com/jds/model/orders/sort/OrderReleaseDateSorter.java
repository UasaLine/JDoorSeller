package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

public class OrderReleaseDateSorter extends SortSqlQuery implements OrderSorter {

    public OrderReleaseDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public StringBuilder sort(StringBuilder query) {
        query.append("order by releasdate ");
        query.append(option);
        return  query;
    }
}
