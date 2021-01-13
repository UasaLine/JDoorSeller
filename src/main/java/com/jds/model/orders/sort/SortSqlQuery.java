package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;

abstract class SortSqlQuery {
    protected SideSqlSorting option;

    public SortSqlQuery(SideSqlSorting option) {
        this.option = option;
    }
}
