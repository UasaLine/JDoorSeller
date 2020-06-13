package com.jds.service;

import com.jds.dao.entity.ImageEntity;
import com.jds.dao.repository.ColorRepository;
import com.jds.dao.repository.FurnitureRepository;
import com.jds.dao.entity.DoorFurniture;
import com.jds.dao.entity.LimitationDoor;
import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.modelEnum.TypeOfFurniture;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository repository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private TemplateService templateService;

    public List<DoorFurniture> getFurnitureList() {
        return repository.getFurniture();
    }

    public DoorFurniture getDoorFurniture(@NonNull String id) {
        if ("0".equals(id)) {
            return new DoorFurniture();
        }

        DoorFurniture furniture = repository.getFurnitureById(Integer.parseInt(id));
        return furniture.clearNonSerializingFields();
    }

    public List<TypeOfFurniture> getTypesFurniture() {
        return new ArrayList<TypeOfFurniture>(Arrays.asList(TypeOfFurniture.values()));
    }

    public String deleteFurniture(@NonNull String id) {
        DoorFurniture furniture = repository.getFurnitureById(Integer.parseInt(id));
        return repository.deleteFurniture(furniture);
    }

    public String saveFurniture(@NonNull DoorFurniture furniture) {

        return repository.saveFurniture(furniture);
    }

    public AvailableFieldsForSelection getAvailableFields(String doorTypeId) {
        RestrictionOfSelectionFields template = templateService.getTemplateFromLimits(String.valueOf(doorTypeId));
        return AvailableFieldsForSelection.builder()
                .topLock(convertToFurniture(template.getTopLock()))
                .lowerLock(convertToFurniture(template.getLowerLock()))

                .lockCylinder(convertToFurniture(template.getLockCylinder()))

                .topInLockDecor(convertToFurniture(template.getTopInLockDecor()))
                .topOutLockDecor(convertToFurniture(template.getTopOutLockDecor()))
                .lowerInLockDecor(convertToFurniture(template.getLowerInLockDecor()))
                .lowerOutLockDecor(convertToFurniture(template.getLowerInLockDecor()))

                .handle(convertToFurniture(template.getHandle()))

                .shieldColor(convertToImage(template.getShieldColor()))
                .shieldDesign(convertToImage(template.getShieldDesign()))
                .build();


    }

    public List<DoorFurniture> convertToFurniture(List<LimitationDoor> topLock) {
        return topLock.stream()
                .map(lim -> repository.getFurnitureById(lim.getItemId()))
                .peek(furniture -> furniture.clearNonSerializingFields())
                .collect(Collectors.toList());
    }

    public List<ImageEntity> convertToImage(List<LimitationDoor> topLock) {
        return topLock.stream()
                .map(lim -> colorRepository.getColorById(lim.getItemId()))
                .peek(image -> image.clearNonSerializingFields())
                .collect(Collectors.toList());
    }
}
