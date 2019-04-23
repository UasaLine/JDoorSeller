package jds3.entity;

import javax.persistence.*;
import java.util.List;

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

    //private List<Limitation> limitationList;

    //private List<DoorType> doorTypeList;


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

        if (this.fireproof == 1)
            return true;
        else
            return false;

    }

    public void setFireproof(boolean fireproof) {

        if (fireproof)
            this.fireproof = 1;
        else
            this.fireproof = 0;
    }

    public boolean isHot() {
        if (this.hot == 1)
            return true;
        else
            return false;

    }

    public void setHot(boolean hot) {

        if (hot)
            this.hot = 1;
        else
            this.hot = 0;
    }
}
