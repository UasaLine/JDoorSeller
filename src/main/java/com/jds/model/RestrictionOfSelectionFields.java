package com.jds.model;

import com.jds.entity.DoorColors;
import com.jds.entity.DoorFurniture;
import com.jds.entity.LimitationDoor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestrictionOfSelectionFields {

    private List<LimitationDoor> metal;
    private List<LimitationDoor> widthDoor;
    private List<LimitationDoor> heightDoor;
    private List<LimitationDoor> deepnessDoor;
    private List<LimitationDoor> thicknessDoorLeaf;
    private List<DoorColors> colors;
    private List<LimitationDoor> additionalDoorSetting;

    private List<LimitationDoor> doorstep;
    private List<LimitationDoor> stainlessSteelDoorstep;
    private List<LimitationDoor> topDoorTrim;
    private List<LimitationDoor> leftDoorTrim;
    private List<LimitationDoor> rightDoorTrim;


    private List<LimitationDoor> firstSealingLine;
    private List<LimitationDoor> secondSealingLine;
    private List<LimitationDoor> thirdSealingLine;

    private List<DoorFurniture> topLock;
    private List<DoorFurniture> lowerLock;
    private List<DoorFurniture> handle;
    private List<DoorFurniture> lowerlockCylinder;
    private List<DoorFurniture> closer;
    private List<DoorFurniture> endDoorLock;
    private List<DoorFurniture> typeDoorGlass;
    private List<DoorFurniture> toning;
    private List<DoorFurniture> armor;


    public RestrictionOfSelectionFields stuffColors(List<DoorColors> colors) {
        this.setColors(colors);
        return this;
    }

    public RestrictionOfSelectionFields addTopLock(List<DoorFurniture> furnitures) {
        topLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            topLock.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerLock(List<DoorFurniture> furnitures) {
        lowerLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lowerLock.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addHendle(List<DoorFurniture> furnitures) {
        handle = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            handle.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerlockCylinder(List<DoorFurniture> furnitures) {
        lowerlockCylinder = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lowerlockCylinder.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addCloser(List<DoorFurniture> furnitures) {
        closer = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            closer.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addEndDoorLock(List<DoorFurniture> furnitures) {
        endDoorLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            endDoorLock.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addGlass(List<DoorFurniture> furnitures) {
        typeDoorGlass = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            typeDoorGlass.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addToning(List<DoorFurniture> furnitures) {
        toning = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            toning.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addArmor(List<DoorFurniture> furnitures) {
        armor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            armor.add(furniture);
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

        additionalDoorSetting = new ArrayList<>();
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

        doorstep = new ArrayList<>();
        doorstep.add(new LimitationDoor("doorstep",
                1,
                1,
                1));
        doorstep.add(new LimitationDoor("doorstep",
                0,
                1,
                0));

        stainlessSteelDoorstep = new ArrayList<>();
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep",
                1,
                1,
                1));
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep",
                0,
                1,
                0));
    }


}
