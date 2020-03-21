package com.jds.service;

import com.jds.dao.ColorRepository;
import com.jds.dao.MainDAO;
import com.jds.dao.MetalRepository;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.model.modelEnum.TypeOfLimitionDoor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MaineService {

    @Autowired
    private MainDAO dAO;

    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;

    public void saveOrUpdateDoorClass(String classId, String name, String description,
                                      boolean fireproofcheckbox, boolean hotcheckbox, String namePicture) {

        dAO.saveOrUpdateDoorClassById(new DoorClass(Integer.parseInt(classId)
                , name
                , description
                , dooltranslateIntoInt(fireproofcheckbox)
                , dooltranslateIntoInt(hotcheckbox)
                , namePicture));

    }

    public List<DoorClass> getDoorClass() {

        List<DoorClass> doorClassList = dAO.getDoorClass();

        return doorClassList.stream()
                .peek((dClass) -> dClass.getDoorTypes().stream().
                        forEach((type) -> type.clearNonSerializingFields()))
                .collect(Collectors.toList());
    }

    public DoorClass getDoorClass(@NonNull String calassId) {
        return dAO.getDoorClass(Integer.parseInt(calassId));
    }

    public int deleteDoorClass(String calassId) {
        dAO.deleteDoorClass(dAO.getDoorClass(Integer.parseInt(calassId)));
        return 1;
    }

    public List<FireproofDoor> getlistDoor() {
        return dAO.getlistDoor();
    }

    public List<DoorType> getDoorType() {
        return dAO.getDoorType();
    }

    public DoorType getDoorType(String typeId) {
        if (typeId == null) {
            return new DoorType();
        }
        return getDoorType(Integer.parseInt(typeId));
    }

    public DoorType getDoorType(@NonNull int typeId) {

        return dAO.getDoorType(typeId);
    }

    public List<DoorType> getTypeFromListById(List<DoorClass> DoorClassList, String classId) throws NotBoundException {

        if (classId == null) {
            classId = "1";
        }
        if (DoorClassList == null && DoorClassList.size() == 0) {
            throw new NotBoundException("List DoorClassList empty!");
        }
        int intId = Integer.parseInt(classId);

        Optional<DoorClass> doorClass = DoorClassList.stream()
                .filter((c) -> c.getId() == intId)
                .findFirst();

        if (doorClass.isPresent()) {
            return doorClass.get().getDoorTypes();
        } else {
            return new ArrayList<>();
        }
    }

    public List<MaterialFormula> getMaterialFormulas() {
        return dAO.getMaterialFormula();
    }

    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(String idDoorType) {

        int idType = Integer.parseInt(idDoorType);

        return new RestrictionOfSelectionFields(idType)

                .widthDoor(generateSizeDoor(TypeOfLimitionDoor.HEIGHT))
                .heightDoor(generateSizeDoor(TypeOfLimitionDoor.WIDTH))

                .stuffMetal(metalDao.getMetals())

                .stuffColors(colorDao.getColors())

                .addTopLock(dAO.getFurniture(TypeOfFurniture.TOP_LOCK))
                .addLowerLock(dAO.getFurniture(TypeOfFurniture.LOWER_LOCK))
                .addHendle(dAO.getFurniture(TypeOfFurniture.HANDLE))
                .addLockCylinder(dAO.getFurniture(TypeOfFurniture.LOCK_CYLINDER))
                .addCloser(dAO.getFurniture(TypeOfFurniture.CLOSER))
                .addEndDoorLock(dAO.getFurniture(TypeOfFurniture.END_DOOR_LOCK))

                .addGlass(dAO.getFurniture(TypeOfFurniture.TYPE_GLASS))
                .addToning(dAO.getFurniture(TypeOfFurniture.GLASS_PELLICLE))
                .addArmor(dAO.getFurniture(TypeOfFurniture.ARMOR_GLASS_PELLICLE))

                .addTopInLockDecor(dAO.getFurniture(TypeOfFurniture.TOP_IN_LOCK_DECOR))
                .addTopOutLockDecor(dAO.getFurniture(TypeOfFurniture.TOP_OUT_LOCK_DECOR))
                .addLowerInLockDecor(dAO.getFurniture(TypeOfFurniture.LOWER_IN_LOCK_DECOR))
                .addLowerOutLockDecor(dAO.getFurniture(TypeOfFurniture.LOWER_OUT_LOCK_DECOR));
    }

    public RestrictionOfSelectionFields getTemplateFromLimits(@NonNull String doorTypeId) {

        int intDoorTypeId = Integer.parseInt(doorTypeId);

        List<LimitationDoor> limitList = dAO.getLimitationDoor(intDoorTypeId);
        RestrictionOfSelectionFields restriction = new RestrictionOfSelectionFields();
        restriction.setDoorTypeid(intDoorTypeId);

        limitList.stream().forEach((lim) -> RestrictionBuild(restriction, lim));

        return restriction;
    }

    public void RestrictionBuild(RestrictionOfSelectionFields restriction, @NonNull LimitationDoor lim) {

        if (TypeOfLimitionDoor.METAL_THICKNESS == lim.getTypeSettings()) {
            restriction.addMetal(lim);
        } else if (TypeOfLimitionDoor.WIDTH == lim.getTypeSettings()) {
            restriction.addWidthDoor(lim);
        } else if (TypeOfLimitionDoor.HEIGHT == lim.getTypeSettings()) {
            restriction.addHeightDoor(lim);
        } else if (TypeOfLimitionDoor.DEPTH == lim.getTypeSettings()) {
            restriction.addDeepnessDoor(lim);
        } else if (TypeOfLimitionDoor.LEAF_THICKNESS == lim.getTypeSettings()) {
            restriction.addThicknessDoorLeaf(lim);

        } else if (TypeOfLimitionDoor.DOORSTEP == lim.getTypeSettings()) {
            restriction.addDoorstep(lim);
        } else if (TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP == lim.getTypeSettings()) {
            restriction.addStainlessSteelDoorstep(lim);

        } else if (TypeOfLimitionDoor.FIRST_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addFirstSealingLine(lim);
        } else if (TypeOfLimitionDoor.SECOND_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addSecondSealingLine(lim);
        } else if (TypeOfLimitionDoor.THIRD_SEALING_LINE == lim.getTypeSettings()) {
            restriction.addThirdSealingLine(lim);

        } else if (TypeOfLimitionDoor.TOP_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addTopDoorTrim(lim);
        } else if (TypeOfLimitionDoor.LEFT_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addLeftDoorTrim(lim);
        } else if (TypeOfLimitionDoor.RIGHT_DOOR_TRIM == lim.getTypeSettings()) {
            restriction.addRightDoorTrim(lim);

        } else if (TypeOfLimitionDoor.COLOR_DOOR == lim.getTypeSettings()) {
            restriction.addColors(lim);

        } else if (TypeOfLimitionDoor.TOP_LOCK == lim.getTypeSettings()) {
            restriction.addTopLock(lim);
        } else if (TypeOfLimitionDoor.LOWER_LOCK == lim.getTypeSettings()) {
            restriction.addLowerLock(lim);
        } else if (TypeOfLimitionDoor.HANDLE == lim.getTypeSettings()) {
            restriction.addHandle(lim);
        } else if (TypeOfLimitionDoor.LOCK_CYLINDER == lim.getTypeSettings()) {
            restriction.addLockCylinder(lim);
        } else if (TypeOfLimitionDoor.TOP_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopInLockDecor(lim);
        } else if (TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopOutLockDecor(lim);
        } else if (TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerInLockDecor(lim);
        } else if (TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerOutLockDecor(lim);
        } else if (TypeOfLimitionDoor.CLOSER == lim.getTypeSettings()) {
            restriction.addCloser(lim);
        } else if (TypeOfLimitionDoor.END_DOOR_LOCK == lim.getTypeSettings()) {
            restriction.addEndDoorLock(lim);
        } else if (TypeOfLimitionDoor.TYPE_GLASS == lim.getTypeSettings()) {
            restriction.addTypeDoorGlass(lim);
        } else if (TypeOfLimitionDoor.TONING == lim.getTypeSettings()) {
            restriction.addToning(lim);
        } else if (TypeOfLimitionDoor.ARMOR == lim.getTypeSettings()) {
            restriction.addArmor(lim);
        }
    }


    public List<LimitationDoor> generateSizeDoor(TypeOfLimitionDoor typeSettings) {
        List<LimitationDoor> size = new ArrayList<>();

        LimitationDoor limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(980);
        limit.setPairOfValues(0);
        size.add(limit);

        limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(860);
        limit.setPairOfValues(0);
        size.add(limit);

        limit = new LimitationDoor();
        limit.setTypeSettings(typeSettings);
        limit.setStartRestriction(500);
        limit.setStopRestriction(1050);
        limit.setPairOfValues(1);
        size.add(limit);

        return size;
    }

    public DoorTemplate getDoorTemplate(String doorTypeId) {

        return DoorTemplate.builder()
                .template(getTemplateFromLimits(doorTypeId))
                .restriction(getRestrictionOfSelectionFields(doorTypeId))
                .build();
    }

    public BendSetting saveBendSetting(BendSetting bendSetting) {
        return dAO.saveBendSetting(bendSetting);
    }

    public int saveOrUpdateDoorType(@NonNull String typeId, @NonNull String classId, String name,
                                    String namePicture, int doorLeaf,
                                    String nameForPrint, String nameForPrintInternalOpening,
                                    int daysToRelease, int markUp, int markUpGlassPackage,
                                    boolean priceList, double retailPrice,
                                    double wholesalePriceFromStock1, double wholesalePriceFromStock2,
                                    double wholesalePriceFromOrder) {

        int intTypeid = Integer.parseInt(typeId);
        int intClassId = Integer.parseInt(classId);
        DoorType doorType = new DoorType();

        if (intTypeid == 0 && intClassId > 0) {
            DoorClass doorClass = dAO.getDoorClass(intClassId);
            doorType.setDoorClass(doorClass);
        }
        if (intTypeid > 0) {
            doorType = dAO.getDoorType(intTypeid);
        }
        doorType.setName(name);
        doorType.setNamePicture(namePicture);
        doorType.setDoorLeaf(doorLeaf);
        doorType.setNameForPrint(nameForPrint);
        doorType.setNameForPrintInternalOpening(nameForPrintInternalOpening);
        doorType.setDaysToRelease(daysToRelease);
        doorType.setMarkUp(markUp);
        doorType.setMarkUpGlassPackage(markUpGlassPackage);
        doorType.setPriceList(booleanToInt(priceList));
        doorType.setRetailPrice(retailPrice);
        doorType.setWholesalePriceFromStock1(wholesalePriceFromStock1);
        doorType.setWholesalePriceFromStock2(wholesalePriceFromStock2);
        doorType.setWholesalePriceFromOrder(wholesalePriceFromOrder);

        return saveOrUpdateDoorType(doorType);
    }

    public int booleanToInt(boolean val) {

        if (val) {
            return 1;
        }
        return 0;
    }

    public void saveDoorTemplate(RestrictionOfSelectionFields restriction) {

        DoorType doorType = dAO.getDoorType(restriction.getDoorTypeid());

        List<LimitationDoor> limitList = dAO.getLimitationDoor(doorType.getId());

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.METAL_THICKNESS, restriction.getMetal(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH, restriction.getWidthDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT, restriction.getHeightDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DEPTH, restriction.getDeepnessDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEAF_THICKNESS, restriction.getThicknessDoorLeaf(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.COLOR_DOOR, restriction.getColors(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DOORSTEP, restriction.getDoorstep(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP, restriction.getStainlessSteelDoorstep(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.FIRST_SEALING_LINE, restriction.getFirstSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SECOND_SEALING_LINE, restriction.getSecondSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.THIRD_SEALING_LINE, restriction.getThirdSealingLine(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_DOOR_TRIM, restriction.getTopDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEFT_DOOR_TRIM, restriction.getLeftDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.RIGHT_DOOR_TRIM, restriction.getRightDoorTrim(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_LOCK, restriction.getTopLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_LOCK, restriction.getLowerLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HANDLE, restriction.getHandle(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOCK_CYLINDER, restriction.getLockCylinder(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_IN_LOCK_DECOR, restriction.getTopInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR, restriction.getTopOutLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR, restriction.getLowerInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR, restriction.getLowerOutLockDecor(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.CLOSER, restriction.getCloser(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.END_DOOR_LOCK, restriction.getEndDoorLock(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TYPE_GLASS, restriction.getTypeDoorGlass(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TONING, restriction.getToning(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.ARMOR, restriction.getArmor(), limitList);

        deleteAllDoorTemplate(limitList);

    }

    public void deleteAllDoorTemplate(@NonNull List<LimitationDoor> limitList) {


        limitList.stream().forEach((limit) -> dAO.deleteLimit(limit));

    }

    public void saveAsLimitationDoor(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                     @NonNull List<LimitationDoor> newlimList, List<LimitationDoor> oldLimitList) {


        newlimList.stream().peek((lim) -> lim.setFildForSaveToDataBase(doorType, type, oldLimitList))
                .forEach((lim) -> dAO.saveOrUpdateLimitationDoor(lim));

    }

    public void saveAsLimitationDoorForColors(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                              List<LimitationDoor> colorList, List<LimitationDoor> oldLimitList) {

        colorList.stream().forEach((color) -> dAO.saveOrUpdateLimitationDoor(color));

    }

    public void saveAsLimitationDoorForFurniture(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                                 @NonNull List<DoorFurniture> furnitureList, List<LimitationDoor> oldLimitList) {

        furnitureList.stream().forEach((furniture) -> dAO.saveOrUpdateLimitationDoor(LimitationDoor.getNewLimit(furniture, doorType, type, oldLimitList)));

    }

    public int saveOrUpdateDoorType(DoorType doorType) {
        return dAO.saveOrUpdateDoorType(doorType);
    }

    public SalaryConstants saveSalaryConstants(SalaryConstants constants) {
        return dAO.saveSalaryConstants(constants);
    }

    public SalarySetting saveSalarySetting(SalarySetting setting) {
        return dAO.saveSalarySetting(setting);
    }

    public SpecificationSetting saveSpecificationSetting(SpecificationSetting setting) {
        return dAO.saveSpecificationSetting(setting);
    }

    public static int dooltranslateIntoInt(boolean value) {
        if (value == true) {
            return 1;
        } else {
            return 0;
        }
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

    public List<RawMaterials> getAllMaterials() {
        return dAO.getRawMaterials();
    }

    public List<ShortTemplate> getTemplateList() {

        List<DoorType> list = dAO.getDoorTypeListFromLimitTable();
        return list.stream()
                .map(type -> new ShortTemplate(type))
                .collect(Collectors.toList());
    }

    public String getClassId(@NonNull String typeId) {

        if (typeId == "0") {
            return "0";
        }

        DoorType type = dAO.getDoorType(Integer.parseInt(typeId));

        String response = "0";
        if (type.getId() > 0) {
            response = String.valueOf(type.getDoorClass().getId());
        }

        return response;
    }


}
