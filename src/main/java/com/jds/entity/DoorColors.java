package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "Door_Colors")
public class DoorColors {
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

    public DoorColors(String name, String picturePath) {
        this.name = name;
        this.picturePath = picturePath;
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
}
