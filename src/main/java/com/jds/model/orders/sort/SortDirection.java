package com.jds.model.orders.sort;

import com.jds.model.enumModels.SideSqlSorting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

abstract class SortDirection {
    protected SideSqlSorting option;

    public SortDirection(SideSqlSorting option) {
        this.option = option;
    }

    protected Order getDirection(CriteriaBuilder builder, Root root, String fieldName) {
        if (option == SideSqlSorting.ASC) {
            return  builder.asc(root.get(fieldName));
        }
        return  builder.desc(root.get(fieldName));
    }
}
