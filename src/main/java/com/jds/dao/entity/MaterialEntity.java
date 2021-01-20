package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "materials")
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "id_manufacturer_program")
    private String idManufacturerProgram;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "components", referencedColumnName = "id")
    private MaterialComponents components;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="components_parent_id", nullable=false)
    private MaterialComponents parent;

    @Column(name = "price")
    private int price;
}
