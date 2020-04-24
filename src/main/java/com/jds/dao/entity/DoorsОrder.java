package com.jds.dao.entity;

import com.jds.model.modelEnum.OrderStatus;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
public class DoorsОrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int order_id;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public boolean isWorking(){

        if((this.getStatus() == OrderStatus.IN_WORK)
                ||(this.getStatus() == OrderStatus.READY)){
            return true;
        }

        return false;
    };

    public DoorsОrder()  {

        doors = new ArrayList<>();
        data = new java.sql.Date(new Date().getTime());
        status = OrderStatus.CALC;
        statusList = new ArrayList<>();
        this.addAllStatus();
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

    public DoorsОrder calculateTotal() {

        totalAmount = 0;
        totalTax = 0;
        totalQuantity = 0;
        int tax = 20;

        for (DoorEntity door : doors) {
            totalQuantity += 1;
            totalAmount += door.getPriceWithMarkup();
            totalTax += (door.getPriceWithMarkup() * tax) / (100 + tax);
        }


        return this;
    }

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

    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
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

    public void addDoor(DoorEntity door) {
        this.doors.add(door);
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<OrderStatus> statusList) {
        this.statusList = statusList;
    }

    public void addStatusList(OrderStatus status) {
        this.statusList.add(status);
    }

    public void addAllStatus() {
        this.addStatusList(OrderStatus.CALC);
        this.addStatusList(OrderStatus.TO_WORK);
        this.addStatusList(OrderStatus.CLOSED);
    }


}
