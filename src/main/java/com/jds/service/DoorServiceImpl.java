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
    private FurnitureService furnitureService;
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
            door = createNewDoorByTemplate(typeId, doorId);

        } else {
            door = createNewDoorWithAvailableDoorClass();
        }

        if (DoorOrder.isNotNew(orderId)) {
            door.setOrderHolder(orderId);
        }

        return door.clearNonSerializingFields();
    }

    private DoorEntity getDoorFromDao(int doorId, int orderId) {
        DoorEntity door = dAO.getDoor(doorId);
        allowEditing(door, orderId);
        return door;
    }

    private void allowEditing(DoorEntity door, int orderId) {
        DoorOrder order = orderService.getOrder(orderId);
        if (order.getStatus() == OrderStatus.CALC) {
            door.setTemplate(templateService.getTemplate(String.valueOf(door.getDoorType().getId())));
        }

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

    public DoorEntity createNewDoorWithAvailableDoorClass() {

        DoorEntity door = new DoorEntity();
        door.addAvailableDoorClass(dAO.getAvailableDoorClass());

        return door;
    }

    public DoorEntity createNewDoorByTemplate(@NonNull int typeId, @NonNull int id) {


        DoorType doorType = dAO.getDoorType(typeId);

        DoorEntity doorEntity = new DoorEntity();
        doorEntity.setId(id);
        doorEntity.addAvailableDoorClass(dAO.getAvailableDoorClass());

        doorEntity.setDoorType(doorType);
        doorEntity.setSideDoorOpen(SideDoorOpen.RIGHT.toString());

        RestrictionOfSelectionFields template = templateService.getTemplate(String.valueOf(typeId));
        doorEntity.setTemplate(template);

        doorEntity.setMetal(findInTemplateRestriction(template.getMetal()));
        doorEntity.setWidthDoor(findInTemplateSize(template.getWidthDoor()));
        doorEntity.setHeightDoor(findInTemplateSize(template.getHeightDoor()));
        doorEntity.setDoorLeaf(doorType.getDoorLeaf());
        doorEntity.setActiveDoorLeafWidth(findInTemplateSize(template.getWidthDoorLeaf()));

        doorEntity.setDeepnessDoor((int) findInTemplateRestriction(template.getDeepnessDoor()));
        doorEntity.setThicknessDoorLeaf((int) findInTemplateRestriction(template.getThicknessDoorLeaf()));


        doorEntity.setDoorstep((int) findInTemplateRestriction(template.getDoorstep()));
        doorEntity.setStainlessSteelDoorstep((int) findInTemplateRestriction(template.getStainlessSteelDoorstep()));

        doorEntity.setTopDoorTrim((int) findInTemplateRestriction(template.getTopDoorTrim()));
        doorEntity.setLeftDoorTrim((int) findInTemplateRestriction(template.getLeftDoorTrim()));
        doorEntity.setRightDoorTrim((int) findInTemplateRestriction(template.getRightDoorTrim()));

        doorEntity.setTopDoorTrimSize(findInTemplateSize(template.getTopDoorTrimSize()));
        doorEntity.setLeftDoorTrimSize(findInTemplateSize(template.getLeftDoorTrimSize()));
        doorEntity.setRightDoorTrimSize(findInTemplateSize(template.getRightDoorTrimSize()));

        //sealingLine
        doorEntity.setFirstSealingLine((int) findInTemplateRestriction(template.getFirstSealingLine()));
        doorEntity.setSecondSealingLine((int) findInTemplateRestriction(template.getSecondSealingLine()));
        doorEntity.setThirdSealingLine((int) findInTemplateRestriction(template.getThirdSealingLine()));
        //price
        //discountPrice
        //priceWithMarkup

        doorEntity.setDoorColor(findInTemplateColor(template.getColors()));

        LimitationDoor defaultDoorColor = template.getDefaultDoorColor();
        LimitationDoor defaultDoorDesign = template.getDefaultDoorDesign();
        LimitationDoor defaultOutShieldColor = template.getDefaultOutShieldColor();
        LimitationDoor defaultOutShieldDesign = template.getDefaultOutShieldDesign();

        doorEntity.setDoorDesign(DoorDesign.instanceDesign(
                defaultDoorColor != null ? colorDao.getColorById(defaultDoorColor.getItemId()) : null,
                defaultDoorDesign != null ? colorDao.getColorById(defaultDoorDesign.getItemId()) : null,
                defaultOutShieldColor != null ? colorDao.getColorById(defaultOutShieldColor.getItemId()) : null,
                defaultOutShieldDesign != null ? colorDao.getColorById(defaultOutShieldDesign.getItemId()) : null
        ));

        AvailableFieldsForSelection availableFields = AvailableFieldsForSelection.builder()

                .topLock(defaultAndGetFurniture(template.getTopLock()))
                .lowerLock(defaultAndGetFurniture(template.getLowerLock()))
                .topOutLockDecor(defaultAndGetFurniture(template.getTopOutLockDecor()))
                .topInLockDecor(defaultAndGetFurniture(template.getTopInLockDecor()))
                .lowerOutLockDecor(defaultAndGetFurniture(template.getLowerOutLockDecor()))
                .lowerInLockDecor(defaultAndGetFurniture(template.getLowerInLockDecor()))
                .lockCylinder(defaultAndGetFurniture(template.getLockCylinder()))
                .handle(defaultAndGetFurniture(template.getHandle()))

                .shieldColor(defaultAndGetImage(template.getShieldColor()))
                .shieldDesign(defaultAndGetImage(template.getShieldDesign()))
                .shieldGlass(furnitureService.getImageByLimit((template.getShieldGlass())))

                .peepholePosition(defaultAndConvertToFurniture(template.getPeepholePosition()))
                .peephole(defaultAndGetFurniture(template.getPeephole()))
                .closer(defaultAndGetFurniture(template.getCloser()))
                .nightLock(defaultAndGetFurniture(template.getNightLock()))
                .build();

        doorEntity.setFurnitureKit(FurnitureKit.instanceKit(availableFields));
        doorEntity.setShieldKit(ShieldKit.instanceKit(availableFields));

        return doorEntity;

    }

    private List<DoorFurniture> defaultAndGetFurniture(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());
        return furnitureService.getFurnitureByLmit(defList);
    }

    private List<DoorFurniture> defaultAndConvertToFurniture(List<LimitationDoor> listLim) {
        return listLim.stream()
                .filter(lim -> lim.isDefault())
                .map(DoorFurniture::newInstance)
                .collect(Collectors.toList());
    }

    private List<ImageEntity> defaultAndGetImage(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());
        return furnitureService.getImageByLimit(defList);
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

    public int findInTemplateSize(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            if (defList.get(0).getPairOfValues() == 1) {
                return defList.get(0).getDefaultValue();
            } else {
                return (int) defList.get(0).getStartRestriction();
            }
        }

        return 0;
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
