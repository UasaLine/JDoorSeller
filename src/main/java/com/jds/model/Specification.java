package com.jds.model;

import com.jds.entity.DoorType;
import com.jds.entity.LineSpecification;
import com.jds.entity.RawMaterials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specification {

    DoorType doorType;

    List<LineSpecification> lineSpecifications;

    List<RawMaterials> availableValues;

}
