package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SortingSqlOption;

abstract class SortSqlQuery {
    protected SortingSqlOption option;

    SortSqlQuery(SortingSqlOption option) {
        this.option = option;
    }

    SortSqlQuery() {
        this.option = SortingSqlOption.DESC;
    }
}
