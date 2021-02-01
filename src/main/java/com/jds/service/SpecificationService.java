package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.MainDAO;
import com.jds.dao.repository.MaterialsRepository;
import com.jds.dao.repository.SpecificationRepository;
import com.jds.model.Specification;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository repository;

    private Logger logger = LoggerFactory.getLogger(SpecificationService.class);

    public List<LineSpecification> getLineSpecification(@NonNull int DoorTypeId) {

        List<LineSpecification> lineSpecificationList = repository.getLineSpecificationList();
        return lineSpecificationList;

    }

    public List<SpecificationEntity> getLineSpecification() {

        List<SpecificationEntity> specificationList = repository.getSpecification();
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

    public SpecificationEntity save(@NonNull SpecificationEntity spec) {

        SpecificationEntity baseSpec = repository.getSpecificationByManufacturerId(spec.getManufacturerId());

        if (baseSpec != null) {
            spec.fullIdBySpecification(baseSpec);
        }

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

    public void createSpecification(DoorEntity doorEntity)  {

        int typeId = doorEntity.getDoorType().getId();

        SpecificationEntity specificationTemplate = repository.getSpecificationEntityByDoorType(typeId);

        if (specificationTemplate != null){
            SpecificationEntity newSpec = specificationTemplate.cloneBySpecification();
            newSpec.setDoorId(doorEntity.getId());
            newSpec.setManufacturerId(String.valueOf(doorEntity.getId()));
            newSpec.setName(doorEntity.getName());

            repository.saveSpecificationEntity(newSpec);
        } else {

            logger.error("door specification not found");
        }
    }

    public SpecificationEntity getSpecificationByDoorId(int id) {
        return repository.getSpecificationByDoorId(id);
    }
}
