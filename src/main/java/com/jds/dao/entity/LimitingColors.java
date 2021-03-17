package com.jds.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "limiting_colors")
public class LimitingColors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "master_id")
    private ImageEntity maserImage;

    @OneToOne
    @JoinColumn(name = "slave_id")
    private ImageEntity slaveImage;
}
