package com.jds.entity;

import com.jds.model.LimiItem;

import javax.persistence.*;

@Entity
@Table(name = "Door_Colors")
public class DoorColors implements LimiItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "name")
    private String name;

    @Column(name = "picturePathFirst")
    private String picturePath;

    @Column(name = "price")
    private int pricePaintingMeterOfSpace;

    @Column(name = "smooth")
    private int smooth;

    public int getPricePaintingMeterOfSpace() {
        return pricePaintingMeterOfSpace;
    }

    public void setPricePaintingMeterOfSpace(int pricePaintingMeterOfSpace) {
        this.pricePaintingMeterOfSpace = pricePaintingMeterOfSpace;
    }

    public DoorColors(String name, String picturePath, int price) {
        this.name = name;
        this.picturePath = picturePath;
        this.pricePaintingMeterOfSpace = price;
    }

    public DoorColors() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public int getSmooth() {
        return smooth;
    }

    public void setSmooth(int smooth) {
        this.smooth = smooth;
    }
}
