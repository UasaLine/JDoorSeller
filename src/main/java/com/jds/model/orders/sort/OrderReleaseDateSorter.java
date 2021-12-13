package com.jds.model.orders.sort;

import com.jds.dao.entity.DoorOrder;
import com.jds.model.enumModels.SideSqlSorting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class OrderReleaseDateSorter extends SortDirection implements OrderSorter {

    public OrderReleaseDateSorter(SideSqlSorting option) {
        super(option);
    }

    @Override
    public Order sort(CriteriaBuilder builder, Root<DoorOrder> root) {

        return getDirection(builder, root, "releasDate");
    }
}
