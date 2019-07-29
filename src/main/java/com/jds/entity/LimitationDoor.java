package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "Limitation_Door")
public class LimitationDoor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "typeSettings")
    private String typeSettings;

    @Column(name = "firstItem")
    private String firstItem;

    @Column(name = "secondItem")
    private String secondItem;

    @Column(name = "startRestriction")
    private double startRestriction;

    @Column(name = "stopRestriction")
    private double stopRestriction;

    @Column(name = "comment")
    private String comment;

    @Column(name = "defaultValue")
    private int defaultValue;


    public LimitationDoor(String firstItem, double startRestriction, double stopRestriction, int defaultValue) {
        this.firstItem = firstItem;
        this.secondItem = firstItem;
        this.startRestriction = startRestriction;
        this.stopRestriction = stopRestriction;
        this.defaultValue = defaultValue;
    }

    public LimitationDoor(String firstItem,String secondItem, double startRestriction, double stopRestriction, int defaultValue) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.startRestriction = startRestriction;
        this.stopRestriction = stopRestriction;
        this.defaultValue = defaultValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public String getTypeSettings() {
        return typeSettings;
    }

    public void setTypeSettings(String typeSettings) {
        this.typeSettings = typeSettings;
    }

    public String getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(String firstItem) {
        this.firstItem = firstItem;
    }

    public String getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(String secondItem) {
        this.secondItem = secondItem;
    }

    public double getStartRestriction() {
        return startRestriction;
    }

    public void setStartRestriction(double startRestriction) {
        this.startRestriction = startRestriction;
    }

    public double getStopRestriction() {
        return stopRestriction;
    }

    public void setStopRestriction(double stopRestriction) {
        this.stopRestriction = stopRestriction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }


}
