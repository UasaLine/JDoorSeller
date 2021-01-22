package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "components")
public class MaterialComponents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "materials_components",
            joinColumns = {@JoinColumn(name = "components_id")},
            inverseJoinColumns = {@JoinColumn(name = "materials_id")}
    )
    private List<MaterialEntity> materialList;

    @OneToOne(mappedBy = "components")
    private MaterialEntity material;
}
