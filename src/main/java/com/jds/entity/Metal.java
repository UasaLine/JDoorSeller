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
    private String id_manufacturer_program;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_displayed", nullable = false)
    private String name_displayed;

    @Column(name = "index_heft")
    private int index_heft;

    @Column(name = "is_used")
    private int is_used;

    public Metal() {
        //empty constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_manufacturer_program() {
        return id_manufacturer_program;
    }

    public void setId_manufacturer_program(String id_manufacturer_program) {
        this.id_manufacturer_program = id_manufacturer_program;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_displayed() {
        return name_displayed;
    }

    public void setName_displayed(String name_displayed) {
        this.name_displayed = name_displayed;
    }

    public int getIndex_heft() {
        return index_heft;
    }

    public void setIndex_heft(int index_heft) {
        this.index_heft = index_heft;
    }

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }
}
