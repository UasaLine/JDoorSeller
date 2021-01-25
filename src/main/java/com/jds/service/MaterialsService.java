package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.MainDAO;
import com.jds.dao.repository.MaterialsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialsService {

    @Autowired
    private MaterialsRepository repository;

    public List<RawMaterials> getRawMaterials() {
        return repository.getRawMaterials();
    }

    public List<MaterialFormula> getMaterialFormulas() {
        return repository.getMaterialFormula();
    }

    public MaterialEntity saveMaterialsEntity(MaterialEntity materialEntity) {

        MaterialEntity materialBase = repository.getMaterialsByManufactureId(materialEntity.getManufacturerId());
        int componentId = 0;

        if (materialBase != null) {
            materialEntity.setId(materialBase.getId());
            componentId = materialBase.getComponents().getId();
            materialEntity.getComponents().setId(componentId);
        } else {
            materialBase = repository.saveMaterialsEntity(materialEntity);
        }
        materialEntity = checkInBaseExist(materialEntity);
        materialEntity = repository.saveMaterialsEntity(materialEntity);

        return materialEntity;
    }

    public MaterialEntity checkInBaseExist(MaterialEntity materialEntity) {

        List<MaterialEntity> materialComponentsList = materialEntity.getComponents().getMaterialList();
        for (int i = 0; i < materialComponentsList.size(); i++) {
            MaterialEntity material = materialComponentsList.get(i);
            MaterialEntity materialFromBase = repository.getMaterialsByManufactureId(material.getManufacturerId());
            if (materialFromBase != null) {
                material.setId(materialFromBase.getId());
            } else {
                material = repository.saveMaterialsEntity(material);
            }
        }
        return materialEntity;
    }

    public MaterialEntity getMaterial(@NonNull int id) {
        return repository.getMaterial(id);
    }

    public List<MaterialEntity> getMaterials() {
        return repository.getMaterials();
    }
}
