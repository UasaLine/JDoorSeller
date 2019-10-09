package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.modelEnum.TypeOfFurniture;
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

        //for test please restore
        //return dAO.getDoorClass();

        List<DoorClass> doorClassList = dAO.getDoorClass();

        for (DoorClass doorClassElem: doorClassList){
            List<DoorType> doorTypelist = doorClassElem.getDoorTypes();
            for (DoorType typeElem:doorTypelist ){
                typeElem.makeRightNamePictureDoorType();
                dAO.saveOrUpdateDoorType(typeElem);
            }
        }

        return doorClassList;
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

        return new RestrictionOfSelectionFields()
                .stuffColors(dAO.getDoorColors())
                .addTopLock(dAO.getFurnitureByType(TypeOfFurniture.TOP_LOCK, idType))
                .addLowerLock(dAO.getFurnitureByType(TypeOfFurniture.LOWER_LOCK, idType))
                .addHendle(dAO.getFurnitureByType(TypeOfFurniture.HANDLE, idType))
                .addLowerlockCylinder(dAO.getFurnitureByType(TypeOfFurniture.LOCK_CYLINDER, idType))
                .addCloser(dAO.getFurniture(TypeOfFurniture.CLOSER))
                .addEndDoorLock(dAO.getFurniture(TypeOfFurniture.END_DOOR_LOCK))
                .addGlass(dAO.getFurnitureByType(TypeOfFurniture.TYPE_GLASS, idType))
                .addToning(dAO.getFurniture(TypeOfFurniture.GLASS_PELLICLE))
                .addArmor(dAO.getFurniture(TypeOfFurniture.ARMOR_GLASS_PELLICLE));
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
            //exclusively for the test
            if ("ДПД(противопожарная)".equals(doorClass.getName())) {
                door.addAvailableDoorClass(doorClass.clearNonSerializingFields());
            }
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

        if (intTypeid==0 && intClassId>0) {
            DoorClass doorClass = dAO.getDoorClass(intClassId);
            doorType.setDoorClass(doorClass);
        }
        if ( intTypeid>0){
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


    public int saveOrUpdateDoorType(DoorType doorType){
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
