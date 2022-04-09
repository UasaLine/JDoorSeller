package com.jds.service;

import com.jds.dao.entity.*;
import com.jds.dao.repository.*;
import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.DoorTemplate;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.ShortTemplate;
import com.jds.model.enumModels.SideDoorOpen;

import com.jds.model.enumModels.TypeOfFurniture;
import com.jds.model.enumModels.TypeOfLimitionDoor;
import com.jds.model.tools.DoorSellerBooleanUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateService {

    @Autowired
    private MainDAO mainDAO;
    @Autowired
    private MetalRepository metalDao;
    @Autowired
    private ColorRepository colorDao;
    @Autowired
    private FurnitureRepository furnitureDao;
    @Autowired
    private FurnitureService furnitureService;
    @Autowired
    private GlassPositionService glassPositionService;

    private Logger logger = LoggerFactory.getLogger(TemplateService.class);

    public List<ShortTemplate> getTemplateList() {

        List<DoorType> list = mainDAO.getDoorTypeListFromLimitTable();
        return list.stream()
                .map(ShortTemplate::new)
                .sorted((o1, o2) -> o1.compareTo(o2))
                .collect(Collectors.toList());
    }

    public DoorTemplate getDoorTemplate(@NonNull String doorTypeId) {

        return DoorTemplate.builder()
                .template(getTemplate(doorTypeId))
                .restriction(getRestrictionOfSelectionFields(doorTypeId))
                .build();
    }

    public void saveDoorTemplate(@NonNull RestrictionOfSelectionFields restriction) {

        DoorType doorType = mainDAO.getDoorType(restriction.getDoorTypeid());

        List<LimitationDoor> limitList = mainDAO.getLimitationDoor(doorType.getId());

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.METAL_THICKNESS, restriction.getMetal(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH, restriction.getWidthDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT, restriction.getHeightDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DEPTH, restriction.getDeepnessDoor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEAF_THICKNESS, restriction.getThicknessDoorLeaf(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.WIDTH_ACTIVE_LEAF, restriction.getWidthDoorLeaf(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HEIGHT_FANLIGHT, restriction.getHeightDoorFanlight(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.COLOR_DOOR, restriction.getColors(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DESIGN_DOOR, restriction.getDesign(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SHIELD_COLOR, restriction.getShieldColor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SHIELD_DESIGN, restriction.getShieldDesign(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.OUT_SHIELD_COLOR, restriction.getOutShieldColor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.OUT_SHIELD_DESIGN, restriction.getOutShieldDesign(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.DOORSTEP, restriction.getDoorstep(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.STAINLESS_STEEL_DOORSTEP, restriction.getStainlessSteelDoorstep(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.INTERNAL_OPENING, restriction.getInnerOpen(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.FIRST_SEALING_LINE, restriction.getFirstSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SECOND_SEALING_LINE, restriction.getSecondSealingLine(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.THIRD_SEALING_LINE, restriction.getThirdSealingLine(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_DOOR_TRIM, restriction.getTopDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEFT_DOOR_TRIM, restriction.getLeftDoorTrim(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.RIGHT_DOOR_TRIM, restriction.getRightDoorTrim(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_DOOR_TRIM_SIZE, restriction.getTopDoorTrimSize(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LEFT_DOOR_TRIM_SIZE, restriction.getLeftDoorTrimSize(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.RIGHT_DOOR_TRIM_SIZE, restriction.getRightDoorTrimSize(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_LOCK, restriction.getTopLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_LOCK, restriction.getLowerLock(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.HANDLE, restriction.getHandle(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOCK_CYLINDER, restriction.getLockCylinder(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.PEEPHOLE, restriction.getPeephole(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.PEEPHOLE_POSITION, restriction.getPeepholePosition(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.NIGHT_LOCK, restriction.getNightLock(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_IN_LOCK_DECOR, restriction.getTopInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TOP_OUT_LOCK_DECOR, restriction.getTopOutLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_IN_LOCK_DECOR, restriction.getLowerInLockDecor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.LOWER_OUT_LOCK_DECOR, restriction.getLowerOutLockDecor(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.CLOSER, restriction.getCloser(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.END_DOOR_LOCK, restriction.getEndDoorLock(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TYPE_GLASS, restriction.getTypeDoorGlass(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.TONING, restriction.getToning(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.ARMOR, restriction.getArmor(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.GLASS_POSITION, restriction.getGlassPositions(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SIZE_COST_HEIGHT, restriction.getSizeCostHeight(), limitList);
        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.SIZE_COST_WIDTH, restriction.getSizeCostWidth(), limitList);

        saveAsLimitationDoor(doorType, TypeOfLimitionDoor.STANDARD_SIZE, restriction.getStandardSize(), limitList);

        deleteAllDoorTemplate(limitList);

    }

    public RestrictionOfSelectionFields getTemplate(@NonNull String doorTypeId) {

        int intDoorTypeId = Integer.parseInt(doorTypeId);

        List<LimitationDoor> limitList = mainDAO.getLimitationDoor(intDoorTypeId);
        List<ImageEntity> colorList = colorDao.getShieldGlass();
        RestrictionOfSelectionFields restriction = new RestrictionOfSelectionFields();
        restriction.setDoorTypeid(intDoorTypeId);

        limitList.forEach(lim -> restrictionBuild(restriction, lim));
        restriction.addShieldGlass(colorList);
        return restriction;
    }

    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(@NonNull String idDoorType) {

        int idType = Integer.parseInt(idDoorType);

        return new RestrictionOfSelectionFields(idType)

                .widthDoor(generateSizeDoor(TypeOfLimitionDoor.HEIGHT))
                .heightDoor(generateSizeDoor(TypeOfLimitionDoor.WIDTH))

                .stuffMetal(metalDao.getMetals())

                .stuffColors(colorDao.getDoorColors())
                .stuffDesign(colorDao.getDoorDesign())
                .stuffDoorColor(colorDao.getDoorColors())
                .stuffDoorDesign(colorDao.getDoorDesign())
                .stuffShieldColor(colorDao.getShieldColor())
                .stuffShieldDesign(colorDao.getShieldDesign())

                .stuffOutShieldColor(colorDao.getShieldColor())
                .stuffOutShieldDesign(colorDao.getShieldDesign())

                .addTopLock(furnitureDao.getFurniture(TypeOfFurniture.TOP_LOCK))
                .addLowerLock(furnitureDao.getFurniture(TypeOfFurniture.LOWER_LOCK))
                .addHendle(furnitureDao.getFurniture(TypeOfFurniture.HANDLE))
                .addLockCylinder(furnitureDao.getFurniture(TypeOfFurniture.LOCK_CYLINDER))
                .addCloser(furnitureDao.getFurniture(TypeOfFurniture.CLOSER))
                .addEndDoorLock(furnitureDao.getFurniture(TypeOfFurniture.END_DOOR_LOCK))

                .addPeephole(furnitureDao.getFurniture(TypeOfFurniture.PEEPHOLE))
                .addPeepholePosition()

                .addNightLock(furnitureDao.getFurniture(TypeOfFurniture.NIGHT_LOCK))

                .addGlass(furnitureDao.getFurniture(TypeOfFurniture.TYPE_GLASS))
                .addToning(furnitureDao.getFurniture(TypeOfFurniture.GLASS_PELLICLE))
                .addArmor(furnitureDao.getFurniture(TypeOfFurniture.ARMOR_GLASS_PELLICLE))
                .addGlassPositions(glassPositionService.get())


                .addTopInLockDecor(furnitureDao.getFurniture(TypeOfFurniture.TOP_IN_LOCK_DECOR))
                .addTopOutLockDecor(furnitureDao.getFurniture(TypeOfFurniture.TOP_OUT_LOCK_DECOR))
                .addLowerInLockDecor(furnitureDao.getFurniture(TypeOfFurniture.LOWER_IN_LOCK_DECOR))
                .addLowerOutLockDecor(furnitureDao.getFurniture(TypeOfFurniture.LOWER_OUT_LOCK_DECOR));
    }

    public void saveAsLimitationDoor(@NonNull DoorType doorType,
                                     @NonNull TypeOfLimitionDoor type,
                                     @NonNull List<LimitationDoor> newlimList,
                                     List<LimitationDoor> oldLimitList) {

        newlimList.stream()
                .peek(lim -> lim.setFildForSaveToDataBase(doorType, type, oldLimitList))
                .forEach(lim -> mainDAO.saveOrUpdateLimitationDoor(lim));

    }

    public void deleteAllDoorTemplate(@NonNull List<LimitationDoor> limitList) {


        limitList.stream().forEach(limit -> mainDAO.deleteLimit(limit));

    }

    public void restrictionBuild(@NonNull RestrictionOfSelectionFields restriction, @NonNull LimitationDoor lim) {

        TypeOfLimitionDoor typeOfLimitionDoor = lim.getTypeSettings();

        switch (typeOfLimitionDoor) {
            case METAL_THICKNESS:
                restriction.addMetal(lim);
                break;

            case WIDTH:
                restriction.addWidthDoor(lim);
                break;
            case HEIGHT:
                restriction.addHeightDoor(lim);
                break;
            case WIDTH_ACTIVE_LEAF:
                restriction.addWidthDoorLeaf(lim);
                break;
            case HEIGHT_FANLIGHT:
                restriction.addHeightDoorFanlight(lim);
                break;
            case DEPTH:
                restriction.addDeepnessDoor(lim);
                break;
            case LEAF_THICKNESS:
                restriction.addThicknessDoorLeaf(lim);
                break;
            case DOORSTEP:
                restriction.addDoorstep(lim);
                break;
            case STAINLESS_STEEL_DOORSTEP:
                restriction.addStainlessSteelDoorstep(lim);
                break;
            case INTERNAL_OPENING:
                restriction.addInnerOpen(lim);
                break;
            case STANDARD_SIZE:
                restriction.addStandardSize(lim);
                break;

            case FIRST_SEALING_LINE:
                restriction.addFirstSealingLine(lim);
                break;
            case SECOND_SEALING_LINE:
                restriction.addSecondSealingLine(lim);
                break;
            case THIRD_SEALING_LINE:
                restriction.addThirdSealingLine(lim);
                break;

            case TOP_DOOR_TRIM:
                restriction.addTopDoorTrim(lim);
                break;
            case LEFT_DOOR_TRIM:
                restriction.addLeftDoorTrim(lim);
                break;
            case RIGHT_DOOR_TRIM:
                restriction.addRightDoorTrim(lim);
                break;
            case TOP_DOOR_TRIM_SIZE:
                restriction.addTopDoorTrimSize(lim);
                break;
            case LEFT_DOOR_TRIM_SIZE:
                restriction.addLeftDoorTrimSize(lim);
                break;
            case RIGHT_DOOR_TRIM_SIZE:
                restriction.addRightDoorTrimSize(lim);
                break;

            case COLOR_DOOR:
                restriction.addColors(lim);
                break;
            case DESIGN_DOOR:
                restriction.addDesign(lim);
                break;
            case SHIELD_COLOR:
                restriction.addShieldColor(lim);
                break;
            case SHIELD_DESIGN:
                restriction.addShieldDesign(lim);
                break;

            case OUT_SHIELD_COLOR:
                restriction.addOutShieldColor(lim);
                break;
            case OUT_SHIELD_DESIGN:
                restriction.addOutShieldDesign(lim);
                break;

            case TOP_LOCK:
                restriction.addTopLock(lim);
                break;
            case LOWER_LOCK:
                restriction.addLowerLock(lim);
                break;
            case HANDLE:
                restriction.addHandle(lim);
                break;
            case LOCK_CYLINDER:
                restriction.addLockCylinder(lim);
                break;
            case TOP_IN_LOCK_DECOR:
                restriction.addTopInLockDecor(lim);
                break;
            case TOP_OUT_LOCK_DECOR:
                restriction.addTopOutLockDecor(lim);
                break;
            case LOWER_IN_LOCK_DECOR:
                restriction.addLowerInLockDecor(lim);
                break;
            case LOWER_OUT_LOCK_DECOR:
                restriction.addLowerOutLockDecor(lim);
                break;

            case CLOSER:
                restriction.addCloser(lim);
                break;
            case END_DOOR_LOCK:
                restriction.addEndDoorLock(lim);
                break;
            case PEEPHOLE:
                restriction.addPeephole(lim);
                break;
            case PEEPHOLE_POSITION:
                restriction.addPeepholePosition(lim);
                break;

            case NIGHT_LOCK:
                restriction.addNightLock(lim);
                break;

            case TYPE_GLASS:
                restriction.addTypeDoorGlass(lim);
                break;
            case TONING:
                restriction.addToning(lim);
                break;
            case ARMOR:
                restriction.addArmor(lim);
                break;
            case SIZE_COST_HEIGHT:
                restriction.addSizeCostHeight(lim);
                break;
            case SIZE_COST_WIDTH:
                restriction.addSizeCostWidth(lim);
                break;
            case GLASS_POSITION:
                restriction.addGlassPositions(lim);
                break;

            default:
                logger.error("[restrictionBuild] typeOfLimitionDoor: {} - is not processed by the switch", typeOfLimitionDoor);
        }
    }

    public List<LimitationDoor> generateSizeDoor(@NonNull TypeOfLimitionDoor typeSettings) {
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

    public static LimitationDoor getDefaultLine(List<LimitationDoor> list) {
        return list.stream()
                .filter(line -> line.getDefaultValue() == 1)
                .findFirst().orElse(new LimitationDoor());
    }

    public static LimitationDoor getLineByItemId(List<LimitationDoor> list, int id) {
        return list.stream()
                .filter(line -> line.getItemId() == id)
                .findFirst().orElse(new LimitationDoor());
    }

    public DoorEntity newDoor(@NonNull int typeId, @NonNull int id) {

        DoorType doorType = mainDAO.getDoorType(typeId);

        DoorEntity doorEntity = new DoorEntity();
        doorEntity.setId(id);
        doorEntity.addAvailableDoorClass(mainDAO.getAvailableDoorClass());

        doorEntity.setDoorType(doorType);
        doorEntity.setSideDoorOpen(SideDoorOpen.RIGHT.toString());

        RestrictionOfSelectionFields template = getTemplate(String.valueOf(typeId));
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

                .typeDoorGlass(defaultAndGetFurniture(template.getTypeDoorGlass()))
                .toning(defaultAndGetFurniture(template.getToning()))
                .armor(defaultAndGetFurniture(template.getArmor()))
                .glassPosition(defaultAndGlassPosition(template.getGlassPositions()))
                .build();

        doorEntity.setFurnitureKit(FurnitureKit.instanceKit(availableFields));
        doorEntity.setShieldKit(ShieldKit.instanceKit(availableFields));
        DoorGlass glass = DoorGlass.instance(availableFields, doorEntity);
        doorEntity.setDoorGlass(glass);
        doorEntity.setIsDoorGlass(DoorSellerBooleanUtils.toInt(glass.exists()));

        return doorEntity;
    }

    private double findInTemplateRestriction(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(LimitationDoor::isDefault)
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getStartRestriction();
        }

        return 0;
    }

    private String findInTemplateColor(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(LimitationDoor::isDefault)
                .collect(Collectors.toList());

        if (defList.size() == 1) {
            return defList.get(0).getFirstItem();
        }

        return "";
    }

    private int findInTemplateSize(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(LimitationDoor::isDefault)
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

    private List<DoorFurniture> defaultAndGetFurniture(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(LimitationDoor::isDefault)
                .collect(Collectors.toList());
        return furnitureService.getFurnitureByLmit(defList);
    }

    private List<GlassPositionEntity> defaultAndGlassPosition(List<LimitationDoor> listLim) {
        return listLim.stream()
                .filter(LimitationDoor::isDefault)
                .map(l -> glassPositionService.getById(l.getItemId()))
                .collect(Collectors.toList());
    }

    private List<DoorFurniture> defaultAndConvertToFurniture(List<LimitationDoor> listLim) {
        return listLim.stream()
                .filter(LimitationDoor::isDefault)
                .map(DoorFurniture::newInstance)
                .collect(Collectors.toList());
    }

    private List<ImageEntity> defaultAndGetImage(List<LimitationDoor> listLim) {
        List<LimitationDoor> defList = listLim.stream()
                .filter(LimitationDoor::isDefault)
                .collect(Collectors.toList());
        return furnitureService.getImageByLimit(defList);
    }


}
