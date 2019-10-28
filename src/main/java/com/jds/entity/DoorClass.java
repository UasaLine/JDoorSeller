package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Door_Class")
public class DoorClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "fireproof", nullable = false)
    private int fireproof;

    @Column(name = "hot", nullable = false)
    private int hot;

    //@Column(name = "valid", nullable = false)
    //private int valid;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doorClass")
    private List<DoorType> doorTypes;

    public DoorClass clearNonSerializingFields(){

        for(DoorType doorType:doorTypes){
            doorType.clearNonSerializingFields();
        }

        return this;
    }

    public DoorClass() {
    }

    public DoorClass(int id,String name, String description, int fireproof,int hot) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fireproof = fireproof;
        this.hot = hot;
    }
}
