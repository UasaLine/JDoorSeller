package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShieldKit {
    private int id;
    private ImageEntity ShieldColor;
    private ImageEntity ShieldDesign;
    private DoorEntity door;
}
