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
    @JoinColumn(name = "doorClass_id")
    private DoorClass doorClass;

    @Column(name = "typeSettings")
    private String typeSettings;

    @Column(name = "firstItem")
    private String firstItem;

    @Column(name = "secondItem")
    private String secondItem;

    @Column(name = "startRestriction")
    private int startRestriction;

    @Column(name = "stopRestriction")
    private int stopRestriction;

    @Column(name = "comment")
    private String comment;

    @Column(name = "defaultValue")
    private int defaultValue;

    public LimitationDoor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoorClass getDoorClass() {
        return doorClass;
    }

    public void setDoorClass(DoorClass doorClass) {
        this.doorClass = doorClass;
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

    public int getStartRestriction() {
        return startRestriction;
    }

    public void setStartRestriction(int startRestriction) {
        this.startRestriction = startRestriction;
    }

    public int getStopRestriction() {
        return stopRestriction;
    }

    public void setStopRestriction(int stopRestriction) {
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
