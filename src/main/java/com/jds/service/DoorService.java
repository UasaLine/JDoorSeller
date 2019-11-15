package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.dao.OrderDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.modelEnum.TypeOfLimitionDoor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoorService {

    @Autowired
    private MainDAO dAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MaineService maineService;


    public DoorEntity calculateTheDoor(@NonNull DoorEntity door) {
        if (door.getDoorType().getPriceList() == 1) {
            return recalculateTheDoorByPriceList(door);
        } else {
            return recalculateTheDoorCompletely(door);
        }
    }

    public DoorEntity recalculateTheDoorByPriceList(DoorEntity doorEntity) {
        return doorEntity.setPriceOfDoorType()
                .createName();
    }

    public DoorEntity recalculateTheDoorCompletely(DoorEntity door) {


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

        return door;
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

    public List<DoorPart> getDoorPart(DoorEntity door) {

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType().getId()), door);

    }

    public DoorEntity getDoor(@NonNull int id, @NonNull String orderId, @NonNull int typid) {

        DoorEntity door;

        if (id > 0){
            door = dAO.getDoor(id);
        }
        else if (id==0 && typid>0){
            door = createNewDoorByTemplate(typid);
        }
        else {
            door = new DoorEntity();
            List<DoorClass> doorClassList = dAO.getAvailableDoorClass();
            for (DoorClass doorClass : doorClassList) {
                door.addAvailableDoorClass(doorClass.clearNonSerializingFields());
            }
        }


        int intOrderId = Integer.parseInt(orderId);
        if (!orderId.isEmpty() && !orderId.equals("0") && door.getId() == 0) {
            DoorsОrder order = orderDAO.getOrder(intOrderId);
            order.addDoor(door);
            orderDAO.saveOrder(order);
        }

        return door.clearNonSerializingFields();
    }

    public DoorEntity createNewDoorByTemplate(@NonNull int typeId){

        DoorTemplate doorTemplate = maineService.getDoorTemplate(String.valueOf(typeId));

        return new DoorEntity();

    }



    public DoorEntity saveDoor(DoorEntity door) {

        return dAO.saveDoor(door.clearEmptyLinks());

    }

    public DoorsОrder deleteDoorFromOrder(String id, String orderId) {

        if (orderId != null && !orderId.isEmpty() && !orderId.equals("0") && id != null && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = orderDAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                orderDAO.saveOrder(order);
                return orderService.clearNonSerializingFields(order);
            }

        }

        return null;
    }


}
