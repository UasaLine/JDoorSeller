package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.Door;
import com.jds.model.cutting.Sheet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Door")
public class DoorEntity implements Door {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "doorType")
    private int doorType;

    @Column(name = "widthDoor")
    private int widthDoor;

    @Column(name = "heightDoor")
    private int heightDoor;

    @Column(name = "аctivDoorLeafWidth")
    private int аctivDoorLeafWidth;

    @Column(name = "doorFanlightHeight")
    private int doorFanlightHeight;

    @Column(name = "metal")
    private int metal;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "doors",fetch = FetchType.LAZY)
    private List<DoorsОrder> оrders;

    @Column(name = "doorColor")
    private String doorColor;

    @Transient
    private List<Sheet> sheets;

    @Transient
    private int glassWidth;

    @Transient
    private int glassHeight;

    @Transient
    private List<DoorClass> availableDoorClass;

    @Transient
    private int discountPrice;

    @Transient
    private int priceWithMarkup;

    public DoorEntity() {
        this.оrders = new ArrayList<DoorsОrder>();
        this.availableDoorClass = new ArrayList<DoorClass>();
    }

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

    public int getDoorType() {
        return doorType;
    }

    public void setDoorType(int doorType) {
        this.doorType = doorType;
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

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
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

    public List<DoorsОrder> getОrders() {
        return оrders;
    }

    public void setОrders(List<DoorsОrder> оrders) {
        this.оrders = оrders;
    }

    public void addOrder (DoorsОrder door){
        this.оrders.add(door);
    }

    public String getDoorColor() {
        return doorColor;
    }

    public void setDoorColor(String doorColor) {
        this.doorColor = doorColor;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public int getGlassWidth() {
        return glassWidth;
    }

    public void setGlassWidth(int glassWidth) {
        this.glassWidth = glassWidth;
    }

    public int getGlassHeight() {
        return glassHeight;
    }

    public void setGlassHeight(int glassHeight) {
        this.glassHeight = glassHeight;
    }

    public List<DoorClass> getAvailableDoorClass() {
        return availableDoorClass;
    }

    public void setAvailableDoorClass(List<DoorClass> availableDoorClass) {
        this.availableDoorClass = availableDoorClass;
    }

    public void addAvailableDoorClass(DoorClass doorClass){
        this.availableDoorClass.add(doorClass);
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPriceWithMarkup() {
        return priceWithMarkup;
    }

    public void setPriceWithMarkup(int priceWithMarkup) {
        this.priceWithMarkup = priceWithMarkup;
    }
}
