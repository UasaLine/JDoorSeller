package com.jds.dao.entity;

import com.jds.model.orders.OrderDiscounts;
import com.jds.model.enumModels.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class DoorOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Column(name = "seller_order_id")
    private int sellerOrderId;

    @Column(name = "company")
    private String company;

    @Column(name = "partner")
    private String partner;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column(name = "releasDate")
    @Temporal(TemporalType.DATE)
    private Date releasDate;

    @Column(name = "productionStart")
    private int productionStart;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "doors_orders",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "door_id")}
    )
    private List<DoorEntity> doors;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller")
    private UserEntity seller;

    @Column(name = "comment")
    private String comment;

    @Column(name = "totalAmount")
    private int totalAmount;

    @Column(name = "totalTax")
    private int totalTax;

    @Column(name = "totalQuantity")
    private int totalQuantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Transient
    private List<OrderStatus> statusList;

    public boolean isWorking() {

        if ((this.getStatus() == OrderStatus.IN_WORK)
                || (this.getStatus() == OrderStatus.READY)) {
            return true;
        }

        return false;
    }

    public DoorOrder() {

        doors = new ArrayList<>();
        data = new java.sql.Date(new Date().getTime());
        status = OrderStatus.startStatus();
        statusList = new ArrayList<>();
    }

    public int deleteDoor(int id) {

        for (int i = 0; i < doors.size(); i++) {
            if (doors.get(i).getId() == id) {
                doors.remove(i);
                return 1;
            }
        }
        return 0;
    }

    public DoorOrder calculateTotal(UserSetting userSetting, OrderDiscounts orderDiscounts) {

        if (userSetting.getIncludesTax() != 0) {
            totalCostByDoors(userSetting.getSalesTax(), orderDiscounts);
        } else {
            totalCostByDoors(0, orderDiscounts);
        }


        return this;
    }

    private void totalCostByDoors(int tax, OrderDiscounts orderDiscounts) {

        totalAmount = 0;
        totalTax = 0;
        totalQuantity = 0;

        for (DoorEntity door : doors) {

            double discount = orderDiscounts.getByDoorId(door.getId());
            totalQuantity = totalQuantity + door.getQuantity();
            totalAmount += (door.getPriceWithMarkup() - Math.round(door.getPriceWithMarkup() * discount / 100)) * door.getQuantity();
            totalTax += ((door.getPriceWithMarkup() - Math.round((door.getPriceWithMarkup() * discount / 100))) * door.getQuantity()) * tax / (100 + tax);
        }
    }

    public void addDoor(DoorEntity door) {
        this.doors.add(door);
    }

    public void addAllStatus() {
        this.statusList = OrderStatus.statusList(status);
    }

    public void clearNonSerializingFields() {
        seller.setOrders(null);
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
        }
    }

    public void clearLAZY() {
        seller.setOrders(null);
        doors = null;
    }

    public boolean isSellerOrderIdExists() {
        if (sellerOrderId == 0) {
            return false;
        }
        return true;
    }

    public static boolean isNotNew(int orderId) {
        return orderId > 0;
    }

}
