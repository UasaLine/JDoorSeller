package com.jds.model.orders;

import com.jds.model.Pagination;
import com.jds.model.orders.filter.OrderFilter;
import com.jds.model.orders.sort.OrderSorter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderParamsDto {

    private OrderSorter sorter;
    private OrderFilter filter;
    private Pagination pagination;

    public int getLimit() {
        return pagination.getLimit();
    }

    public int getOffset() {
        return pagination.getOffset();
    }
}
