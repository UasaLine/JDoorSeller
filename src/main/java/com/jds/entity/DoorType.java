package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Door_Type")
public class DoorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "namePicture")
    private String namePicture;

    @Column(name = "doorLeaf")
    private int doorLeaf;

    @Column(name = "nameForPrint")
    private String nameForPrint;

    @Column(name = "nameForPrintInternalOpening")
    private String nameForPrintInternalOpening;

    @Column(name = "daysToRelease")
    private int daysToRelease;

    @Column(name = "markUp")
    private int markUp;

    @Column(name = "markUpGlassPackage")
    private int markUpGlassPackage;

    @Column(name = "DS")
    private int DS;//I do not know

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<SizeOfDoorParts> sizeOfDoorPartsList;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorClass")
    private DoorClass doorClass;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<LimitationDoor> limitationList;

    public List<SizeOfDoorParts> getSizeOfDoorPartsList() {
        return sizeOfDoorPartsList;
    }

    public void setSizeOfDoorPartsList(List<SizeOfDoorParts> sizeOfDoorPartsList) {
        this.sizeOfDoorPartsList = sizeOfDoorPartsList;
    }

    public DoorType() {
        //empty constructor
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

    public String getNamePicture() {
        return namePicture;
    }

    public void setNamePicture(String namePicture) {
        this.namePicture = namePicture;
    }

    public int getDoorLeaf() {
        return doorLeaf;
    }

    public void setDoorLeaf(int doorLeaf) {
        this.doorLeaf = doorLeaf;
    }

    public String getNameForPrint() {
        return nameForPrint;
    }

    public void setNameForPrint(String nameForPrint) {
        this.nameForPrint = nameForPrint;
    }

    public String getNameForPrintInternalOpening() {
        return nameForPrintInternalOpening;
    }

    public void setNameForPrintInternalOpening(String nameForPrintInternalOpening) {
        this.nameForPrintInternalOpening = nameForPrintInternalOpening;
    }

    public int getDaysToRelease() {
        return daysToRelease;
    }

    public void setDaysToRelease(int daysToRelease) {
        this.daysToRelease = daysToRelease;
    }

    public int getMarkUp() {
        return markUp;
    }

    public void setMarkUp(int markUp) {
        this.markUp = markUp;
    }

    public int getMarkUpGlassPackage() {
        return markUpGlassPackage;
    }

    public void setMarkUpGlassPackage(int markUpGlassPackage) {
        this.markUpGlassPackage = markUpGlassPackage;
    }

    public int getDS() {
        return DS;
    }

    public void setDS(int DS) {
        this.DS = DS;
    }

    public DoorClass getDoorClass() {
        return doorClass;
    }

    public void setDoorClass(DoorClass doorClass) {
        this.doorClass = doorClass;
    }

    public List<LimitationDoor> getLimitationList() {
        return limitationList;
    }

    public void setLimitationList(List<LimitationDoor> limitationList) {
        this.limitationList = limitationList;
    }
}
