package com.jds.entity;

import com.jds.model.FireproofDoor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class DoorsОrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    int order_id;

    @Column(name = "company")
    String company;

    @Column(name = "partner")
    String partner;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    Date data;

    @Column(name = "releasDate")
    @Temporal(TemporalType.DATE)
    Date releasDate;

    @Column(name = "productionStart")
    int productionStart;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "dorors_orders",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "door_id") }
    )
    List<DoorEntity> doors;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller")
    User seller;

    @Column(name = "comment")
    String comment;

    @Column(name = "totalAmount")
    int totalAmount;

    @Column(name = "totalTax")
    int totalTax;

    public int getId() {
        return order_id;
    }

    public void setId(int order_id) {
        this.order_id = order_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getReleasDate() {
        return releasDate;
    }

    public void setReleasDate(Date releasDate) {
        this.releasDate = releasDate;
    }

    public int getProductionStart() {
        return productionStart;
    }

    public void setProductionStart(int productionStart) {
        this.productionStart = productionStart;
    }

    public List<DoorEntity> getDoors() {
        return doors;
    }

    public void setDoors(List<DoorEntity> doors) {
        this.doors = doors;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }

    public void addDoor(DoorEntity door){
        this.doors.add(door);
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
