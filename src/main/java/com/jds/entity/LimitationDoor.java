package com.jds.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "firstItem")
    private String firstItem;

    @Column(name = "secondItem")
    private String secondItem;

    @Column(name = "startRestriction")
    private double startRestriction;

    @Column(name = "stopRestriction")
    private double stopRestriction;

    @Column(name = "pairOfValues")
    private int pairOfValues;

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


}
