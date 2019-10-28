package com.jds.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.LimiItem;
import com.jds.model.modelEnum.TypeOfFurniture;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Door_Furniture")
public class DoorFurniture implements LimiItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "typeOfFurniture")
    @Enumerated(EnumType.STRING)
    private TypeOfFurniture typeOfFurniture;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

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

    @Column(name = "armorLock")
    private int armorLock;

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

    @Column(name = "price")
    private double price;

    @Column(name = "pricecomit")
    private String priceComit;

    //glass

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toning")
    private List<DoorGlass> toning;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeDoorGlass")
    private List<DoorGlass> typeDoorGlass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "armor")
    private List<DoorGlass> armors;

    //Furniture

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topLock")
    private List<DoorFurniture> topLock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topinternaLockDecoration")
    private List<DoorFurniture> topinternaLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topouterLockDecoration")
    private List<DoorFurniture> topouterLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toplockCylinder")
    private List<DoorFurniture> toplockCylinder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerLock")
    private List<DoorFurniture> lowerLock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerinternaLockDecoration")
    private List<DoorFurniture> lowerinternaLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerouterLockDecoration")
    private List<DoorFurniture> lowerouterLockDecoration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lowerlockCylinder")
    private List<DoorFurniture> lowerlockCylinder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "handle")
    private List<DoorFurniture> handle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "closer")
    private List<DoorFurniture> closer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endDoorLock")
    private List<DoorFurniture> endDoorLock;



    public DoorFurniture setNuulLazyFild(){
        setDoorType(null);
        setToning(null);
        setArmors(null);
        setTypeDoorGlass(null);
        return this;
    }

    public void makeRightNamePictureDoorType(){

        picturePathFirst = picturePathFirst.replace("CalculationDoorsAutonomous","images");
        picturePathFirst = picturePathFirst + ".jpg";

        sketchPathFirst = sketchPathFirst.replace("CalculationDoorsAutonomous","images");
        sketchPathSecond = sketchPathSecond.replace("CalculationDoorsAutonomous","images");
    }

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

    public TypeOfFurniture getTypeOfFurniture() {
        return typeOfFurniture;
    }

    public void setTypeOfFurniture(TypeOfFurniture typeOfFurniture) {
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

    public int getArmorLock() {
        return armorLock;
    }

    public void setArmorLock(int armorLock) {
        this.armorLock = armorLock;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceComit() {
        return priceComit;
    }

    public void setPriceComit(String priceComit) {
        this.priceComit = priceComit;
    }


    public List<DoorGlass> getTypeDoorGlass() {
        return typeDoorGlass;
    }

    public void setTypeDoorGlass(List<DoorGlass> typeDoorGlass) {
        this.typeDoorGlass = typeDoorGlass;
    }

    public List<DoorGlass> getToning() {
        return toning;
    }

    public void setToning(List<DoorGlass> toning) {
        this.toning = toning;
    }

    public List<DoorGlass> getArmors() {
        return armors;
    }

    public void setArmors(List<DoorGlass> armors) {
        this.armors = armors;
    }
}
