package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
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

    public void saveOrUpdateDoorClass(String classId, String name, String description,
                                      boolean fireproofcheckbox, boolean hotcheckbox) {

        dAO.saveOrUpdateDoorClassById(new DoorClass(Integer.parseInt(classId)
                , name
                , description
                , dooltranslateIntoInt(fireproofcheckbox)
                , dooltranslateIntoInt(hotcheckbox)));

    }

    public List<DoorClass> getDoorClass() {

        List<DoorClass> doorClassList = dAO.getDoorClass();

        return doorClassList.stream()
                .peek((dClass) -> dClass.getDoorTypes().stream().
                        forEach((type) -> type.clearNonSerializingFields()))
                .collect(Collectors.toList());
    }

    public DoorClass getDoorClass(String calassId) {
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

    public List<Metal> getMetals() {
        return dAO.getMetals();
    }

    public List<DoorPart> getDoorPart(DoorEntity door) {

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType().getId()), door);

    }

    public DoorEntity calculateTheDoor(DoorEntity door) {


        PayrollSettings paySettings = new PayrollSettings();
        paySettings.setBendSetting(dAO.getbendSettingId(door.getDoorType().getId(), door.getMetal(), door.getSealingLine()));
        paySettings.setConstMap(dAO.getSalaryConstantsMap());
        paySettings.setDoorColors(dAO.getDoorColor(door.getDoorColor()));
        paySettings.setDoorType(dAO.getDoorType(door.getDoorType().getId()));
        paySettings.setSalarySetting(dAO.getSalarySetting(door.getMetal()));

        List<SpecificationSetting> speciSettingList = dAO.getSpecificationSetting(door.getMetal(), door.getDoorType().getId());

        //new instance cost
        door.setCostList(new CostList());

        door = calculateMetalDoor(door)
                .calculateColorDoor(paySettings.getDoorColors())
                .calculateSalary(paySettings)
                .calculateFurniture()
                .calculateGlass()
                .calculateMaterials(speciSettingList)
                .costToPrice()
                .createName();

        //int price = getRandomPrice(8500,25000);

        return door;
    }

    public DoorColors saveDoorColors(DoorColors doorColors) {
        doorColors.setPicturePath("images/Door/AColor1/" + doorColors.getPicturePath() + ".jpg");
        return dAO.saveDoorColors(doorColors);
    }

    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(String idDoorType) {

        int idType = Integer.parseInt(idDoorType);

        return new RestrictionOfSelectionFields(idType)
                .widthDoor(generateSizeDoor(TypeOfLimitionDoor.HEIGHT))
                .heightDoor(generateSizeDoor(TypeOfLimitionDoor.WIDTH))

                .stuffColors(dAO.getDoorColors())

                .addTopLock(dAO.getFurnitureByType(TypeOfFurniture.TOP_LOCK, idType))
                .addLowerLock(dAO.getFurnitureByType(TypeOfFurniture.LOWER_LOCK, idType))
                .addHendle(dAO.getFurnitureByType(TypeOfFurniture.HANDLE, idType))
                .addLockCylinder(dAO.getFurnitureByType(TypeOfFurniture.LOCK_CYLINDER, idType))
                .addCloser(dAO.getFurniture(TypeOfFurniture.CLOSER))
                .addEndDoorLock(dAO.getFurniture(TypeOfFurniture.END_DOOR_LOCK))

                .addGlass(dAO.getFurnitureByType(TypeOfFurniture.TYPE_GLASS, idType))
                .addToning(dAO.getFurniture(TypeOfFurniture.GLASS_PELLICLE))
                .addArmor(dAO.getFurniture(TypeOfFurniture.ARMOR_GLASS_PELLICLE))

                .addTopInLockDecor(dAO.getFurniture(TypeOfFurniture.TOP_IN_LOCK_DECOR))
                .addTopOutLockDecor(dAO.getFurniture(TypeOfFurniture.TOP_OUT_LOCK_DECOR))
                .addLowerInLockDecor(dAO.getFurniture(TypeOfFurniture.LOWER_IN_LOCK_DECOR))
                .addLowerOutLockDecor(dAO.getFurniture(TypeOfFurniture.LOWER_OUT_LOCK_DECOR));
    }

    public RestrictionOfSelectionFields getTemplateFromLimits(String doorTypeId) {

        List<LimitationDoor> limitList = dAO.getLimitationDoor(Integer.parseInt(doorTypeId));
        RestrictionOfSelectionFields restriction = new RestrictionOfSelectionFields();
        restriction.setDoorTypeid(Integer.parseInt(doorTypeId));

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

        } else if (TypeOfLimitionDoor.COLOR_DOOR == lim.getTypeSettings()) {
            restriction.addColors(dAO.getDoorColor(lim.getFirstItem()));

        } else if (TypeOfLimitionDoor.TOP_LOCK == lim.getTypeSettings()) {
            restriction.addTopLock(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.LOWER_LOCK == lim.getTypeSettings()) {
            restriction.addLowerLock(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.HANDLE == lim.getTypeSettings()) {
            restriction.addHandle(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.LOCK_CYLINDER == lim.getTypeSettings()) {
            restriction.addLockCylinder(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.TOP_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopInLockDecor(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addTopOutLockDecor(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerInLockDecor(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR == lim.getTypeSettings()) {
            restriction.addLowerOutLockDecor(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.CLOSER == lim.getTypeSettings()) {
            restriction.addCloser(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.END_DOOR_LOCK == lim.getTypeSettings()) {
            restriction.addEndDoorLock(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.TYPE_GLASS == lim.getTypeSettings()) {
            restriction.addTypeDoorGlass(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.TONING == lim.getTypeSettings()) {
            restriction.addToning(dAO.getDoorFurnitureId(lim.getItemId()));
        } else if (TypeOfLimitionDoor.ARMOR == lim.getTypeSettings()) {
            restriction.addArmor(dAO.getDoorFurnitureId(lim.getItemId()));
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

    private static int getRandomPrice(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private DoorEntity calculateMetalDoor(DoorEntity door) {

        List<DoorPart> partList = getDoorPart(door);
        Sheet sheet = new Sheet(2500, 1250);

        SheetCutting sheetCutting = new SheetCutting(partList, sheet);
        sheetCutting.CompleteCutting();
        sheetCutting.clearHardCalculationData();

        Metal metal = dAO.getMetal(door.getMetal());

        door.setSheets(sheetCutting.getSheets());
        door.calculateWeigh(metal);
        door.calculateCostMetal(metal);

        return door;
    }

    public BendSetting saveBendSetting(BendSetting bendSetting) {
        return dAO.saveBendSetting(bendSetting);
    }

    public DoorEntity saveDoor(DoorEntity door) {

        return dAO.saveDoor(door.clearEmptyLinks());

    }

    public DoorsОrder saveOrder(DoorsОrder order) {
        return dAO.saveOrder(order.calculateTotal());
    }

    public List<DoorsОrder> getOrders() {
        List<DoorsОrder> orders = dAO.getOrders();
        for (DoorsОrder order : orders) {
            clearNonSerializingFields(order);
        }
        return orders;
    }

    public DoorsОrder getOrder(String id) {

        int intId = Integer.parseInt(id);

        if (intId == 0) {
            return new DoorsОrder();
        }
        return clearNonSerializingFields(dAO.getOrder(intId));
    }

    public DoorsОrder clearNonSerializingFields(DoorsОrder order) {

        List<DoorEntity> doors = order.getDoors();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
        }
        return order;
    }

    public DoorEntity getDoor(String id, String orderId, String doorGroup) {

        DoorEntity door = null;
        if (id != null && !id.isEmpty() && !id.equals("0")) {
            door = dAO.getDoor(Integer.parseInt(id));
            if (door.getFurnitureKit() == null) {
                door.setFurnitureKit(new FurnitureKit());
            }
            if (door.getDoorGlass() == null) {
                door.setDoorGlass(new DoorGlass());
            }
        }
        if (door == null) {
            door = new DoorEntity();
        }

        List<DoorClass> doorClassList = dAO.getAvailableDoorClass();
        for (DoorClass doorClass : doorClassList) {
                door.addAvailableDoorClass(doorClass.clearNonSerializingFields());
        }

        if (orderId != null && !orderId.isEmpty() && !orderId.equals("0") && (door.getId() == 0)) {
            DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
            order.addDoor(door);
            dAO.saveOrder(order);
        }

        door.clearNonSerializingFields();

        return door;

    }

    public DoorsОrder deleteDoorFromOrder(String id, String orderId) {

        if (orderId != null && !orderId.isEmpty() && !orderId.equals("0") && id != null && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                dAO.saveOrder(order);
                return clearNonSerializingFields(order);
            }

        }

        return null;
    }

    public String deleteOrder(String orderId) {
        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        return String.valueOf(order.getId());
    }

    public int saveOrUpdateDoorType(@NonNull String typeId, @NonNull String classId, String name,
                                    String namePicture, int doorLeaf,
                                    String nameForPrint, String nameForPrintInternalOpening,
                                    int daysToRelease, int markUp, int markUpGlassPackage) {

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

        return saveOrUpdateDoorType(doorType);
    }


    public void saveDoorTemplate(RestrictionOfSelectionFields restriction) {

        DoorType doorType = dAO.getDoorType(restriction.getDoorTypeid());

        List<LimitationDoor> limitList = dAO.getLimitationDoor(doorType.getId());

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.METAL_THICKNESS, restriction.getMetal(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH, restriction.getWidthDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT, restriction.getHeightDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DEPTH, restriction.getDeepnessDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEAF_THICKNESS, restriction.getThicknessDoorLeaf(), limitList);

        saveAsLimitationDoorForColors(doorType, TypeOfLimitionDoor.COLOR_DOOR, restriction.getColors(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DOORSTEP, restriction.getDoorstep(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP, restriction.getStainlessSteelDoorstep(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.FIRST_SEALING_LINE, restriction.getFirstSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SECOND_SEALING_LINE, restriction.getSecondSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.THIRD_SEALING_LINE, restriction.getThirdSealingLine(), limitList);

        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.TOP_LOCK, restriction.getTopLock(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.LOWER_LOCK, restriction.getLowerLock(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.HANDLE, restriction.getHandle(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.LOCK_CYLINDER, restriction.getLockCylinder(), limitList);

        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.TOP_IN_LOCK_DECOR, restriction.getTopInLockDecor(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR, restriction.getTopOutLockDecor(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR, restriction.getLowerInLockDecor(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR, restriction.getLowerOutLockDecor(), limitList);

        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.CLOSER, restriction.getCloser(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.END_DOOR_LOCK, restriction.getEndDoorLock(), limitList);

        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.TYPE_GLASS, restriction.getTypeDoorGlass(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.TONING, restriction.getToning(), limitList);
        saveAsLimitationDoorForFurniture(doorType, TypeOfLimitionDoor.ARMOR, restriction.getArmor(), limitList);

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
                                              List<DoorColors> colorList, List<LimitationDoor> oldLimitList) {

        colorList.stream().forEach((color) -> dAO.saveOrUpdateLimitationDoor(LimitationDoor.getNewLimit(color, doorType, type, oldLimitList)));

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

    public List<PrintAppToTheOrder> getPrintAppList(String orderId) {

        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        List<DoorEntity> doors = order.getDoors();

        List<PrintAppToTheOrder> printAppList = new ArrayList<>();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
            printAppList.add(new PrintAppToTheOrder(door, order));
        }

        return printAppList;

    }

    public static int dooltranslateIntoInt(boolean value) {
        if (value == true) {
            return 1;
        } else {
            return 0;
        }
    }
}
