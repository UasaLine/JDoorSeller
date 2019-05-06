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

    @Column(name = "doorType")
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

}
