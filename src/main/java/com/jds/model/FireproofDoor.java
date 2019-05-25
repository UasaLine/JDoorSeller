package com.jds.model;

import com.jds.entity.DoorClass;
import com.jds.entity.Metal;
import com.jds.model.modelEnum.SideDoorOpen;

import java.util.ArrayList;
import java.util.List;

public class FireproofDoor implements Door{

    private int id;
    private String doorClass;
    private int widthDoor;
    private int heightDoor;
    private String metal;
    private int deepnessDoor;
    private int thicknessDoorLeaf;
    private String sideDoorOpen;
    private int innerDoorOpen;
    private int doorstep;
    private int stainlessSteelDoorstep;
    private int topDoorTrim;
    private int leftDoorTrim;
    private int rightDoorTrim;


    public FireproofDoor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}



