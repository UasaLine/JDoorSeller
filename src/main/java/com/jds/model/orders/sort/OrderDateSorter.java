package com.jds.model.orders.sort;


import com.jds.dao.entity.DoorOrder;
import com.jds.model.enumClasses.SideSqlSorting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class OrderDateSorter extends SortDirection implements OrderSorter {
    public OrderDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public Order sort(CriteriaBuilder builder, Root<DoorOrder> root) {

        return getDirection(builder, root, "data");
    }
}
