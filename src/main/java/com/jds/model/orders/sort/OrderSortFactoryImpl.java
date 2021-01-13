package com.jds.model.orders.sort;

import com.jds.model.enumClasses.SideSqlSorting;
import org.springframework.stereotype.Service;

@Service
public class OrderSortFactoryImpl implements OrderSortFactory {
    //TODO (salagaev) move logic to user settings 1
    private OrderSortField currentSortField;
    private SideSqlSorting sideSorting;

    @Override
    public OrderSorter sorter(OrderSortField field) {
        switch (field) {
            case DATE:
                return new OrderDateSorter(sortSide(field));
            case SUM:
                return new OrderSumSorter(sortSide(field));
            case RELEASE_DATE:
                return new OrderReleaseDateSorter(sortSide(field));
            case STATUS:
                return new OrderStatusSorter(sortSide(field));
            default: {
                return new OrderDateSorter(sortSide(field));
            }
        }
    }
    //TODO (salagaev) move logic to user settings 2
    private SideSqlSorting sortSide(OrderSortField sortField) {
        if (sortField == currentSortField && sideSorting == SideSqlSorting.DESC) {
            sideSorting = SideSqlSorting.ASC;
        } else {
            currentSortField = sortField;
            sideSorting = SideSqlSorting.DESC;
        }
        return sideSorting;
    }
}
