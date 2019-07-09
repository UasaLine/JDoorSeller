package com.jds.model;

import com.jds.entity.LimitationDoor;

import java.util.ArrayList;
import java.util.List;

public class RestrictionOfSelectionFields {

    private List<LimitationDoor> metal;
    private List<LimitationDoor> widthDoor;
    private List<LimitationDoor> heightDoor;
    private List<LimitationDoor> deepnessDoor;
    private List<LimitationDoor> thicknessDoorLeaf;


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
}
