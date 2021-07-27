package com.jds.model.orders.print;

import com.jds.dao.entity.DoorEntity;
import com.jds.model.orders.OrderDiscounts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderPrintLine implements Comparable<OrderPrintLine> {

    private int id;
    private String name;
    private int quantity;
    private int priceWithMarkup;
    private double discount;
    private int totalPrice;

    public OrderPrintLine(DoorEntity door, OrderDiscounts orderDiscounts) {
        this.id = door.getId();
        this.name = door.getName();
        this.quantity = door.getQuantity();
        this.priceWithMarkup = door.getPriceWithMarkup();

        int sum = priceWithMarkup * quantity;
        double discountAsPercent = orderDiscounts.getByDoorId(door.getId());

        this.discount = Math.floor(sum * discountAsPercent) / 100;
        this.totalPrice = (int) (priceWithMarkup - Math.round(priceWithMarkup * discountAsPercent / 100)) * quantity;
    }

    @Override
    public int compareTo(OrderPrintLine line) {
        return this.id - line.id;
    }
}
