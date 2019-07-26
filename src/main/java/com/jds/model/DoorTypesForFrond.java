package com.jds.model;

import com.jds.entity.DoorType;

public class DoorTypesForFrond {

    private int id;
    private int doorLeaf;
    private String namePicture;

    public DoorTypesForFrond() {
    }

    public DoorTypesForFrond(DoorType doorType) {
        id = doorType.getId();
        doorLeaf =  doorType.getDoorLeaf();
        namePicture = doorType.getNamePicture();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoorLeaf() {
        return doorLeaf;
    }

    public void setDoorLeaf(int doorLeaf) {
        this.doorLeaf = doorLeaf;
    }

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }
}
