package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.MainDAO;
import com.jds.model.Specification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialsService {

    @Autowired
    private MainDAO dAO;

    public List<RawMaterials> getAllMaterials() {
        return dAO.getRawMaterials();
    }

    public List<MaterialFormula> getMaterialFormulas() {
        return dAO.getMaterialFormula();
    }

    public SpecificationSetting saveSpecificationSetting(SpecificationSetting setting) {
        return dAO.saveSpecificationSetting(setting);
    }

    public Specification getSpecification(@NonNull String typeId) {
        DoorType doorType = dAO.getDoorType(Integer.parseInt(typeId)).clearNonSerializingFields();
        return Specification.builder()
                .doorType(doorType)
                .availableValues(dAO.getRawMaterials())
                .lineSpecifications(getLineSpecification(doorType.getId()))
                .build();
    }

    public List<LineSpecification> getLineSpecification(@NonNull int DoorTypeId) {

        List<LineSpecification> lineSpecificationList = dAO.getLineSpecification(DoorTypeId);
        lineSpecificationList.stream().forEach((lin) -> lin.getDoorType().clearNonSerializingFields());
        return lineSpecificationList;

    }

    public List<LineSpecification> saveSpecification(@NonNull Specification specification) {

        List<LineSpecification> lineSpecInBase = dAO.getLineSpecification(specification.getDoorType().getId());

        List<LineSpecification> lineSpecifications = specification.getLineSpecifications();
        lineSpecifications.stream()
                .peek((lineSpec) -> setIdLineSpecification(lineSpec, lineSpecInBase, specification.getAvailableValues()))
                .forEach((lineSpec) -> dAO.saveLineSpecification(lineSpec));

        lineSpecInBase.stream()
                .forEach((lineSpec) -> dAO.deleteLineSpecification(lineSpec));

        return lineSpecifications;
    }

    public LineSpecification setIdLineSpecification(@NonNull LineSpecification lineSpecification,
                                                    @NonNull List<LineSpecification> oldLimitList,
                                                    @NonNull List<RawMaterials> materials) {

        int newId = 0;
        if (oldLimitList.size() > 0) {
            newId = oldLimitList.get(0).getId();
            oldLimitList.remove(0);
        }

        for (RawMaterials material : materials) {
            if (material.getName().equals(lineSpecification.getName())) {
                lineSpecification.setMaterialId(material.getIdManufacturerProgram());
            }
        }

        lineSpecification.setId(newId);

        return lineSpecification;
    }
}
