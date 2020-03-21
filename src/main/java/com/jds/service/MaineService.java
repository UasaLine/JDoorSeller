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
