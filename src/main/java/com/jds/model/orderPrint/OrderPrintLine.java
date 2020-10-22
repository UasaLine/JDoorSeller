package com.jds.model.orderPrint;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.OrderDiscount;
import com.jds.model.OrderDiscounts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class OrderPrintLine implements Comparable<OrderPrintLine> {

    private int position;
    private int id;
    private String name;
    private int quantity;
    private int priceWithMarkup;
    private int discount;
    private int totalPrice;

    public OrderPrintLine(DoorEntity door, OrderDiscounts orderDiscounts) {
        this.position++;
        this.id = door.getId();
        this.name = door.getName();
        this.quantity = door.getQuantity();
        this.priceWithMarkup = door.getPriceWithMarkup();

        this.discount = orderDiscounts.getByDoorId(door.getId());
        this.totalPrice = (int) Math.floor((priceWithMarkup- (priceWithMarkup*discount/100))*quantity);
    }

    @Override
    public int compareTo(OrderPrintLine line) {
        return this.id - line.id;
    }
}
