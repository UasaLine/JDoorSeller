package com.jds.model;

import com.jds.entity.DoorColors;
import com.jds.entity.LimitationDoor;

import java.util.ArrayList;
import java.util.List;

public class RestrictionOfSelectionFields {

    private List<LimitationDoor> metal;
    private List<LimitationDoor> widthDoor;
    private List<LimitationDoor> heightDoor;
    private List<LimitationDoor> deepnessDoor;
    private List<LimitationDoor> thicknessDoorLeaf;
    private List<DoorColors> colors;


    //replace with getting data from the database
    public RestrictionOfSelectionFields() {
        metal = new ArrayList<>();
        metal.add(new LimitationDoor(
                "1.2",
                1.2,
                1.2,
                1));

        metal.add(new LimitationDoor(
                "1.5",
                1.5,
                1.5,
                0));
        metal.add(new LimitationDoor(
                "2.0",
                2.0,
                2.0,
                0));

        widthDoor = new ArrayList<>();
        widthDoor.add(new LimitationDoor("1.2",
                500,
                1170,
                0));

        heightDoor = new ArrayList<>();
        heightDoor.add(new LimitationDoor("1.2",
                1500,
                2200,
                0));

        deepnessDoor = new ArrayList<>();
        deepnessDoor.add(new LimitationDoor("97",
                97,
                97,
                1));

        thicknessDoorLeaf = new ArrayList<>();
        thicknessDoorLeaf.add(new LimitationDoor("60",
                60,
                60,
                1));

        colors = new ArrayList<>();
        colors.add(new DoorColors("1004","images/Door/AColor1/1004.jpg"));
        colors.add(new DoorColors("1013","images/Door/AColor1/1013.jpg"));
        colors.add(new DoorColors("1014","images/Door/AColor1/1014.jpg"));
        colors.add(new DoorColors("1015","images/Door/AColor1/1015.jpg"));
        colors.add(new DoorColors("1021","images/Door/AColor1/1021.jpg"));

        colors.add(new DoorColors("1028","images/Door/AColor1/1028.jpg"));
        colors.add(new DoorColors("1034","images/Door/AColor1/1034.jpg"));
        colors.add(new DoorColors("2004","images/Door/AColor1/2004.jpg"));
        colors.add(new DoorColors("2005","images/Door/AColor1/2005.jpg"));
        colors.add(new DoorColors("3003","images/Door/AColor1/3003.jpg"));

        colors.add(new DoorColors("3012","images/Door/AColor1/3012.jpg"));
        colors.add(new DoorColors("3020","images/Door/AColor1/3020.jpg"));
        colors.add(new DoorColors("4003","images/Door/AColor1/4003.jpg"));
        colors.add(new DoorColors("5002","images/Door/AColor1/5002.jpg"));
        colors.add(new DoorColors("5005","images/Door/AColor1/5005.jpg"));

    }

    public List<LimitationDoor> getMetal() {
        return metal;
    }

    public void setMetal(List<LimitationDoor> metal) {
        this.metal = metal;
    }

    public List<LimitationDoor> getWidthDoor() {
        return widthDoor;
    }

    public void setWidthDoor(List<LimitationDoor> widthDoor) {
        this.widthDoor = widthDoor;
    }

    public List<LimitationDoor> getHeightDoor() {
        return heightDoor;
    }

    public void setHeightDoor(List<LimitationDoor> heightDoor) {
        this.heightDoor = heightDoor;
    }

    public List<LimitationDoor> getDeepnessDoor() {
        return deepnessDoor;
    }

    public void setDeepnessDoor(List<LimitationDoor> deepnessDoor) {
        this.deepnessDoor = deepnessDoor;
    }

    public List<LimitationDoor> getThicknessDoorLeaf() {
        return thicknessDoorLeaf;
    }

    public void setThicknessDoorLeaf(List<LimitationDoor> thicknessDoorLeaf) {
        this.thicknessDoorLeaf = thicknessDoorLeaf;
    }

    public List<DoorColors> getColors() {
        return colors;
    }

    public void setColors(List<DoorColors> colors) {
        this.colors = colors;
    }
}
