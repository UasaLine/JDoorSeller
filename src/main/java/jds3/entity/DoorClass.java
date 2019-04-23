package jds3.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Door_Class")
public class DoorClass {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "fireproof", nullable = false)
    private boolean fireproof;

    @Column(name = "hot", nullable = false)
    private boolean hot;

    private List<Limitation> limitationList;

    private List<DoorType> doorTypeList;


    public DoorClass() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFireproof() {
        return fireproof;
    }

    public void setFireproof(boolean fireproof) {
        this.fireproof = fireproof;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
}
