package com.jds.entity;


import javax.persistence.*;

@Entity
@Table(name = "Door_Furniture")
public class DoorFurniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "typeOfFurniture")
    private String typeOfFurniture;

    //@Column(name = "doorType")
    //private DoorType doorType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "itCylinderLock")
    private int itCylinderLock;

    @Column(name = "isTwoSectionLock")
    private int isTwoSectionLock;

    @Column(name = "comment")
    private String comment;

    @Column(name = "longKey")
    private int longKey;

    @Column(name = "bugelHandle")
    private int bugelHandle;

    @Column(name = "аrmorLock")
    private int аrmorLock;

    @Column(name = "picturePathFirst")
    private String picturePathFirst;

    @Column(name = "picturePathSecond")
    private String picturePathSecond;

    @Column(name = "sketchPathFirst")
    private String sketchPathFirst;

    @Column(name = "sketchPathSecond")
    private String sketchPathSecond;

    @Column(name = "forWarmDoors")
    private int forWarmDoors;

    @Column(name = "numberOfDoorLeaves")
    private int numberOfDoorLeaves;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdManufacturerProgram() {
        return idManufacturerProgram;
    }

    public void setIdManufacturerProgram(String idManufacturerProgram) {
        this.idManufacturerProgram = idManufacturerProgram;
    }

    public String getTypeOfFurniture() {
        return typeOfFurniture;
    }

    public void setTypeOfFurniture(String typeOfFurniture) {
        this.typeOfFurniture = typeOfFurniture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItCylinderLock() {
        return itCylinderLock;
    }

    public void setItCylinderLock(int itCylinderLock) {
        this.itCylinderLock = itCylinderLock;
    }

    public int getIsTwoSectionLock() {
        return isTwoSectionLock;
    }

    public void setIsTwoSectionLock(int isTwoSectionLock) {
        this.isTwoSectionLock = isTwoSectionLock;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLongKey() {
        return longKey;
    }

    public void setLongKey(int longKey) {
        this.longKey = longKey;
    }

    public int getBugelHandle() {
        return bugelHandle;
    }

    public void setBugelHandle(int bugelHandle) {
        this.bugelHandle = bugelHandle;
    }

    public int getАrmorLock() {
        return аrmorLock;
    }

    public void setАrmorLock(int аrmorLock) {
        this.аrmorLock = аrmorLock;
    }

    public String getPicturePathFirst() {
        return picturePathFirst;
    }

    public void setPicturePathFirst(String picturePathFirst) {
        this.picturePathFirst = picturePathFirst;
    }

    public String getPicturePathSecond() {
        return picturePathSecond;
    }

    public void setPicturePathSecond(String picturePathSecond) {
        this.picturePathSecond = picturePathSecond;
    }

    public String getSketchPathFirst() {
        return sketchPathFirst;
    }

    public void setSketchPathFirst(String sketchPathFirst) {
        this.sketchPathFirst = sketchPathFirst;
    }

    public String getSketchPathSecond() {
        return sketchPathSecond;
    }

    public void setSketchPathSecond(String sketchPathSecond) {
        this.sketchPathSecond = sketchPathSecond;
    }

    public int getForWarmDoors() {
        return forWarmDoors;
    }

    public void setForWarmDoors(int forWarmDoors) {
        this.forWarmDoors = forWarmDoors;
    }

    public int getNumberOfDoorLeaves() {
        return numberOfDoorLeaves;
    }

    public void setNumberOfDoorLeaves(int numberOfDoorLeaves) {
        this.numberOfDoorLeaves = numberOfDoorLeaves;
    }
}
