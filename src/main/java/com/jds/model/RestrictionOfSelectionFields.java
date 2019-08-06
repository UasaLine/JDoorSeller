package com.jds.model;

import com.jds.entity.DoorColors;
import com.jds.entity.DoorFurniture;
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
    private List<LimitationDoor>  additionalDoorSetting;
    private List<DoorFurniture>  topLock;
    private List<DoorFurniture>  lowerLock;
    private List<DoorFurniture>  handle;
    private List<DoorFurniture> lowerlockCylinder;
    private List<DoorFurniture> closer;
    private List<DoorFurniture> endDoorLock;


    public  RestrictionOfSelectionFields stuffColors(List<DoorColors> colors){
        this.setColors(colors);
        return this;
    }

    public RestrictionOfSelectionFields addTopLock(List<DoorFurniture> furnitures){
        topLock = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            topLock.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerLock(List<DoorFurniture> furnitures){
        lowerLock = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            lowerLock.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addHendle(List<DoorFurniture> furnitures){
        handle = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            handle.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerlockCylinder(List<DoorFurniture> furnitures){
        lowerlockCylinder = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            lowerlockCylinder.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addCloser(List<DoorFurniture> furnitures){
        closer = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            closer.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addEndDoorLock(List<DoorFurniture> furnitures){
        endDoorLock = new ArrayList<>();
        for(DoorFurniture furniture:furnitures){
            furniture.setDoorType(null);
            endDoorLock.add(furniture);
        }
        return this;
    }


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

        additionalDoorSetting = new  ArrayList<>();
        additionalDoorSetting.add(new LimitationDoor("doorstep",
                1,
                1,
                1));
        additionalDoorSetting.add(new LimitationDoor("stainlessSteelDoorstep",
                1,
                0,
                0));
        additionalDoorSetting.add(new LimitationDoor("firstSealingLine",
                "firstSealingLine_1",
                1,
                1,
                1));
        additionalDoorSetting.add(new LimitationDoor("secondSealingLine",
                "secondSealingLine_2",
                2,
                2,
                1));

        additionalDoorSetting.add(new LimitationDoor("topDoorTrim",
                1,
                0,
                1));
        additionalDoorSetting.add(new LimitationDoor("leftDoorTrim",
                1,
                0,
                1));
        additionalDoorSetting.add(new LimitationDoor("rightDoorTrim",
                1,
                0,
                1));

    }

    public List<LimitationDoor> getAdditionalDoorSetting() {
        return additionalDoorSetting;
    }

    public void setAdditionalDoorSetting(List<LimitationDoor> additionalDoorSetting) {
        this.additionalDoorSetting = additionalDoorSetting;
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

    public List<DoorFurniture> getTopLock() {
        return topLock;
    }

    public void setTopLock(List<DoorFurniture> topLock) {
        this.topLock = topLock;
    }

    public List<DoorFurniture> getLowerLock() {
        return lowerLock;
    }

    public void setLowerLock(List<DoorFurniture> lowerLock) {
        this.lowerLock = lowerLock;
    }

    public List<DoorFurniture> getHandle() {
        return handle;
    }

    public void setHandle(List<DoorFurniture> handle) {
        this.handle = handle;
    }

    public List<DoorFurniture> getLowerlockCylinder() {
        return lowerlockCylinder;
    }

    public void setLowerlockCylinder(List<DoorFurniture> lowerlockCylinder) {
        this.lowerlockCylinder = lowerlockCylinder;
    }

    public List<DoorFurniture> getCloser() {
        return closer;
    }

    public void setCloser(List<DoorFurniture> closer) {
        this.closer = closer;
    }

    public List<DoorFurniture> getEndDoorLock() {
        return endDoorLock;
    }

    public void setEndDoorLock(List<DoorFurniture> endDoorLock) {
        this.endDoorLock = endDoorLock;
    }
}
