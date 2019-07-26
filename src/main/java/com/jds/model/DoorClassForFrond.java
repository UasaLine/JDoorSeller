package com.jds.model;

import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;

import java.util.ArrayList;
import java.util.List;

public class DoorClassForFrond {

    private int id;
    private String name;
    private List<DoorTypesForFrond> doorTypes;

    public DoorClassForFrond() {
    }

    public DoorClassForFrond(DoorClass doorClass) {
        this.id = doorClass.getId();
        this.name = doorClass.getName();
        this.doorTypes = convertDoorTypesToDoorTypesForFrond(doorClass.getDoorTypes());
    }

    private  List<DoorTypesForFrond> convertDoorTypesToDoorTypesForFrond(List<DoorType> Types){

        List<DoorTypesForFrond> TypesForFrond = new ArrayList<>();
        for(DoorType doorType:Types){
            TypesForFrond.add(new DoorTypesForFrond(doorType));
        }
        return TypesForFrond;
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

    public List<DoorTypesForFrond> getDoorTypes() {
        return doorTypes;
    }

    public void setDoorTypes(List<DoorTypesForFrond> doorTypes) {
        this.doorTypes = doorTypes;
    }
}
