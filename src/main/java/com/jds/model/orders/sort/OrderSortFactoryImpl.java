package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;
import org.springframework.stereotype.Service;

@Service
public class OrderSortFactoryImpl implements OrderSortFactory {

    @Override
    public OrderSorter sorter(OrderSortField field, SideSqlSorting side) {
        switch (field) {
            case DATE:
                return new OrderDateSorter(side);
            case SUM:
                return new OrderSumSorter(side);
            case RELEASE_DATE:
                return new OrderReleaseDateSorter(side);
            case STATUS:
                return new OrderStatusSorter(side);
            default: {
                return new OrderDateSorter(side);
            }
        }
    }
}
