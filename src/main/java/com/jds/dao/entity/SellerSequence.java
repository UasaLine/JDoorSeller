package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seller_sequence")
public class SellerSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NaturalId
    @Column(name = "seller_id")
    private int sellerId;

    @Column(name = "order_id")
    private int orderId;

    public int nextId() {
        orderId = orderId + 1;
        return orderId;
    }

    public SellerSequence(int sellerId, int orderId) {
        this.sellerId = sellerId;
        this.orderId = orderId;
    }

}
