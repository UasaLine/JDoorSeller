package com.jds.service;

import com.jds.dao.repository.MainDAO;
import com.jds.dao.entity.*;
import com.jds.model.*;
import com.jds.model.enumClasses.PriceGroups;
import com.jds.model.enumClasses.TypeOfLimitionDoor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MaineService {

    @Autowired
    private MainDAO dAO;

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
                .map(DoorClass::clearNonSerializingFields)
                .map(DoorClass::sortDoorTypesList)
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

    public List<DoorType> getTypesByClassId(@NonNull String classId) {

        int intClassId = Integer.parseInt(classId);

        DoorClass doorClass = dAO.getDoorClass(intClassId);
        return doorClass.getDoorTypes().stream()
                .sorted()
                .collect(Collectors.toList());

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

    public void saveAsLimitationDoorForColors(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                              List<LimitationDoor> colorList, List<LimitationDoor> oldLimitList) {

        colorList.stream().forEach(color -> dAO.saveOrUpdateLimitationDoor(color));

    }

    public void saveAsLimitationDoorForFurniture(@NonNull DoorType doorType, @NonNull TypeOfLimitionDoor type,
                                                 @NonNull List<DoorFurniture> furnitureList, List<LimitationDoor> oldLimitList) {

        furnitureList.stream().forEach(furniture -> dAO.saveOrUpdateLimitationDoor(LimitationDoor.getNewLimit(furniture, doorType, type, oldLimitList)));

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

    public static int dooltranslateIntoInt(boolean value) {
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getClassId(@NonNull String typeId) {

        String response = "0";
        if (!"0".equals(typeId)) {
            DoorClass doorClass = dAO.getDoorClassByDoorTypeId(Integer.parseInt(typeId));
            if (doorClass != null) {
                response = String.valueOf(doorClass.getId());
            }
        }
        return response;
    }


    public Set<PriceGroups> getAllPriceGroup() {
        return EnumSet.allOf(PriceGroups.class);
    }

    public DoorType getDoorTypeclearNonSer(String typeId) {
        if (typeId == null) {
            return new DoorType();
        }
        DoorType doorType = getDoorType(Integer.parseInt(typeId));
        return doorType.clearNonSerializingFields();
    }
}
