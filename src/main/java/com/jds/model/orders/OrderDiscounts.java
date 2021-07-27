package com.jds.model.orders;

import com.jds.dao.entity.OrderDiscount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDiscounts {
    private List<OrderDiscount> ordersDiscountsList;

    public OrderDiscounts(List<OrderDiscount> ordersDiscountsList) {
        this.ordersDiscountsList = ordersDiscountsList;
    }

    public double getByDoorId (int door_id){

        OrderDiscount orderDiscount = ordersDiscountsList.stream()
                .filter((dis)-> dis.getDoor_id() == (door_id))
                .findFirst().orElse(null);

        if (orderDiscount != null){
            return orderDiscount.getDiscount();
        } return 0;

    }


}
