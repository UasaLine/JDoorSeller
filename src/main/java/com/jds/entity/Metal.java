package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "Metal")
public class Metal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "id_manufacturer_program", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_displayed", nullable = false)
    private String nameDisplayed;

    @Column(name = "index_heft")
    private int indexHeft;

    @Column(name = "is_used")
    private int isUsed;

    @Column(name = "price")
    private int price;

    public Metal() {
        //empty constructor
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getNameDisplayed() {
        return nameDisplayed;
    }

    public void setNameDisplayed(String nameDisplayed) {
        this.nameDisplayed = nameDisplayed;
    }

    public int getIndexHeft() {
        return indexHeft;
    }

    public void setIndexHeft(int indexHeft) {
        this.indexHeft = indexHeft;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
