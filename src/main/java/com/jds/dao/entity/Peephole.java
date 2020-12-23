package com.jds.dao.entity;

import com.jds.model.modelEnum.PeepholePosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "peephole")
public class Peephole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "manufacturer_id")
    String idManufacturerProgram;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    PeepholePosition position;

    @Column(name = "height")
    int height;
}
