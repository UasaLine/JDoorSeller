package com.jds.model.orders.sort;

import com.jds.dao.entity.DoorOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface OrderSorter {
    public Order sort(CriteriaBuilder builder, Root<DoorOrder> root);
}
