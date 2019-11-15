package com.jds.model;

import com.jds.entity.DoorColors;
import com.jds.entity.DoorFurniture;
import com.jds.entity.LimitationDoor;
import com.jds.entity.Metal;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionOfSelectionFields {

    private int doorTypeid;
    private List<LimitationDoor> metal = new ArrayList<>();
    private List<LimitationDoor> widthDoor = new ArrayList<>();
    private List<LimitationDoor> heightDoor = new ArrayList<>();
    private List<LimitationDoor> deepnessDoor = new ArrayList<>();
    private List<LimitationDoor> thicknessDoorLeaf = new ArrayList<>();
    private List<DoorColors> colors = new ArrayList<>();
    private List<LimitationDoor> additionalDoorSetting = new ArrayList<>();

    private List<LimitationDoor> doorstep = new ArrayList<>();
    private List<LimitationDoor> stainlessSteelDoorstep = new ArrayList<>();

    private List<LimitationDoor> firstSealingLine = new ArrayList<>();
    private List<LimitationDoor> secondSealingLine = new ArrayList<>();
    private List<LimitationDoor> thirdSealingLine = new ArrayList<>();

    private List<DoorFurniture> topLock = new ArrayList<>();
    private List<DoorFurniture> lowerLock = new ArrayList<>();
    private List<DoorFurniture> handle = new ArrayList<>();
    private List<DoorFurniture> lockCylinder = new ArrayList<>();
    private List<DoorFurniture> topInLockDecor = new ArrayList<>();
    private List<DoorFurniture> topOutLockDecor = new ArrayList<>();
    private List<DoorFurniture> lowerInLockDecor = new ArrayList<>();
    private List<DoorFurniture> lowerOutLockDecor = new ArrayList<>();
    private List<DoorFurniture> closer = new ArrayList<>();
    private List<DoorFurniture> endDoorLock = new ArrayList<>();

    private List<DoorFurniture> typeDoorGlass = new ArrayList<>();
    private List<DoorFurniture> toning = new ArrayList<>();
    private List<DoorFurniture> armor = new ArrayList<>();


    //replace with getting data from the database
    public RestrictionOfSelectionFields(int doorTypeId) {

        this.doorTypeid = doorTypeId;

        widthDoor = new ArrayList<>();
        widthDoor.add(new LimitationDoor("1.2",500,1170,0));

        heightDoor = new ArrayList<>();
        heightDoor.add(new LimitationDoor("1.2", 1500,2200,0));

        deepnessDoor = new ArrayList<>();
        deepnessDoor.add(new LimitationDoor("97",97, 97, 1));

        thicknessDoorLeaf = new ArrayList<>();
        thicknessDoorLeaf.add(new LimitationDoor("60",60,60,1));

        additionalDoorSetting = new ArrayList<>();
        additionalDoorSetting.add(new LimitationDoor("doorstep",1, 1,1));
        additionalDoorSetting.add(new LimitationDoor("stainlessSteelDoorstep",1,0,0));
        additionalDoorSetting.add(new LimitationDoor("firstSealingLine","firstSealingLine_1",1, 1,
                1));
        additionalDoorSetting.add(new LimitationDoor("secondSealingLine","secondSealingLine_2",2,2,
                1));
        additionalDoorSetting.add(new LimitationDoor("topDoorTrim",1,0,
                1));
        additionalDoorSetting.add(new LimitationDoor("leftDoorTrim",1,0,
                1));
        additionalDoorSetting.add(new LimitationDoor("rightDoorTrim",1,0,
                1));

        doorstep = new ArrayList<>();
        doorstep.add(new LimitationDoor("doorstep",1,1,1));
        doorstep.add(new LimitationDoor("doorstep",0,1,0));

        stainlessSteelDoorstep = new ArrayList<>();
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep",1,1,
                1));
        stainlessSteelDoorstep.add(new LimitationDoor("stainlessSteelDoorstep",0, 1,
                0));

        firstSealingLine = new ArrayList<>();
        firstSealingLine.add(new LimitationDoor("doorstep", 1, 1,1));
        firstSealingLine.add(new LimitationDoor("doorstep",0,1,0));

        secondSealingLine = new ArrayList<>();
        secondSealingLine.add(new LimitationDoor("doorstep",1,1,1));
        secondSealingLine.add(new LimitationDoor("doorstep",0,1,0));

        thirdSealingLine = new ArrayList<>();
        thirdSealingLine.add(new LimitationDoor("doorstep",1,1,1));
        thirdSealingLine.add(new LimitationDoor("doorstep",2,0,0));
        thirdSealingLine.add(new LimitationDoor("doorstep",3,0,0));
    }

    public void addMetal(LimitationDoor limit) {
        this.metal.add(limit.setNuulLazyFild());
    }

    public void addMetal(@NonNull Metal metal) {
        this.metal.add(
                new LimitationDoor(String.valueOf(metal.getNameDisplayed()),
                        metal.getNameDisplayed(),
                        metal.getNameDisplayed(),
                        0));
    }


    public RestrictionOfSelectionFields widthDoor(List<LimitationDoor> widthDoor) {
        this.setWidthDoor(widthDoor);
        return this;
    }

    public void addWidthDoor(LimitationDoor limit) {
        this.widthDoor.add(limit.setNuulLazyFild());
    }

    public RestrictionOfSelectionFields heightDoor(List<LimitationDoor> heightDoor) {
        this.setHeightDoor(heightDoor);
        return this;
    }

    public void addHeightDoor(LimitationDoor limit) {
        this.heightDoor.add(limit.setNuulLazyFild());
    }

    public void addDeepnessDoor(LimitationDoor deepnessDoor) {
        this.deepnessDoor.add(deepnessDoor.setNuulLazyFild());
    }

    public void addThicknessDoorLeaf(LimitationDoor thicknessDoorLeaf) {
        this.thicknessDoorLeaf.add(thicknessDoorLeaf.setNuulLazyFild());
    }

    public void addDoorstep(LimitationDoor doorstep) {
        this.doorstep.add(doorstep.setNuulLazyFild());
    }

    public void addStainlessSteelDoorstep(LimitationDoor stainlessSteelDoorstep) {
        this.stainlessSteelDoorstep.add(stainlessSteelDoorstep.setNuulLazyFild());
    }

    public void addFirstSealingLine(LimitationDoor lim) {
        this.firstSealingLine.add(lim.setNuulLazyFild());
    }

    public void addSecondSealingLine(LimitationDoor lim) {
        this.secondSealingLine.add(lim.setNuulLazyFild());
    }

    public void addThirdSealingLine(LimitationDoor lim) {

        this.thirdSealingLine.add(lim.setNuulLazyFild());
    }

    public RestrictionOfSelectionFields stuffColors(List<DoorColors> colors) {
        this.setColors(colors);
        return this;
    }

    public void addColors(DoorColors color) {
        if (color != null) {
            this.colors.add(color);
        }
    }

    public RestrictionOfSelectionFields addTopLock(List<DoorFurniture> furnitures) {
        topLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            topLock.add(furniture);
        }
        return this;
    }

    public void addTopLock(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.topLock.add(furniture);
        }
    }

    public RestrictionOfSelectionFields addLowerLock(List<DoorFurniture> furnitures) {
        lowerLock = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lowerLock.add(furniture);
        }
        return this;
    }


    public void addLowerLock(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.lowerLock.add(furniture);
        }
    }

    public RestrictionOfSelectionFields addHendle(List<DoorFurniture> furnitures) {
        handle = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            handle.add(furniture);
        }
        return this;
    }

    public void addHandle(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.handle.add(furniture);
        }
    }

    public RestrictionOfSelectionFields addLockCylinder(List<DoorFurniture> furnitures) {
        lockCylinder = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lockCylinder.add(furniture);
        }
        return this;
    }

    public void addLockCylinder(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.lockCylinder.add(furniture);
        }
    }

    public RestrictionOfSelectionFields addCloser(List<DoorFurniture> furnitures) {
        closer = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            closer.add(furniture);
        }
        return this;
    }

    public void addTopInLockDecor(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.topInLockDecor.add(furniture);
        }
    }

    public void addTopOutLockDecor(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.topOutLockDecor.add(furniture);
        }
    }

    public void addLowerInLockDecor(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.lowerInLockDecor.add(furniture);
        }
    }

    public void addLowerOutLockDecor(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.lowerOutLockDecor.add(furniture);
        }
    }

    public void addCloser(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.closer.add(furniture);
        }
    }

    public void addEndDoorLock(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.endDoorLock.add(furniture);
        }
    }

    public void addTypeDoorGlass(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.typeDoorGlass.add(furniture);
        }
    }

    public void addToning(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.toning.add(furniture);
        }
    }

    public void addArmor(DoorFurniture furniture) {
        if (furniture != null) {
            furniture.setNuulLazyFild();
            this.armor.add(furniture);
        }
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

    public RestrictionOfSelectionFields addTopInLockDecor(List<DoorFurniture> furnitures) {
        topInLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            topInLockDecor.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addTopOutLockDecor(List<DoorFurniture> furnitures) {
        topOutLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            topOutLockDecor.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerInLockDecor(List<DoorFurniture> furnitures) {
        lowerInLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lowerInLockDecor.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields addLowerOutLockDecor(List<DoorFurniture> furnitures) {
        lowerOutLockDecor = new ArrayList<>();
        for (DoorFurniture furniture : furnitures) {
            furniture.setNuulLazyFild();
            lowerOutLockDecor.add(furniture);
        }
        return this;
    }

    public RestrictionOfSelectionFields stuffMetal(@NonNull List<Metal> metals) {

        metals.stream().forEach((metal) -> this.addMetal(metal));

        return this;

    }
}
