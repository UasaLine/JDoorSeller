package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.enumClasses.PriceGroups;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Door_Type")
@Getter
@Setter
public class DoorType implements Comparable<DoorType> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    private int DS;//I do not know what is this

    @Column(name = "priceList")
    int priceList;

    @Column(name = "retailPrice")
    double retailPrice;

    @Column(name = "wholesalePriceFromStock1")
    double wholesalePriceFromStock1;

    @Column(name = "wholesalePriceFromStock2")
    double wholesalePriceFromStock2;

    @Column(name = "wholesalePriceFromOrder")
    double wholesalePriceFromOrder;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<BendSetting> bendSettings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType")
    private List<DoorFurniture> doorFurnitures;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorClass")
    private DoorClass doorClass;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<SizeOfDoorParts> sizeOfDoorPartsList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<LimitationDoor> limitationList;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<SpecificationSetting> specificationSettings;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorType", cascade = CascadeType.ALL)
    private List<DoorEntity> doorEntityList;

    public DoorType clearNonSerializingFields() {

        doorClass = null;
        bendSettings = null;
        doorFurnitures = null;
        sizeOfDoorPartsList = null;
        limitationList = null;
        specificationSettings = null;
        doorEntityList = null;

        return this;
    }

    public void makeRightNamePictureDoorType() {

        namePicture = namePicture.replace("CalculationDoorsAutonomous", "images");
        namePicture = namePicture.replace("\\", "/");

        int index = namePicture.lastIndexOf(".jpg");
        if (index == -1) {
            namePicture = namePicture + ".jpg";
        }

    }

    public List<BendSetting> getBendSettings() {
        return bendSettings;
    }

    public void setBendSettings(List<BendSetting> bendSettings) {
        this.bendSettings = bendSettings;
    }

    public List<SizeOfDoorParts> getSizeOfDoorPartsList() {
        return sizeOfDoorPartsList;
    }

    public void setSizeOfDoorPartsList(List<SizeOfDoorParts> sizeOfDoorPartsList) {
        this.sizeOfDoorPartsList = sizeOfDoorPartsList;
    }

    public DoorType() {
        //empty constructor
    }

    public List<LimitationDoor> getLimitationList() {
        return limitationList;
    }

    public void setLimitationList(List<LimitationDoor> limitationList) {
        this.limitationList = limitationList;
    }

    public List<DoorFurniture> getDoorFurnitures() {
        return doorFurnitures;
    }

    public void setDoorFurnitures(List<DoorFurniture> doorFurnitures) {
        this.doorFurnitures = doorFurnitures;
    }

    public List<SpecificationSetting> getSpecificationSettings() {
        return specificationSettings;
    }

    public void setSpecificationSettings(List<SpecificationSetting> specificationSettings) {
        this.specificationSettings = specificationSettings;
    }

    public List<DoorEntity> getDoorEntityList() {
        return doorEntityList;
    }

    public void setDoorEntityList(List<DoorEntity> doorEntityList) {
        this.doorEntityList = doorEntityList;
    }

    @Override
    public int compareTo(DoorType doorType) {
        return this.getName().compareTo(doorType.getName());
    }

    public static boolean isNotNew(int typeId) {
        return typeId > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoorType)) return false;
        DoorType doorType = (DoorType) o;
        return id == doorType.id &&
                Double.compare(doorType.wholesalePriceFromStock1, wholesalePriceFromStock1) == 0 &&
                Double.compare(doorType.wholesalePriceFromStock2, wholesalePriceFromStock2) == 0 &&
                Double.compare(doorType.wholesalePriceFromOrder, wholesalePriceFromOrder) == 0 &&
                name.equals(doorType.name) &&
                Objects.equals(bendSettings, doorType.bendSettings) &&
                Objects.equals(doorFurnitures, doorType.doorFurnitures) &&
                Objects.equals(doorClass, doorType.doorClass) &&
                Objects.equals(sizeOfDoorPartsList, doorType.sizeOfDoorPartsList) &&
                Objects.equals(limitationList, doorType.limitationList) &&
                Objects.equals(specificationSettings, doorType.specificationSettings) &&
                Objects.equals(doorEntityList, doorType.doorEntityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public double getPrice(PriceGroups priceGroups) {
        double price = 0;
        switch (priceGroups) {
            case RETAIL_PRICE:
                price = getRetailPrice();
                break;
            case WHOLESALE_PRICE:
                price = getWholesalePriceFromStock1();
                break;
            case PRICE_OVER_1_MILLION_PER_MONTH:
                price = getWholesalePriceFromStock2();
                break;
            case WHOLESALE_PRICE_ON_PREPAYMENT:
                price = getWholesalePriceFromOrder();
                break;
        }
        return price;
    }
}
