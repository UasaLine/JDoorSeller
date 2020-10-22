package com.jds.dao.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_discount")
public class OrderDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "door_id")
    private int door_id;

    @Column(name = "discount")
    private int discount;

//    @OneToOne(mappedBy = "order_door_discount",fetch = FetchType.LAZY)
//    private DoorsОrder order;
//
//    @OneToOne(mappedBy = "order_door_discount",fetch = FetchType.LAZY)
//    private DoorEntity door;

    public OrderDiscount instanceOrderDoorDiscount(DoorsОrder doorsОrder, DoorEntity doorEntity) {
        OrderDiscount orderDoorDiscount = new OrderDiscount();
        orderDoorDiscount.setOrder_id(doorsОrder.getOrder_id());
        orderDoorDiscount.setDoor_id(doorEntity.getId());

        return orderDoorDiscount;
    }

    public OrderDiscount clearNonSerializingFields() {
//        order = null;
//        door = null;

        if (order_id != 0){
            order_id = 0;
        }
        if (door_id != 0){
            door_id = 0;
        }

        return this;
    }
}
