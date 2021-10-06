package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.*;
import com.jds.model.*;
import com.jds.model.cutting.DoorPart;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.enumClasses.OrderStatus;
import com.jds.model.enumClasses.SideDoorOpen;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoorServiceImpl implements DoorService {

    @Autowired
    private MainDAO dAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private MaterialsRepository materialsDAO;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private OrderDiscountService orderDiscountService;
    @Autowired
    private SpecificationService specificationService;

    @Override
    public DoorEntity calculate(@NonNull DoorEntity door) {

        return recalculateByPrice(
                door,
                userService.getCurrentUser().getDiscount(),
                userService.getUserSetting().getRetailMargin());
    }

    @Override
    public DoorEntity getDoor(@NonNull int doorId, @NonNull int orderId, @NonNull int typeId) {

        DoorEntity door;

        if (DoorEntity.isNotNew(doorId)) {
            door = getDoorFromDao(doorId, orderId);

        } else if (DoorType.isNotNew(typeId)) {
            door = templateService.newDoor(typeId, doorId);

        } else {
            door = new DoorEntity();
            door.addAvailableDoorClass(dAO.getAvailableDoorClass());
        }

        if (DoorOrder.isNotNew(orderId)) {
            door.setOrderHolder(orderId);
        }

        return door.clearNonSerializingFields();
    }

    private DoorEntity getDoorFromDao(int doorId, int orderId) {
        DoorEntity door = dAO.getDoor(doorId);
        DoorOrder order = orderService.getOrder(orderId);
        if (order.getStatus() == OrderStatus.CALC) {
            door.setTemplate(templateService.getTemplate(String.valueOf(door.getDoorType().getId())));
        }
        return door;
    }

    public DoorEntity recalculateByPrice(@NonNull DoorEntity doorEntity,
                                         @NonNull int discount,
                                         @NonNull int retailMargin) {

        if (doorEntity.isNew()) {
            dAO.saveDoor(doorEntity);
        }

        doorEntity
                .addPriceToCostList(discount)
                .costOfChangesAtTemplate()
                .calculateGlass()
                .calculateFurniture()
                .calculateStainlessSteelDoorstep()
                .addRetailMarginToCostList(retailMargin)
                .setPriceOfDoorType(userService.getCurrentUser())
                .createName();

        specificationService.createSpecification(doorEntity);

        return addToOrderIfNotExist(doorEntity);
    }

    //@TODO (salagaev) old calc method ..
    public DoorEntity recalculateTheDoorCompletely(DoorEntity door) {


        PayrollSettings paySettings = new PayrollSettings();
        paySettings.setBendSetting(dAO.getbendSettingId(door.getDoorType().getId(), door.getMetal(), door.getSealingLine()));
        paySettings.setConstMap(dAO.getSalaryConstantsMap());
        paySettings.setDoorColors(colorDao.getDoorColorByName(door.getDoorColor()));
        paySettings.setDoorType(dAO.getDoorType(door.getDoorType().getId()));
        paySettings.setSalarySetting(dAO.getSalarySetting(door.getMetal()));

        List<SpecificationSetting> speciSettingList = materialsDAO.getSpecificationSetting(door.getMetal(), door.getDoorType().getId());

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
    public DoorEntity saveDoor(@NonNull DoorEntity door) {

        door.createName();
        door.clearEmptyLinks();
        calculate(door);
        door = dAO.saveDoor(door);
        return addToOrderIfNotExist(door);

    }

    @Override
    public DoorOrder deleteDoorFromOrder(@NonNull String id, @NonNull String orderId) {

        if (!orderId.isEmpty() && !orderId.equals("0") && !id.isEmpty() && !id.equals("0")) {
            DoorOrder order = orderDAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1) {
                orderDAO.saveOrder(order.calculateTotal(userService.getUserSetting(), orderDiscountService.getOrderDiscounts(orderId)));
                return orderService.clearNonSerializingFields(order);
            }

        }

        return null;
    }

    public DoorEntity addToOrderIfNotExist(@NonNull DoorEntity door) {

        if (door.getOrderHolder() == 0) {
            return door;
        }

        DoorOrder order = orderDAO.getOrder(door.getOrderHolder());
        DoorEntity fineDoor = order.getDoors().stream()
                .filter(doorItem -> doorItem.getId() == door.getId())
                .findFirst()
                .orElse(null);

        order.calculateTotal(userService.getUserSetting(), orderDiscountService.getOrderDiscounts(String.valueOf(order.getOrderId())));

        if (fineDoor == null) {
            order.addDoor(door);
        }
        orderDAO.saveOrder(order);
        return door;
    }

    @Override
    public List<LineSpecification> getSpecificationByDoorId(@NonNull String doorId) {

        DoorEntity doorEntity = dAO.getDoor(Integer.parseInt(doorId));

        //@TODO (salagaev) deprecate, to be removed
        List<LineSpecification> lineSpec = new ArrayList<>();

        lineSpec.stream()
                .forEach((lin) -> addFurKitToLineSpec(lineSpec, doorEntity));

        return lineSpec;

    }

    public List<LineSpecification> addFurKitToLineSpec(List<LineSpecification> lineSpec, DoorEntity doorEntity) {

        FurnitureKit furnitureKit = doorEntity.getFurnitureKit();
        DoorType doorType = doorEntity.getDoorType();

        if (furnitureKit.getHandle() != null) {
            addfurniture(lineSpec, furnitureKit.getHandle(), doorType);
        }

        if (furnitureKit.getTopLock() != null) {
            addfurniture(lineSpec, furnitureKit.getTopLock(), doorType);
        }
        if (furnitureKit.getTopLockCylinder() != null) {
            addfurniture(lineSpec, furnitureKit.getTopLockCylinder(), doorType);
        }
        if (furnitureKit.getTopInLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getTopInLockDecor(), doorType);
        }
        if (furnitureKit.getTopOutLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getTopOutLockDecor(), doorType);
        }
        if (furnitureKit.getLowerLock() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLock(), doorType);
        }

        if (furnitureKit.getLowerLock() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLock(), doorType);
        }
        if (furnitureKit.getLowerLockCylinder() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerLockCylinder(), doorType);
        }
        if (furnitureKit.getLowerInLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerInLockDecor(), doorType);
        }
        if (furnitureKit.getLowerOutLockDecor() != null) {
            addfurniture(lineSpec, furnitureKit.getLowerOutLockDecor(), doorType);
        }

        if (furnitureKit.getCloser() != null) {
            addfurniture(lineSpec, furnitureKit.getCloser(), doorType);
        }
        if (furnitureKit.getEndDoorLock() != null) {
            addfurniture(lineSpec, furnitureKit.getEndDoorLock(), doorType);
        }


        return lineSpec;
    }

    public void addfurniture(List<LineSpecification> lineSpec, DoorFurniture furniture, DoorType doorType) {

        lineSpec.add(LineSpecification.builder()
                .name(furniture.getName())
                .value(1)
                .materialId(furniture.getIdManufacturerProgram())
                .build());

    }
}
