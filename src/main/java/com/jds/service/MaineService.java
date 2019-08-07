package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.model.modelEnum.TypeOfSalaryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaineService {

    @Autowired
    private MainDAO dAO;

    public List<DoorClass> getDoorClass() {
        return dAO.getDoorClass();
    }

    public List<FireproofDoor> getlistDoor() {
        return dAO.getlistDoor();
    }

    public List<DoorType> getDoorType() {
        return dAO.getDoorType();
    }

    public List<Metal> getMetals() {
        return dAO.getMetals();
    }

    public List<DoorPart> getDoorPart(DoorEntity door) {

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType()), door);

    }

    public DoorEntity calculateTheDoor(DoorEntity door) {


        PayrollSettings paySettings = new PayrollSettings();
        paySettings.setBendSetting(dAO.getbendSettingId(door.getDoorType(), door.getMetal(), door.getSealingLine()));
        paySettings.setConstMap(dAO.getSalaryConstantsMap());
        paySettings.setDoorColors(dAO.getDoorColor(door.getDoorColor()));
        paySettings.setDoorType(dAO.getDoorType(door.getDoorType()));
        paySettings.setSalarySetting(dAO.getSalarySetting(door.getMetal()));

        //new instance cost
        door.setCostList(new CostList());

        door = calculateMetalDoor(door)
                .calculateColorDoor(paySettings.getDoorColors())
                .calculateSalary(paySettings)
                .calculateFurniture()
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
                .addEndDoorLock(dAO.getFurniture(TypeOfFurniture.END_DOOR_LOCK));
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
        return dAO.saveDoor(door);
    }

    public DoorsОrder saveOrder(DoorsОrder order) {
        return dAO.saveOrder(order.calculateTotal());
    }

    public List<DoorsОrder> getOrders() {
        return dAO.getOrders();
    }

    public DoorsОrder getOrder(String id) {

        int intId = Integer.parseInt(id);

        if (intId == 0) {
            return new DoorsОrder();
        }

        return dAO.getOrder(intId);
    }

    public DoorEntity getDoor(String id, String orderId) {

        DoorEntity door = null;
        if (id != null && !id.isEmpty() && !id.equals("0")) {
            door = dAO.getDoor(Integer.parseInt(id));
            door.addAvailableDoorClass(new DoorClassForFrond(dAO.getDoorType(door.getDoorType()).getDoorClass()));
        }
        if (door == null) {
            door = new DoorEntity();

            List<DoorClass> doorClassList = dAO.getAvailableDoorClass();

            for (DoorClass doorClass : doorClassList) {
                door.addAvailableDoorClass(new DoorClassForFrond(doorClass));
            }
        }


        if (orderId != null && !orderId.isEmpty() && !orderId.equals("0") && (door.getId() == 0)) {

            DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
            order.addDoor(door);
            dAO.saveOrder(order);
        }

        return door;

    }

    public DoorsОrder deleteDoorFromOrder(String id, String orderId) {

        if (orderId != null && !orderId.isEmpty() && !orderId.equals("0") && id != null && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                dAO.saveOrder(order);
                return order;
            }

        }

        return null;
    }

    public DoorsОrder deleteOrder(String orderId) {
        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        return order;
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


}
