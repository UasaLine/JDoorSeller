package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.MainDAO;
import com.jds.dao.repository.MaterialsRepository;
import com.jds.model.Specification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private MainDAO mainDAO;
    @Autowired
    private MaterialsRepository repository;

    public SpecificationSetting saveSpecificationSetting(SpecificationSetting setting) {
        return repository.saveSpecificationSetting(setting);
    }

    public Specification getSpecification(@NonNull String typeId) {
        DoorType doorType = mainDAO.getDoorType(Integer.parseInt(typeId)).clearNonSerializingFields();
        return Specification.builder()
                .doorType(doorType)
                .availableValues(repository.getRawMaterials())
                .lineSpecifications(getLineSpecification(doorType.getId()))
                .build();
    }

    public List<LineSpecification> getLineSpecification(@NonNull int DoorTypeId) {

        List<LineSpecification> lineSpecificationList = repository.getLineSpecificationList();
        return lineSpecificationList;

    }

    public List<SpecificationEntity> getLineSpecification() {

        List<SpecificationEntity> specificationList = repository.getSpecification();
        //specificationList.stream().forEach((lin) -> lin.getDoorType().clearNonSerializingFields());
        return specificationList;

    }

    public List<LineSpecification> saveSpecification(@NonNull Specification specification) {

        List<LineSpecification> lineSpecInBase = repository.getLineSpecificationList();

        List<LineSpecification> lineSpecifications = specification.getLineSpecifications();
        lineSpecifications.stream()
                .peek((lineSpec) -> setIdLineSpecification(lineSpec, lineSpecInBase, specification.getAvailableValues()))
                .forEach((lineSpec) -> repository.saveLineSpecification(lineSpec));

        lineSpecInBase.stream()
                .forEach((lineSpec) -> repository.deleteLineSpecification(lineSpec));

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

    public SpecificationEntity saveSpecificationEntity(@NonNull SpecificationEntity spec) {

        return repository.saveSpecificationEntity(spec);
    }

    public SpecificationEntity getSpecificationEntity(String id) {
        if ("0".equals(id)) {
            return new SpecificationEntity();
        }

        return getSpecificationEntity(Integer.parseInt(id));
    }

    public SpecificationEntity getSpecificationEntity(int id) {

        SpecificationEntity spec = repository.getSpecificationEntityById(id);
        spec.clearNonSerializingFields();

        return spec;
    }

    public String deleteSpecificationEntity(@NonNull String id) {
        SpecificationEntity specificationEntity = getSpecificationEntity(id);

        return repository.deleteSpecificationEntity(specificationEntity);
    }

    public LineSpecification saveSpecificationLine(@NonNull int specificationId, @NonNull LineSpecification lineSpecification) {

        SpecificationEntity specificationEntity = getSpecificationEntity(specificationId);
        specificationEntity.putLine(lineSpecification);
        specificationEntity.setSpecificationToAllLine(new SpecificationEntity(specificationId));

        repository.saveSpecificationEntity(specificationEntity);
        return lineSpecification;
    }

    public LineSpecification getLineSpecification(@NonNull String line_id) {

        LineSpecification lineSpecification;

        if (line_id.equals("0")) {
            lineSpecification = new LineSpecification();
        } else {
            lineSpecification = repository.getSpecificationLineById(Integer.parseInt(line_id));
            lineSpecification.setSpecification(null);
        }

        return lineSpecification;
    }

    public String deleteLineSpecification(@NonNull String lineId) {

        LineSpecification lineSpecification = getLineSpecification(lineId);
        repository.deleteLineSpecification(lineSpecification);
        return "ок";
    }


}
