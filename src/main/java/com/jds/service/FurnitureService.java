package com.jds.service;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.dao.entity.ImageEntity;
import com.jds.dao.repository.ColorRepository;
import com.jds.dao.repository.FurnitureRepository;
import com.jds.dao.entity.DoorFurniture;
import com.jds.dao.entity.LimitationDoor;
import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.enumModels.TypeOfFurniture;
import com.jds.model.image.ColorPicture;
import com.jds.model.image.TypeImageDirectory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ColorService colorService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private DeleteCheckService deleteCheckService;
    @Autowired
    private GlassPositionService glassPositionService;

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
        return Arrays.asList(TypeOfFurniture.values());
    }

    public String deleteFurniture(@NonNull String id) {

        DoorFurniture furniture = repository.getFurnitureById(Integer.parseInt(id));

        if (deleteCheckService.checkFurniture(furniture)) {
            return null;
        } else {
            return repository.deleteFurniture(furniture);
        }
    }

    public DoorFurniture saveFurniture(@NonNull DoorFurniture furniture) {


        return repository.saveFurniture(furniture);
    }

    public AvailableFieldsForSelection getAvailableFields(String doorTypeId) {

        RestrictionOfSelectionFields template = templateService.getTemplate(String.valueOf(doorTypeId));

        return AvailableFieldsForSelection.builder()

                .topLock(getFurnitureByLmit(template.getTopLock()))
                .lowerLock(getFurnitureByLmit(template.getLowerLock()))

                .lockCylinder(getFurnitureByLmit(template.getLockCylinder()))

                .topInLockDecor(getFurnitureByLmit(template.getTopInLockDecor()))
                .topOutLockDecor(getFurnitureByLmit(template.getTopOutLockDecor()))
                .lowerInLockDecor(getFurnitureByLmit(template.getLowerInLockDecor()))
                .lowerOutLockDecor(getFurnitureByLmit(template.getLowerOutLockDecor()))

                .handle(getFurnitureByLmit(template.getHandle()))

                .outShieldColor(getImageByLimit(template.getOutShieldColor()))
                .outShieldDesign(getImageByLimit(template.getOutShieldDesign()))

                .shieldColor(getImageByLimit(template.getShieldColor()))
                .shieldDesign(getImageByLimit(template.getShieldDesign()))
                .shieldGlass(getImageByLimit(template.getShieldGlass()))
                .glassPosition(getGlassPosition(template.getGlassPositions()))

                .typeDoorGlass(getFurnitureByLmit(template.getTypeDoorGlass()))
                .toning(getFurnitureByLmit(template.getToning()))
                .armor(getFurnitureByLmit(template.getArmor()))


                .closer(getFurnitureByLmit(template.getCloser()))
                .peephole(getFurnitureByLmit(template.getPeephole()))
                .peepholePosition(convertToFurniture(template.getPeepholePosition()))

                .nightLock(getFurnitureByLmit(template.getNightLock()))

                .build();
    }

    public List<DoorFurniture> getFurnitureByLmit(List<LimitationDoor> limit) {
        return limit.stream()
                .map(lim -> repository.getFurnitureById(lim.getItemId()))
                .peek(furniture -> furniture.clearNonSerializingFields())
                .collect(Collectors.toList());
    }

    public List<ImageEntity> getImageByLimit(List<LimitationDoor> limit) {
        return limit.stream()
                .map(lim -> colorRepository.getColorById(lim.getItemId()))
                .peek(image -> image.clearNonSerializingFields())
                .collect(Collectors.toList());
    }

    public List<GlassPositionEntity> getGlassPosition(List<LimitationDoor> limit) {
        return limit.stream()
                .map(lim -> glassPositionService.getById(lim.getItemId()))
                .collect(Collectors.toList());
    }

    public List<DoorFurniture> convertToFurniture(List<LimitationDoor> limit) {
        return limit.stream()
                .map(DoorFurniture::newInstance)
                .peek(image -> image.clearNonSerializingFields())
                .collect(Collectors.toList());
    }


    public List<ColorPicture> getPicByType(TypeOfFurniture type) {
        return colorService.getImageFileList(TypeImageDirectory.PREVIEW, type);
    }

    public List<ColorPicture> getSketchByType(TypeOfFurniture type) {
        return colorService.getImageFileList(TypeImageDirectory.SKETCH, type);
    }
}
