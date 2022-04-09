package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.*;
import com.jds.model.cutting.DoorPart;
import com.jds.model.cutting.Sheet;
import com.jds.model.enumModels.PriceGroups;
import com.jds.model.enumModels.PriceType;
import com.jds.model.enumModels.SideDoorOpen;
import com.jds.model.enumModels.TypeOfFurniture;
import com.jds.model.enumModels.TypeOfSalaryConst;
import com.jds.service.TemplateService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Door")
@Getter
@Setter
public class DoorEntity implements SerializingFields<DoorEntity> {

    @Transient
    @JsonIgnore
    private Logger logger = LoggerFactory.getLogger(DoorEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doorType")
    private DoorType doorType;

    @Column(name = "metal")
    private double metal;

    @Column(name = "widthDoor")
    private int widthDoor;

    @Column(name = "heightDoor")
    private int heightDoor;

    @Column(name = "doorLeaf")
    private int doorLeaf;

    @Column(name = "activeDoorLeafWidth")
    private int activeDoorLeafWidth;

    @Column(name = "doorFanlightHeight")
    private int doorFanlightHeight;

    @Column(name = "deepnessDoor")
    private int deepnessDoor;

    @Column(name = "thicknessDoorLeaf")
    private int thicknessDoorLeaf;

    @Column(name = "sideDoorOpen")
    private String sideDoorOpen;

    @Column(name = "innerDoorOpen")
    private int innerOpen;

    //doorstep
    @Column(name = "doorstep")
    private int doorstep;

    @Column(name = "stainlessSteelDoorstep")
    private int stainlessSteelDoorstep;

    //Trim
    @Column(name = "topDoorTrim")
    private int topDoorTrim;

    @Column(name = "topDoorTrimSize")
    private int topDoorTrimSize;

    @Column(name = "leftDoorTrim")
    private int leftDoorTrim;

    @Column(name = "leftDoorTrimSize")
    private int leftDoorTrimSize;

    @Column(name = "rightDoorTrim")
    private int rightDoorTrim;

    @Column(name = "rightDoorTrimSize")
    private int rightDoorTrimSize;

    //price
    @Column(name = "price")
    private int price;

    @Column(name = "discountPrice")
    private int discountPrice;

    @Column(name = "priceWithMarkup")
    private int priceWithMarkup;

    @Column(name = "doorColor")
    private String doorColor;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doorColor_id", referencedColumnName = "id")
    private DoorDesign doorDesign;

    @Column(name = "quantity")
    private int quantity;

    @JsonIgnore
    @ManyToMany(mappedBy = "doors", fetch = FetchType.LAZY)
    private List<DoorOrder> orders;

    @Transient
    private List<Sheet> sheets;

    @Transient
    private List<DoorClass> availableDoorClass;

    @Column(name = "isDoorGlass")
    private int isDoorGlass;

    @Transient
    private int isDoorFanlightGlass;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "glass_id", referencedColumnName = "id")
    private DoorGlass doorGlass;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "furniture_id", referencedColumnName = "id")
    private FurnitureKit furnitureKit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shield_id", referencedColumnName = "id")
    private ShieldKit shieldKit;

    @Column(name = "comment")
    private String comment;

    @Transient
    private CostList costList;

    @Transient
    private double weigh;

    @Transient
    private double space;

    //sealingLine
    @Transient
    private int sealingLine;

    @Transient
    private int firstSealingLine;

    @Transient
    private int secondSealingLine;

    @Transient
    private int thirdSealingLine;

    @Transient
    private int MDF;

    @Transient
    private int additionallyHingeMain;

    @Transient
    private int additionallyHingeNotMain;

    @Transient
    private int amplifierCloser;

    @Transient
    private int filling;

    @Transient
    private RestrictionOfSelectionFields template;

    @Transient
    private int orderHolder;

    public DoorEntity initKitFields() {

        if (furnitureKit == null) {
            furnitureKit = new FurnitureKit();
        }
        if (doorGlass == null) {
            doorGlass = new DoorGlass();
        }
        if (shieldKit == null) {
            shieldKit = new ShieldKit();
        }
        return this;
    }

    public DoorEntity calculateMaterials(List<SpecificationSetting> speciSettingList) {

        int costMetal = 0;
        for (SpecificationSetting setting : speciSettingList) {
            double value = setting.countByTheFormula(this);
            double materialsPrice = setting.getRawMaterials().getPrice();
            costMetal = (int) (value * materialsPrice);
            costList.addLine("материаллы : " + setting.getRawMaterials().getName() + ", " + value + ", " + materialsPrice + " руб."
                    , 12
                    , false
                    , costMetal);
        }

        return this;
    }

    public DoorEntity clearEmptyLinks() {

        if (doorGlass != null && !doorGlass.exists()) {
            doorGlass = null;
        }
        if (furnitureKit != null && !furnitureKit.exists()) {
            //@TODO Salagaev set furnitureKit = null;
        }
        if (shieldKit != null && !shieldKit.exists()) {
            shieldKit = null;
        }
        if (doorDesign != null && !doorDesign.exists()) {
            doorDesign = null;
        }
        return this;
    }

    public DoorEntity clearNonSerializingFields() {
        orders = null;
        if (getDoorGlass() != null) {
            getDoorGlass().clearNonSerializingFields();
        }
        if (getFurnitureKit() != null) {
            getFurnitureKit().clearNonSerializingFields();
        }
        if (getShieldKit() != null) {
            getShieldKit().clearNonSerializingFields();
        }
        if (getDoorDesign() != null) {
            getDoorDesign().clearNonSerializingFields();
        }
        if (doorType != null) {
            doorType.clearNonSerializingFields();
        }
        return this;
    }

    public void calculateWeigh(Metal metal) {

        space = 0;

        for (Sheet sheet : sheets) {
            space += sheet.getAmountWorkSpace();
        }
        space = space * 1.15;
        this.weigh = space * metal.getIndexHeft();
    }

    public void calculateCostMetal(Metal metal) {

        for (Sheet sheet : sheets) {
            List<DoorPart> doorPartArrayList = sheet.getContainsParts();
            for (DoorPart doorPart : doorPartArrayList) {
                costList.addLine("металл :" + doorPart.getName() + " - " + doorPart.getSpace()
                        , 1
                        , false
                        , 0);
            }
        }

        int quantitySheetsInt = sheets.size();
        Sheet sheet = sheets.get(quantitySheetsInt - 1);
        double quantitySheetsDoubl = (double) quantitySheetsInt - sheet.getResidueSpace();
        int costMetal = (int) (quantitySheetsDoubl * metal.getIndexHeft() * metal.getPrice());
        costList.addLine("металл : total [лист-" + quantitySheetsDoubl +
                        ", вес-" + metal.getIndexHeft() +
                        ", цена-" + metal.getPrice() + "]"
                , 1
                , false
                , costMetal);
    }

    public DoorEntity calculateColorDoor(ImageEntity doorColors) {

        double spaceColor = getSpace();//-S_ребер;?
        double costColor = (doorColors.getPricePaintingMeterOfSpace() * spaceColor) / 5;

        costList.addLine("Цвет : [S-" + spaceColor
                        + ", цена-" + doorColors.getPricePaintingMeterOfSpace(),
                2,
                false,
                (int) costColor);

        return this;

    }

    public DoorEntity calculateGlass() {

        List<PriceType> groups = Arrays.asList(PriceType.FOR_BUY, PriceType.FOR_SELL);
        String glassName = "Стекло: S-";

        if (doorGlass != null && doorGlass.exists()) {

            double glassSpace = doorGlass.getSpace();
            int cost = doorGlass.getCost(TypeOfFurniture.TYPE_GLASS, glassSpace);
            costList.addLine(glassName + glassSpace + ", " + doorGlass.getTypeDoorGlass().getName(),
                    groups, false, cost);

            int costMarkup = doorGlass.getGlassCost(template.getTypeDoorGlass(), doorGlass.getTypeDoorGlass().getId());
            if (costMarkup != 0) {
                costList.addLine("Наценка на стекло: " + doorGlass.getTypeDoorGlass().getName(),
                        groups, false, costMarkup);
            }

            if (doorGlass.getToning() != null) {

                cost = doorGlass.getCost(TypeOfFurniture.GLASS_PELLICLE, glassSpace);
                costList.addLine(glassName + glassSpace + ", " + doorGlass.getToning().getName(),
                        groups, false, cost);

                costMarkup = doorGlass.getGlassCost(template.getToning(), doorGlass.getToning().getId());
                if (costMarkup != 0) {
                    costList.addLine("Наценка на тонировку: " + doorGlass.getToning().getName(),
                            groups, false, costMarkup);
                }
            }

            if (doorGlass.getArmor() != null) {
                cost = doorGlass.getCost(TypeOfFurniture.ARMOR_GLASS_PELLICLE, glassSpace);
                costList.addLine(glassName + glassSpace + ", " + doorGlass.getArmor().getName(),
                        groups, false, cost);

                costMarkup = doorGlass.getGlassCost(template.getArmor(), doorGlass.getArmor().getId());
                if (costMarkup != 0) {
                    costList.addLine("Наценка на броню: " + doorGlass.getArmor().getName(),
                            groups, false, costMarkup);
                }
            }
        }

        if (isDoorFanlightGlass == 1) {
            //@TODO Salagaev set Glass
        }

        return this;
    }

    public DoorEntity createName() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(doorType.getNameForPrint());
        stringBuilder.append(" ");
        stringBuilder.append(widthDoor);
        stringBuilder.append(" X ");
        stringBuilder.append(heightDoor);
        stringBuilder.append(" (");
        stringBuilder.append(metal);
        stringBuilder.append(" мм) ");
        stringBuilder.append(getSideOpenAsRU());
        stringBuilder.append(" ");

        if (doorDesign != null) {
            stringBuilder.append(doorDesign.toString());
        }

        if (shieldKit != null && shieldKit.exists()) {
            stringBuilder.append(" - ");
            stringBuilder.append(shieldKit.toString());
        }

        name = stringBuilder.toString();
        return this;

    }

    public DoorEntity() {
        this.orders = new ArrayList<>();
        this.availableDoorClass = new ArrayList<>();
        this.doorGlass = new DoorGlass();
        this.costList = new CostList();
        this.furnitureKit = new FurnitureKit();
        this.setQuantity(1);
        doorLeaf = 1;
    }


    public DoorEntity calculateSalary(PayrollSettings paySet) {


        //constructorSalary
        //********************************************************
        costList.addLine("Конструктор:",
                3,
                false,
                (int) (paySet.getConstMap().get(TypeOfSalaryConst.CONSTRUCTOR_COST)
                        + paySet.getConstMap().get(TypeOfSalaryConst.MARKUP_CONSTRUCTOR)));

        //cuttingSalary
        //********************************************************
        costList.addLine("Резка:",
                3,
                false,
                (int) (this.sheets.size() * paySet.getConstMap().get(TypeOfSalaryConst.CUTTING_COST_PER_SHEET)));


        //bendingSalary
        //********************************************************
        if (paySet.getBendSetting().getBend() == 0) {
            logger.warn("ERROR calculateSalary  - Количество гибов = 0!");
        }
        double bendingCost = paySet.getConstMap().get(TypeOfSalaryConst.BENDING_COST);
        double cost = bendingCost * paySet.getBendSetting().getBend();
        costList.addLine("Гибка: [" + paySet.getBendSetting().getBend() + " Х " + bendingCost + " = " + cost,
                3,
                false,
                (int) cost);

        //guillotine
        //********************************************************
        if (paySet.getBendSetting().getGuillotine() == 0) {
            logger.warn("ERROR calculateSalary  - Количество резов гильотины = 0!");
        }
        cost = paySet.getBendSetting().getGuillotine() * paySet.getConstMap().get(TypeOfSalaryConst.GUILLOTINE_COST);
        costList.addLine("Гильотина: " + paySet.getBendSetting().getGuillotine()
                        + " Х " + paySet.getConstMap().get(TypeOfSalaryConst.GUILLOTINE_COST) + " = " + cost,
                3,
                false,
                (int) cost);


        //contact welding
        //********************************************************
        cost = 0;
        if (metal < 1.4) {
            if (paySet.getDoorType().getDoorLeaf() == 1) {
                cost = paySet.getSalarySetting().getContactWeldingForOneLeaf();
            } else if (paySet.getDoorType().getDoorLeaf() == 2) {
                cost = paySet.getSalarySetting().getContactWeldingForTwoLeaf();
            }
            costList.addLine("Контактная Сварка: " + cost,
                    3,
                    false,
                    (int) cost);

        }

        //welding
        //********************************************************
        weldingCost(paySet);

        //clean
        //*****************************************
        cleanCost(paySet);

        //Polymer coloring
        //*****************************************
        polymerColoringCost(paySet);

        //assembly
        assemblyCost(paySet);

        //packaging
        packagingCost(paySet);

        return this;
    }

    public DoorEntity setPriceOfDoorType(UserEntity user) {

        int priceForSell = costList.getCostByGroup(PriceType.FOR_SELL.getGroup());
        int priceForBuy = costList.getCostByGroup(PriceType.FOR_BUY.getGroup());

        setPrice(priceForBuy);
        setDiscountPrice(priceForBuy);
        setPriceWithMarkup(priceForSell);

        return this;
    }

    int getBasePrice(UserEntity user) {

        PriceGroups priceGroup = user.getPriceGroup();
        if (priceGroup == PriceGroups.RETAIL_PRICE) {
            return (int)
                    doorType.getRetailPrice();
        } else if (priceGroup == PriceGroups.WHOLESALE_PRICE) {
            return (int) doorType.getWholesalePriceFromStock1();
        } else if (priceGroup == PriceGroups.PRICE_OVER_1_MILLION_PER_MONTH) {
            return (int) doorType.getWholesalePriceFromStock2();
        } else if (priceGroup == PriceGroups.WHOLESALE_PRICE_ON_PREPAYMENT) {
            return (int) doorType.wholesalePriceFromOrder;
        }
        return 0;
    }

    public DoorEntity costToPrice() {

        double summMarkUp = 0;
        if (isDoorGlass == 1) {
            summMarkUp = doorType.getMarkUpGlassPackage() * costList.getTotalCost() / 100;
        } else {
            summMarkUp = doorType.getMarkUp() * costList.getTotalCost() / 100;
        }

        setPrice(costList.getTotalCost() + (int) summMarkUp);
        setDiscountPrice(price - ((int) (price * 0.25)));
        setPriceWithMarkup(discountPrice + ((int) (discountPrice * 1.25)));

        return this;

    }

    public DoorEntity calculateFurniture() {

        //TopLock
        if (furnitureKit.getTopLock() != null) {
            addCostFurnitureToCostList("Верхний замок", "Наценка верхний замок",
                    template.getTopLock(), furnitureKit.getTopLock());
        }

        if (furnitureKit.getTopInLockDecor() != null) {
            addCostFurnitureToCostList("Накладка верх. внутреняя", "Наценка накладка верх. внутреняя",
                    template.getTopInLockDecor(), furnitureKit.getTopInLockDecor());
        }

        if (furnitureKit.getTopOutLockDecor() != null) {
            addCostFurnitureToCostList("Накладка верх. внешняя", "Наценка накладка верх. внешняя: ",
                    template.getTopOutLockDecor(), furnitureKit.getTopOutLockDecor());
        }

        if (furnitureKit.getTopLockCylinder() != null) {
            addCostFurnitureToCostList("Цилиндр ", "Наценка цилиндр: ",
                    template.getLockCylinder(), furnitureKit.getTopLockCylinder());
        }

        //LowerLock
        if (furnitureKit.getLowerLock() != null) {
            addCostFurnitureToCostList("Нижний замок ", "Наценка нижний замок: ",
                    template.getLowerLock(), furnitureKit.getLowerLock());
        }

        if (furnitureKit.getLowerInLockDecor() != null) {
            addCostFurnitureToCostList("Накладка низ. внутренняя", "Наценка накладка низ. внутренняя: ",
                    template.getLowerInLockDecor(), furnitureKit.getLowerInLockDecor());
        }

        if (furnitureKit.getLowerOutLockDecor() != null) {
            addCostFurnitureToCostList("Накладка низ. внешняя", "Наценка накладка низ. внешняя: ",
                    template.getLowerOutLockDecor(), furnitureKit.getLowerOutLockDecor());
        }

        if (furnitureKit.getLowerLockCylinder() != null) {
            addCostFurnitureToCostList("Цилиндр ", "Наценка цилиндр: ",
                    template.getLockCylinder(), furnitureKit.getLowerLockCylinder());
        }

        // handle
        if (furnitureKit.getHandle() != null) {
            addCostFurnitureToCostList("Ручка ", "Наценка ручка: ",
                    template.getHandle(), furnitureKit.getHandle());
        }

        // Closer
        if (furnitureKit.getCloser() != null) {
            addCostFurnitureToCostList("Доводчик ", "Наценка доводчик: ",
                    template.getCloser(), furnitureKit.getCloser());
        }

        //endDoorLock
        if (furnitureKit.getEndDoorLock() != null) {
            addCostFurnitureToCostList("Торцевой шпингалет ", "Наценка торцевой шпингалет: ",
                    template.getEndDoorLock(), furnitureKit.getEndDoorLock());
        }

        return this;
    }

    private void addCostFurnitureToCostList(String name, String nameForChange,
                                            List<LimitationDoor> templateFurniture, DoorFurniture furniture) {
        costList.addLine(name, PriceType.FOR_SELL, false, (int) furniture.getPrice());
        costList.addLine(name, PriceType.FOR_BUY, false, (int) furniture.getPrice());

        int costMarkup = furnitureKit.getFurnitureDefaultCost(templateFurniture, furniture.getId());

        if (costMarkup != 0) {
            costList.addLine(nameForChange, PriceType.FOR_SELL, false, costMarkup);
            costList.addLine(nameForChange, PriceType.FOR_BUY, false, costMarkup);
        }
    }


    private void weldingCost(PayrollSettings paySet) {
        //main
        double cost = 0;
        double spaceDoor = ((double) (widthDoor * heightDoor)) / 1000000;
        if (paySet.getDoorType().getDoorLeaf() == 1) {
            if ((widthDoor <= paySet.getConstMap().get(TypeOfSalaryConst.WIDTH_LIMIT_ONE_LEAF))
                    && (heightDoor <= paySet.getConstMap().get(TypeOfSalaryConst.HEIGHT_LIMIT_ONE_LEAF))) {

                if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                    cost = paySet.getSalarySetting().getWeldingForOneLeafHot();
                } else if (MDF == 1) {
                    cost = paySet.getSalarySetting().getWeldingForOneLeafMDF();
                } else {
                    cost = paySet.getSalarySetting().getWeldingForOneLeaf();
                }
            } else {
                cost = spaceDoor * paySet.getConstMap().get(TypeOfSalaryConst.COST_OVER_LIMIT_ONE_LEAF);
            }
        } else if (paySet.getDoorType().getDoorLeaf() == 2) {
            if ((widthDoor <= paySet.getConstMap().get(TypeOfSalaryConst.WIDTH_LIMIT_TWO_LEAF))
                    && (heightDoor <= paySet.getConstMap().get(TypeOfSalaryConst.HEIGHT_LIMIT_TWO_LEAF))) {

                if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                    cost = paySet.getSalarySetting().getWeldingForTwoLeafHot();
                } else if (MDF == 1) {
                    cost = paySet.getSalarySetting().getWeldingForTwoLeafMDF();
                } else {
                    cost = paySet.getSalarySetting().getWeldingForTwoLeaf();
                }

            } else {
                cost = spaceDoor * paySet.getConstMap().get(TypeOfSalaryConst.COST_OVER_LIMIT_TWO_LEAF);
            }
        }
        costList.addLine("Сварка: " + cost,
                3,
                false,
                (int) cost);

        cost = 0;
        if (doorFanlightHeight > 0) {
            cost = paySet.getConstMap().get(TypeOfSalaryConst.COST_DOOR_FANLIGHT);
            costList.addLine("Фрамуга: " + cost,
                    3,
                    false,
                    (int) cost);
        }

        cost = 0;
        if (additionallyHingeMain == 1 || additionallyHingeNotMain == 1) {

            if (additionallyHingeMain == 1) {
                cost += paySet.getConstMap().get(TypeOfSalaryConst.COST_WELDING_ADDITIONAL_DOOR_HINGE);
            }
            if (additionallyHingeNotMain == 1) {
                cost += paySet.getConstMap().get(TypeOfSalaryConst.COST_WELDING_ADDITIONAL_DOOR_HINGE);
            }

            costList.addLine("Доп.петля - " + cost,
                    3,
                    false,
                    (int) cost);
        }

        //Amplifier Closer
        cost = 0;
        if (amplifierCloser == 1) {
            cost = paySet.getConstMap().get(TypeOfSalaryConst.COST_AMPLIFIER_CLOSER);
            costList.addLine("усилитель доводчика - " + cost,
                    3,
                    false,
                    (int) cost);
        }

        //Glass
        cost = 0;
        if (doorGlass.exists()) {
            cost = paySet.getConstMap().get(TypeOfSalaryConst.WELDING_FOR_GLASS);
            costList.addLine("Стеклопакет - " + cost,
                    3,
                    false,
                    (int) cost);
        }

        cost = 0;
        if (thirdSealingLine > 0) {
            cost = paySet.getConstMap().get(TypeOfSalaryConst.COST_THIRD_SEALING_LINE);
            costList.addLine("Доп. линия уплотнения ",
                    3,
                    false,
                    (int) cost);
        }
    }

    private void cleanCost(PayrollSettings paySet) {

        if (paySet.getDoorType().getDoorLeaf() == 1) {

            costList.addLine(" зачистка, базовая ставка ", 3, false,
                    paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_ONE_LEAF).intValue());

            if (paySet.getDoorColors().getSmooth() == 1) {
                costList.addLine(" зачистка, гладкая краска ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_SMOOTH_PAINT_ONE_LEAF).intValue());
            }

            if (additionallyHingeMain == 1) {
                costList.addLine(" зачистка, доп петля ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_ADDITIONALLY_HINGE_ONE_LEAF).intValue());
            }

            if (thirdSealingLine > 0) {
                costList.addLine(" зачистка, Доп. линия уплотнения ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_THIRD_SEALING_LINE_ONE_LEAF).intValue());
            }

            if (thirdSealingLine > 0) {
                costList.addLine(" зачистка, Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_HOT_ONE_LEAF).intValue());
            }


        } else if (paySet.getDoorType().getDoorLeaf() == 2) {

            costList.addLine(" зачистка, базовая ставка ", 3, false,
                    paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_TWO_LEAF).intValue());

            if (paySet.getDoorColors().getSmooth() == 1) {
                costList.addLine(" зачистка, гладкая краска ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_SMOOTH_PAINT_TWO_LEAF).intValue());
            }

            if (additionallyHingeMain == 1) {
                costList.addLine(" зачистка, доп петля ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_ADDITIONALLY_HINGE_TWO_LEAF).intValue());
            }

            if (thirdSealingLine > 0) {
                costList.addLine(" зачистка, Доп. линия уплотнения ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_THIRD_SEALING_LINE_TWO_LEAF).intValue());
            }

            if (thirdSealingLine > 0) {
                costList.addLine(" зачистка, Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_HOT_TWO_LEAF).intValue());
            }

        }
    }

    private void polymerColoringCost(PayrollSettings paySet) {

        if (paySet.getDoorType().getDoorLeaf() == 1) {
            if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                costList.addLine(" Полимерка : Створок-1шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_HOT_ONE_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Полимерка : Створок-1шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_MDF_ONE_LEAF).intValue());
            } else {
                costList.addLine(" Полимерка : Створок-1шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_ONE_LEAF).intValue());
            }
        } else if (paySet.getDoorType().getDoorLeaf() == 2) {
            if (paySet.getDoorType().getDoorClass().getHot() == 2) {
                costList.addLine(" Полимерка : Створок-2шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_HOT_TWO_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Полимерка : Створок-2шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_MDF_TWO_LEAF).intValue());
            } else {
                costList.addLine(" Полимерка : Створок-2шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_POLYMER_COLOR_TWO_LEAF).intValue());
            }
        }
    }

    private void assemblyCost(PayrollSettings paySet) {

        if (paySet.getDoorType().getDoorLeaf() == 1) {

            if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                costList.addLine(" Сборка : Створок-1шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_HOT_ONE_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Сборка : Створок-1шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_MDF_ONE_LEAF).intValue());
            } else {
                costList.addLine(" Сборка : Створок-1шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_ONE_LEAF).intValue());
            }
        } else if (paySet.getDoorType().getDoorLeaf() == 2) {

            if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                costList.addLine(" Сборка : Створок-2шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_HOT_TWO_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Сборка : Створок-2шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_MDF_TWO_LEAF).intValue());
            } else {
                costList.addLine(" Сборка : Створок-2шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_TWO_LEAF).intValue());
            }
        }

        if (doorGlass.exists()) {
            costList.addLine(" Сборка : стеклопакет ", 3, false,
                    paySet.getConstMap().get(TypeOfSalaryConst.COST_ASSEMBLY_GLASS).intValue());
        }
    }

    private void packagingCost(PayrollSettings paySet) {

        if (paySet.getDoorType().getDoorLeaf() == 1) {

            if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                costList.addLine(" Упаковка : Створок-1шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_HOT_ONE_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Упаковка : Створок-1шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_MDF_ONE_LEAF).intValue());
            } else {
                costList.addLine(" Упаковка : Створок-1шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_ONE_LEAF).intValue());
            }
        } else if (paySet.getDoorType().getDoorLeaf() == 2) {

            if (paySet.getDoorType().getDoorClass().getHot() == 1) {
                costList.addLine(" Упаковка : Створок-2шт,Термо ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_HOT_TWO_LEAF).intValue());
            } else if (MDF == 1) {
                costList.addLine(" Упаковка : Створок-2шт,МД ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_MDF_TWO_LEAF).intValue());
            } else {
                costList.addLine(" Упаковка : Створок-2шт,ММ ", 3, false,
                        paySet.getConstMap().get(TypeOfSalaryConst.COST_PACKAGING_TWO_LEAF).intValue());
            }
        }


    }

    public void addAvailableDoorClass(DoorClass doorClass) {
        this.availableDoorClass.add(doorClass);
    }

    public void addAvailableDoorClass(List<DoorClass> doorClassList) {
        for (DoorClass doorClass : doorClassList) {
            addAvailableDoorClass(doorClass.clearNonSerializingFields());
        }
    }

    public int getSealingLine() {
        if (sealingLine == 0 && thirdSealingLine != 0) {
            sealingLine = 3;
        } else {
            sealingLine = 2;
        }
        return sealingLine;
    }

    public String getTrimName() {
        String topTrim = String.valueOf(getTopDoorTrimSize());
        String lefTrim = String.valueOf(getLeftDoorTrimSize());
        String rightTrim = String.valueOf(getRightDoorTrimSize());
        return "лев: " + lefTrim + " верх: " + topTrim + " прав: " + rightTrim;
    }

    public DoorEntity addPriceToCostList(int discount, int retailMargin, PriceGroups priceGroup) {

        costList = new CostList();
        PriceType type = PriceType.FOR_SELL;
        int priceForSell = (int) doorType.getPrice();
        int withMarkup = ((priceForSell * retailMargin) / 100);
        costList.addLine("Розничная цена", type, false, priceForSell + withMarkup);


        type = PriceType.FOR_BUY;
        int priceForBuy = (int) doorType.getPrice(priceGroup);
        costList.addLine("Диллерская цена", type, false, priceForBuy);
        int discountCost = ((priceForBuy * discount) / 100);
        costList.addLine("Скидка", type, false, -discountCost);

        return this;
    }

    public DoorEntity costOfChangesAtTemplate(int marginForChange) {

        addCostResizing(marginForChange);
        addCostForColorChange(marginForChange);
        addCostForShieldKitChange(marginForChange);
        addCostForFurnitureKitChange(marginForChange);

        return this;
    }

    private DoorEntity addCostResizing(int margin) {

        List<Integer> defaultWidthList = template.getStandardSize().stream()
                .map(LimitationDoor::getStartRestriction)
                .map(Double::intValue)
                .collect(Collectors.toList());
        int defaultWidth = template.getWidthDoor().stream().findFirst()
                .orElse(new LimitationDoor()).getDefaultValue();
        defaultWidthList.add(defaultWidth);

        List<LineCostList> listWidth = new ArrayList<>();

        if (!defaultWidthList.contains(widthDoor)) {
            List<LimitationDoor> sizeCostWidth = template.getSizeCostWidth();
            listWidth = getCostResizing(sizeCostWidth, widthDoor, defaultWidth, margin, false);
        }

        List<LineCostList> listHeight = new ArrayList<>();
        int heightSize = heightDoor;
        int defaultHeight = template.getHeightDoor().stream().findFirst().orElse(new LimitationDoor()).getDefaultValue();

        if (heightSize != defaultHeight) {
            List<LimitationDoor> sizeCostHeight = template.getSizeCostHeight();
            final boolean ALREADY_COUNTED = !listWidth.isEmpty();
            listWidth = getCostResizing(sizeCostHeight, heightSize, defaultHeight, margin, ALREADY_COUNTED);
        }

        costList.addAllLine(listWidth);
        costList.addAllLine(listHeight);

        return this;
    }

    private List<LineCostList> getCostResizing(List<LimitationDoor> list,
                                               int doorSize,
                                               int defaultSize,
                                               int margin,
                                               boolean isCounted) {


        List<LineCostList> resultForSell = list.stream()
                .map(lim -> toMarkup(lim, doorSize, defaultSize, isCounted, margin, PriceType.FOR_SELL.getGroup()))
                .filter(line -> line.getCost() > 0)
                .collect(Collectors.toList());

        List<LineCostList> resultForBuy = list.stream()
                .map(lim -> toMarkup(lim, doorSize, defaultSize, isCounted, 0, PriceType.FOR_BUY.getGroup()))
                .filter(line -> line.getCost() > 0)
                .collect(Collectors.toList());

        resultForSell.addAll(resultForBuy);
        return resultForSell;


    }

    private DoorEntity addCostForColorChange(int margin) {

        LimitationDoor defaultColor = TemplateService.getDefaultLine(template.getColors());

        if (!defaultColor.getFirstItem().equals(doorColor)) {
            LimitationDoor currentColorLim = template.getColors().stream()
                    .filter(line -> line.getFirstItem().equals(doorColor))
                    .findFirst()
                    .orElse(new LimitationDoor());

            int costMargin = (currentColorLim.getCost() * margin) / 100;

            costList.addLine(
                    LimitationDoor.getDescription(currentColorLim)
                    , PriceType.FOR_BUY, false, currentColorLim.getCost());
            costList.addLine(
                    LimitationDoor.getDescription(currentColorLim)
                    , PriceType.FOR_SELL, false, currentColorLim.getCost() + costMargin);
        }

        return this;
    }

    private DoorEntity addCostForShieldKitChange(int margin) {

        if (shieldKit == null) {
            return this;
        }
        ShieldKit kit = shieldKit;

        ImageEntity shieldColor = kit.getShieldColor();
        if (shieldColor != null) {

            LimitationDoor defaultShieldColor = TemplateService.getDefaultLine(
                    template.getShieldColor()
            );

            if (defaultShieldColor.getItemId() != shieldColor.getId()) {

                LimitationDoor currentColorLim = TemplateService.getLineByItemId(
                        template.getShieldColor(), shieldColor.getId()
                );

                int costMargin = (currentColorLim.getCost() * margin) / 100;
                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        PriceType.FOR_SELL,
                        false,
                        currentColorLim.getCost() + costMargin);
                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        PriceType.FOR_BUY,
                        false,
                        currentColorLim.getCost());
            }
        }

        ImageEntity shieldDesign = kit.getShieldDesign();
        if (shieldDesign != null) {

            LimitationDoor defaultShieldDesign = TemplateService.getDefaultLine(
                    template.getShieldDesign()
            );

            if (defaultShieldDesign.getItemId() != shieldDesign.getId()) {

                LimitationDoor currentDesignLim = TemplateService.getLineByItemId(
                        template.getShieldDesign(), shieldDesign.getId()
                );

                int costMargin = (currentDesignLim.getCost() * margin) / 100;
                costList.addLine(
                        LimitationDoor.getDescription(currentDesignLim),
                        PriceType.FOR_SELL,
                        false,
                        currentDesignLim.getCost() + costMargin);
                costList.addLine(
                        LimitationDoor.getDescription(currentDesignLim),
                        PriceType.FOR_BUY,
                        false,
                        currentDesignLim.getCost());
            }
        }

        return this;
    }

    private DoorEntity addCostForFurnitureKitChange(int margin) {

        FurnitureKit kit = furnitureKit;

        addCostForFurniture(kit.getTopLock(), template.getTopLock(), margin);
        addCostForFurniture(kit.getLowerLock(), template.getLowerLock(), margin);
        //...

        return this;

    }

    private LineCostList toMarkup(LimitationDoor lim,
                                  int size,
                                  int defaultSize,
                                  boolean alreadyCounted,
                                  int margin,
                                  int priceGroup) {


        int costStep = (int) lim.getStartRestriction();
        int step = (int) lim.getStep();
        int costForChange = alreadyCounted ? 0 : lim.getCost();

        if (costStep > 0 && step > 0 && (costForChange > 0 || alreadyCounted)) {

            int costDiff = ((size - defaultSize) / step) * costStep;

            int marginCostDiff = (costDiff * margin) / 100;
            int marginCostForChange = (costForChange * margin) / 100;

            return new LineCostList(lim.getTypeSettings().getName() + " " + size +
                    ", Цена за изменение: " + (costForChange + marginCostForChange) +
                    ", шаг цены: " + (costDiff + marginCostDiff),
                    priceGroup,
                    false,
                    (costForChange + marginCostForChange) + (costDiff + marginCostDiff));
        }

        return new LineCostList();
    }

    private DoorEntity addCostForFurniture(DoorFurniture furniture,
                                           List<LimitationDoor> listOfFurnitureLimit,
                                           int margin) {

        if (furniture != null) {

            LimitationDoor defaultShieldColor = TemplateService.getDefaultLine(
                    listOfFurnitureLimit
            );

            if (defaultShieldColor.getItemId() != furniture.getId()) {

                LimitationDoor currentColorLim = TemplateService.getLineByItemId(
                        listOfFurnitureLimit, furniture.getId()
                );

                int cotsMargin = (currentColorLim.getCost() * margin) / 100;
                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        PriceType.FOR_BUY,
                        false,
                        currentColorLim.getCost());
                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        PriceType.FOR_SELL,
                        false,
                        currentColorLim.getCost() + cotsMargin);
            }
        }

        return this;
    }

    public DoorEntity addRetailMarginToCostList(int retailMargin) {

        int totalCost = costList.getTotalCost();
        int withMarkup = ((totalCost * retailMargin) / 100);

        costList.addLine("Цена с наценкой", 500, false, withMarkup);

        return this;
    }

    public DoorEntity calculateStainlessSteelDoorstep() {

        if (this.stainlessSteelDoorstep == 1) {
            int cost = 0;
            List<LimitationDoor> tab = template.getStainlessSteelDoorstep();
            for (LimitationDoor lim : tab) {
                if (lim.getStartRestriction() == 1) {
                    cost = lim.getCost();
                    costList.addLine("Порог из нержавейки", PriceType.FOR_BUY, false, cost);
                    costList.addLine("Порог из нержавейки", PriceType.FOR_SELL, false, cost);
                    return this;
                }
            }
        }
        return this;
    }

    public boolean isNew() {
        return id == 0;
    }

    public DoorPrintView getPrintView(DoorOrder order) {
        return new DoorPrintView(this, order);
    }

    public String doorstepView() {
        if (stainlessSteelDoorstep == 1) {
            return "Шлифованный нержавеющий";
        }
        return "В цвет двери";
    }

    public static List<String> options() {
        List<String> list = new ArrayList<>();

        list.add("WIDTH - ширина двери,мм");
        list.add("HEIGHT - высота двери,мм");
        list.add("LEAF - количество створок,[1,2]");
        list.add("DEEPNESS - глубина двери,мм");
        list.add("THICKNESS - толщина полотна,мм");
        list.add("STEP - порог,[0,1]");
        list.add("STAINLESS_STEEL - порог из нержавейки,[0,1]");

        return list;
    }

    public String getOutShieldName() {
        if (doorDesign != null) {
            return doorDesign.getOutShieldName();
        } else {
            logger.warn("doorId: '{}' doorDesign is null", id);
            return "";
        }
    }

    public static boolean isNotNew(int doorId) {
        return doorId > 0;
    }

    public String getSideOpenAsRU() {

        if (SideDoorOpen.RIGHT.toString().equals(sideDoorOpen)) {
            return SideDoorOpen.RIGHT.ru();
        } else if (SideDoorOpen.LEFT.toString().equals(sideDoorOpen)) {
            return SideDoorOpen.LEFT.ru();
        }
        return sideDoorOpen;
    }
}
