package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "Door")
public class DoorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "doorClass")
    private String doorClass;

    @Column(name = "widthDoor")
    private int widthDoor;

    @Column(name = "heightDoor")
    private int heightDoor;

    @Column(name = "аctivDoorLeafWidth")
    private int аctivDoorLeafWidth;

    @Column(name = "doorFanlightHeight")
    private int doorFanlightHeight;

    @Column(name = "metal")
    private String metal;

    @Column(name = "deepnessDoor")
    private int deepnessDoor;

    @Column(name = "thicknessDoorLeaf")
    private int thicknessDoorLeaf;

    @Column(name = "sideDoorOpen")
    private String sideDoorOpen;

    @Column(name = "innerDoorOpen")
    private int innerDoorOpen;

    @Column(name = "doorstep")
    private int doorstep;

    @Column(name = "stainlessSteelDoorstep")
    private int stainlessSteelDoorstep;

    @Column(name = "topDoorTrim")
    private int topDoorTrim;

    @Column(name = "leftDoorTrim")
    private int leftDoorTrim;

    @Column(name = "rightDoorTrim")
    private int rightDoorTrim;

    @Column(name = "price")
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoorClass() {
        return doorClass;
    }

    public void setDoorClass(String doorClass) {
        this.doorClass = doorClass;
    }

    public int getWidthDoor() {
        return widthDoor;
    }

    public void setWidthDoor(int widthDoor) {
        this.widthDoor = widthDoor;
    }

    public int getHeightDoor() {
        return heightDoor;
    }

    public void setHeightDoor(int heightDoor) {
        this.heightDoor = heightDoor;
    }

    public int getАctivDoorLeafWidth() {
        return аctivDoorLeafWidth;
    }

    public void setАctivDoorLeafWidth(int аctivDoorLeafWidth) {
        this.аctivDoorLeafWidth = аctivDoorLeafWidth;
    }

    public int getDoorFanlightHeight() {
        return doorFanlightHeight;
    }

    public void setDoorFanlightHeight(int doorFanlightheight) {
        this.doorFanlightHeight = doorFanlightheight;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public int getDeepnessDoor() {
        return deepnessDoor;
    }

    public void setDeepnessDoor(int deepnessDoor) {
        this.deepnessDoor = deepnessDoor;
    }

    public int getThicknessDoorLeaf() {
        return thicknessDoorLeaf;
    }

    public void setThicknessDoorLeaf(int thicknessDoorLeaf) {
        this.thicknessDoorLeaf = thicknessDoorLeaf;
    }

    public String getSideDoorOpen() {
        return sideDoorOpen;
    }

    public void setSideDoorOpen(String sideDoorOpen) {
        this.sideDoorOpen = sideDoorOpen;
    }

    public int getInnerDoorOpen() {
        return innerDoorOpen;
    }

    public void setInnerDoorOpen(int innerDoorOpen) {
        this.innerDoorOpen = innerDoorOpen;
    }

    public int getDoorstep() {
        return doorstep;
    }

    public void setDoorstep(int doorstep) {
        this.doorstep = doorstep;
    }

    public int getStainlessSteelDoorstep() {
        return stainlessSteelDoorstep;
    }

    public void setStainlessSteelDoorstep(int stainlessSteelDoorstep) {
        this.stainlessSteelDoorstep = stainlessSteelDoorstep;
    }

    public int getTopDoorTrim() {
        return topDoorTrim;
    }

    public void setTopDoorTrim(int topDoorTrim) {
        this.topDoorTrim = topDoorTrim;
    }

    public int getLeftDoorTrim() {
        return leftDoorTrim;
    }

    public void setLeftDoorTrim(int leftDoorTrim) {
        this.leftDoorTrim = leftDoorTrim;
    }

    public int getRightDoorTrim() {
        return rightDoorTrim;
    }

    public void setRightDoorTrim(int rightDoorTrim) {
        this.rightDoorTrim = rightDoorTrim;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
