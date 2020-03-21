package com.jds.service;

import com.jds.dao.ColorRepository;
import com.jds.dao.MainDAO;
import com.jds.dao.MetalRepository;
import com.jds.dao.OrderDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoorService implements DoorServ {

    @Autowired
    private MainDAO dAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MaineService maineService;
    @Autowired
    private UserService userService;
    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;

    @Override
    public DoorEntity calculateTheDoor(@NonNull DoorEntity door) {


        if (door.getDoorType().getPriceList() == 1) {

            return recalculateTheDoorByPriceList(door,
                    userService.getCurrentUser().getDiscount(),
                    userService.getUserSetting().getRetailMargin());

        } else {
            return recalculateTheDoorCompletely(door);
        }
    }

    public DoorEntity recalculateTheDoorByPriceList(@NonNull DoorEntity doorEntity,
                                                    @NonNull int discount,
                                                    @NonNull int RetailMargin) {


        return addDooToOrder(doorEntity
                .setPriceOfDoorType(discount, RetailMargin)
                .createName()
        );
    }

    public DoorEntity recalculateTheDoorCompletely(DoorEntity door) {


        PayrollSettings paySettings = new PayrollSettings();
        paySettings.setBendSetting(dAO.getbendSettingId(door.getDoorType().getId(), door.getMetal(), door.getSealingLine()));
        paySettings.setConstMap(dAO.getSalaryConstantsMap());
        paySettings.setDoorColors(colorDao.getDoorColorByName(door.getDoorColor()));
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

        Metal metal = metalDao.getMetal(door.getMetal());

        door.setSheets(sheetCutting.getSheets());
        door.calculateWeigh(metal);
        door.calculateCostMetal(metal);

        return door;
    }

    public List<DoorPart> getDoorPart(DoorEntity door) {

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType().getId()), door);

    }

    @Override
    public DoorEntity getDoor(@NonNull int id, @NonNull int orderId, @NonNull int typid) {

        DoorEntity door;

        if (id > 0 && typid == 0) {
            door = dAO.getDoor(id);
        } else if (typid > 0) {
            door = createNewDoorByTemplate(typid, id);
        } else {
            door = createNewDoorWithAvailableDoorClass();
        }

        if (orderId != 0 && door.getId() == 0) {
            door.setOrderHolder(orderId);
        }

        return door.clearNonSerializingFields();
    }

    public DoorEntity createNewDoorWithAvailableDoorClass() {

        DoorEntity door = new DoorEntity();
        door.addAvailableDoorClass(dAO.getAvailableDoorClass());

        return door;
    }

    public DoorEntity createNewDoorByTemplate(@NonNull int typeId, @NonNull int id) {

        RestrictionOfSelectionFields template = maineService.getTemplateFromLimits(String.valueOf(typeId));
        DoorType doorType = dAO.getDoorType(typeId);

        DoorEntity doorEntity = new DoorEntity();
        doorEntity.setId(id);
        doorEntity.addAvailableDoorClass(dAO.getAvailableDoorClass());

        doorEntity.setDoorType(doorType);

        doorEntity.setTemplate(template);

        doorEntity.setMetal(findInTemplateRestriction(template.getMetal()));
        //doorEntity.setWidthDoor(findInTemplateSize(doorTemplate.getTemplate().getWidthDoor()));
        //doorEntity.setHeightDoor(findInTemplateSize(doorTemplate.getTemplate().getHeightDoor()));
        doorEntity.setDoorLeaf(doorType.getDoorLeaf());
        //doorEntity.setActiveDoorLeafWidth()
        //doorEntity.setDoorFanlightHeight
        doorEntity.setDeepnessDoor((int) findInTemplateRestriction(template.getDeepnessDoor()));
        doorEntity.setThicknessDoorLeaf((int) findInTemplateRestriction(template.getThicknessDoorLeaf()));
        //sideDoorOpen
        //innerDoorOpen;

        doorEntity.setDoorstep((int) findInTemplateRestriction(template.getDoorstep()));
        doorEntity.setStainlessSteelDoorstep((int) findInTemplateRestriction(template.getStainlessSteelDoorstep()));
        doorEntity.setTopDoorTrim((int) findInTemplateRestriction(template.getTopDoorTrim()));
        doorEntity.setLeftDoorTrim((int) findInTemplateRestriction(template.getLeftDoorTrim()));
        doorEntity.setRightDoorTrim((int) findInTemplateRestriction(template.getRightDoorTrim()));

        //sealingLine
        doorEntity.setFirstSealingLine((int) findInTemplateRestriction(template.getFirstSealingLine()));
        doorEntity.setSecondSealingLine((int) findInTemplateRestriction(template.getSecondSealingLine()));
        doorEntity.setThirdSealingLine((int) findInTemplateRestriction(template.getThirdSealingLine()));
        //price
        //discountPrice
        //priceWithMarkup
        doorEntity.setDoorColor(findInTemplateColor(template.getColors()));

        doorEntity.setFurnitureKit(FurnitureKit.instanceKit(template));
        /*
        isDoorGlass
        furnitureKit
        doorGlass

        additionallyHingeMain
        additionallyHingeNotMain
        amplifierCloser
       */

        return doorEntity;

    }

    public double findInTemplateRestriction(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getStartRestriction();
        }

        return 0;
    }

    public String findInTemplateColor(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getFirstItem();
        }

        return "";
    }

    @Override
    public DoorEntity saveDoor(@NonNull DoorEntity door) {

        return addDooToOrder(dAO.saveDoor(door.clearEmptyLinks()));

    }
    @Override
    public DoorsОrder deleteDoorFromOrder(@NonNull String id, @NonNull String orderId) {

        if (!orderId.isEmpty() && !orderId.equals("0") && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = orderDAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                orderDAO.saveOrder(order.calculateTotal());
                return orderService.clearNonSerializingFields(order);
            }

        }

        return null;
    }

    public DoorEntity addDooToOrder(@NonNull DoorEntity door) {

        if (door.getOrderHolder() == 0) {
            return door;
        }

        DoorsОrder order = orderDAO.getOrder(door.getOrderHolder());

        List<DoorEntity> doors = order.getDoors();
        for (int i = 0; i < doors.size(); i++) {
            if (doors.get(i).getId() == door.getId()) {
                return door;
            }
        }

        order.addDoor(door);
        orderDAO.saveOrder(order.calculateTotal());
        return door;

    }

    @Override
    public List<LineSpecification> getSpecificationByDoorId(@NonNull String doorId){

        DoorEntity doorEntity = dAO.getDoor(Integer.parseInt(doorId));

        List<LineSpecification> lineSpec = dAO.getLineSpecification(doorEntity.getDoorType().getId());

        lineSpec.stream()
                .peek((lin)->addFurKitToLineSpec(lineSpec,doorEntity))
                .forEach((lin)->lin.getDoorType().clearNonSerializingFields());

        return lineSpec;

    }

    public List<LineSpecification> addFurKitToLineSpec(List<LineSpecification> lineSpec,DoorEntity doorEntity){

        FurnitureKit furnitureKit = doorEntity.getFurnitureKit();
        DoorType doorType = doorEntity.getDoorType();

        if (furnitureKit.getHandle()!=null){
            addfurniture(lineSpec,furnitureKit.getHandle(),doorType);
        }

        if (furnitureKit.getTopLock()!=null){
            addfurniture(lineSpec,furnitureKit.getTopLock(),doorType);
        }
        if (furnitureKit.getToplockCylinder()!=null){
            addfurniture(lineSpec,furnitureKit.getToplockCylinder(),doorType);
        }
        if (furnitureKit.getTopinternaLockDecoration()!=null){
            addfurniture(lineSpec,furnitureKit.getTopinternaLockDecoration(),doorType);
        }
        if (furnitureKit.getTopouterLockDecoration()!=null){
            addfurniture(lineSpec,furnitureKit.getTopouterLockDecoration(),doorType);
        }
        if (furnitureKit.getLowerLock()!=null){
            addfurniture(lineSpec,furnitureKit.getLowerLock(),doorType);
        }

        if (furnitureKit.getLowerLock()!=null){
            addfurniture(lineSpec,furnitureKit.getLowerLock(),doorType);
        }
        if (furnitureKit.getLowerlockCylinder()!=null){
            addfurniture(lineSpec,furnitureKit.getLowerlockCylinder(),doorType);
        }
        if (furnitureKit.getLowerinternaLockDecoration()!=null){
            addfurniture(lineSpec,furnitureKit.getLowerinternaLockDecoration(),doorType);
        }
        if (furnitureKit.getLowerouterLockDecoration()!=null){
            addfurniture(lineSpec,furnitureKit.getLowerouterLockDecoration(),doorType);
        }

        if (furnitureKit.getCloser()!=null){
            addfurniture(lineSpec,furnitureKit.getCloser(),doorType);
        }
        if (furnitureKit.getEndDoorLock()!=null){
            addfurniture(lineSpec,furnitureKit.getEndDoorLock(),doorType);
        }


        return lineSpec;
    }

    public void addfurniture(List<LineSpecification> lineSpec,DoorFurniture furniture,DoorType doorType){

        lineSpec.add(LineSpecification.builder()
                .name(furniture.getName())
                .value(1)
                .materialId(furniture.getIdManufacturerProgram())
                .doorType(doorType)
                .build());


    }
}
