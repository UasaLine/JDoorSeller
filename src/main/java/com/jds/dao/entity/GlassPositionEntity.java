package com.jds.dao.entity;

import com.jds.model.enumModels.GlassPositionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "glass_position_entity")
public class GlassPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private GlassPositionType type;
    @Column(name = "value1")
    private int value1;
    @Column(name = "value2")
    private int value2;
    @Column(name = "value3")
    private int value3;
    @Column(name = "value4")
    private int value4;
}
