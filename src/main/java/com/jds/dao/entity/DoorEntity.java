package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.*;
import com.jds.model.cutting.DoorPart;
import com.jds.model.cutting.Sheet;
import com.jds.model.enumClasses.PriceGroups;
import com.jds.model.enumClasses.TypeOfFurniture;
import com.jds.model.enumClasses.TypeOfSalaryConst;
import com.jds.service.TemplateService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Door")
@Getter
@Setter
public class DoorEntity implements SerializingFields {

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
            double price = setting.getRawMaterials().getPrice();
            costMetal = (int) (value * price);
            costList.addLine("материаллы : " + setting.getRawMaterials().getName() + ", " + value + ", " + price + " руб."
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
            //furnitureKit = null;
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

        if (doorGlass != null && doorGlass.exists()) {

            double glassSpace = doorGlass.getSpace();
            int cost = doorGlass.getCost(TypeOfFurniture.TYPE_GLASS, glassSpace);
            costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getTypeDoorGlass().getName(),
                    200, false, cost);

            int costMarkup = doorGlass.getGlassCost(template.getTypeDoorGlass(), doorGlass.getTypeDoorGlass().getId());
            if (costMarkup != 0) {
                costList.addLine("Наценка на стекло: " + doorGlass.getTypeDoorGlass().getName(),
                        200, false, costMarkup);
            }

            if (doorGlass.getToning() != null) {

                cost = doorGlass.getCost(TypeOfFurniture.GLASS_PELLICLE, glassSpace);
                costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getToning().getName(),
                        200, false, cost);

                costMarkup = doorGlass.getGlassCost(template.getToning(), doorGlass.getToning().getId());
                if (costMarkup != 0) {
                    costList.addLine("Наценка на тонировку: " + doorGlass.getToning().getName(),
                            200, false, costMarkup);
                }
            }

            if (doorGlass.getArmor() != null) {
                cost = doorGlass.getCost(TypeOfFurniture.ARMOR_GLASS_PELLICLE, glassSpace);
                costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getArmor().getName(),
                        200, false, cost);

                costMarkup = doorGlass.getGlassCost(template.getArmor(), doorGlass.getArmor().getId());
                if (costMarkup != 0) {
                    costList.addLine("Наценка на броню: " + doorGlass.getArmor().getName(),
                            200, false, costMarkup);
                }
            }
        }

        if (isDoorFanlightGlass == 1) {

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
        stringBuilder.append(sideDoorOpen);
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
        this.orders = new ArrayList<DoorOrder>();
        this.availableDoorClass = new ArrayList<DoorClass>();
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
            System.out.println("ERROR calculateSalary  - Количество гибов = 0!");
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
            System.out.println("ERROR calculateSalary  - Количество резов гильотины = 0!");
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

        int basePrice = getBasePrice(user);
        int costForChange = costList.getCostByGroup(200);
        int discountPrice = costList.getCostByGroup(100);
        int retailMergin = costList.getCostByGroup(500);

        setPrice(basePrice + costForChange);
        setDiscountPrice(costForChange + discountPrice);
        setPriceWithMarkup(retailMergin + getDiscountPrice());

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
            costList.addLine("Фурнитура: верхний замок ",
                    200,
                    false,
                    (int) furnitureKit.getTopLock().getPrice());


            int costMarkup = furnitureKit.getFurnitureCost(template.getTopLock(), furnitureKit.getTopLock().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка верхний замок: ",
                        200, false, costMarkup);
            }

        }
        if (furnitureKit.getTopInLockDecor() != null) {
            costList.addLine("Фурнитура: накладка верх. внутреняя ",
                    200,
                    false,
                    (int) furnitureKit.getTopInLockDecor().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getTopInLockDecor(), furnitureKit.getTopInLockDecor().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка накладка верх. внутреняя: ",
                        200, false, costMarkup);
            }
        }
        if (furnitureKit.getTopOutLockDecor() != null) {
            costList.addLine("Фурнитура: накладка верх. внешняя ",
                    200,
                    false,
                    (int) furnitureKit.getTopOutLockDecor().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getTopOutLockDecor(), furnitureKit.getTopOutLockDecor().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка накладка верх. внешняя: ",
                        200, false, costMarkup);
            }
        }

        if (furnitureKit.getTopLockCylinder() != null) {
            costList.addLine("Фурнитура: цилиндр ",
                    200,
                    false,
                    (int) furnitureKit.getTopLockCylinder().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getLockCylinder(), furnitureKit.getTopLockCylinder().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка цилиндр: ",
                        200, false, costMarkup);
            }
        }

        //LowerLock
        if (furnitureKit.getLowerLock() != null) {
            costList.addLine("Фурнитура: нижний замок ",
                    200,
                    false,
                    (int) furnitureKit.getLowerLock().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getLowerLock(), furnitureKit.getLowerLock().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка нижний замок: ",
                        200, false, costMarkup);
            }
        }
        if (furnitureKit.getLowerInLockDecor() != null) {
            costList.addLine("Фурнитура: накладка низ. внутренняя",
                    200,
                    false,
                    (int) furnitureKit.getLowerInLockDecor().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getLowerInLockDecor(), furnitureKit.getLowerInLockDecor().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка накладка низ. внутренняя: ",
                        200, false, costMarkup);
            }
        }
        if (furnitureKit.getLowerOutLockDecor() != null) {
            costList.addLine("Фурнитура: накладка низ. внешняя",
                    200,
                    false,
                    (int) furnitureKit.getLowerOutLockDecor().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getLowerOutLockDecor(), furnitureKit.getLowerOutLockDecor().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка накладка низ. внешняя: ",
                        200, false, costMarkup);
            }
        }
        if (furnitureKit.getLowerLockCylinder() != null) {
            costList.addLine("Фурнитура: цилиндр ",
                    200,
                    false,
                    (int) furnitureKit.getLowerLockCylinder().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getLockCylinder(), furnitureKit.getLowerLockCylinder().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка цилиндр: ",
                        200, false, costMarkup);
            }
        }


        // handle
        if (furnitureKit.getHandle() != null) {
            costList.addLine("Фурнитура: ручка ",
                    200,
                    false,
                    (int) furnitureKit.getHandle().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getHandle(), furnitureKit.getHandle().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка ручка: ",
                        200, false, costMarkup);
            }
        }
        // Closer
        if (furnitureKit.getCloser() != null) {
            costList.addLine("Фурнитура: доводчик ",
                    200,
                    false,
                    (int) furnitureKit.getCloser().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getCloser(), furnitureKit.getCloser().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка доводчик: ",
                        200, false, costMarkup);
            }
        }
        //endDoorLock
        if (furnitureKit.getEndDoorLock() != null) {
            costList.addLine("Фурнитура: торцевой шпингалет ",
                    200,
                    false,
                    (int) furnitureKit.getEndDoorLock().getPrice());

            int costMarkup = furnitureKit.getFurnitureCost(template.getEndDoorLock(), furnitureKit.getEndDoorLock().getId());

            if (costMarkup != 0) {
                costList.addLine("Наценка торцевой шпингалет: ",
                        200, false, costMarkup);
            }
        }

        return this;
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

        //doorFanlight
        cost = 0;
        if (doorFanlightHeight > 0) {
            cost = paySet.getConstMap().get(TypeOfSalaryConst.COST_DOOR_FANLIGHT);
            costList.addLine("Фрамуга: " + cost,
                    3,
                    false,
                    (int) cost);
        }

        //handle
        //ConstMap.get(TypeOfSalaryConst.COST_WELDED_HANDLE);


        //door hinge
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

        //like L5
        //ConstMap.get(TypeOfSalaryConst.SQUARES_MODEL_L5;

        //thirdSealingLine
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

            //if(){
            //    costList.addLine(" зачистка, L5 ",3,false,
            //            ConstMap.get(TypeOfSalaryConst.COST_CLEAN_SQUARES_L5_PAINT_ONE_LEAF).intValue());
            //}

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

            //if(){
            //    costList.addLine(" зачистка, L5 ",3,false,
            //            paySet.getConstMap().get(TypeOfSalaryConst.COST_CLEAN_SQUARES_L5_PAINT_TWO_LEAF).intValue());
            //}

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

    public DoorEntity addPriceToCostList(int discount, PriceGroups priceGroup) {

        costList = new CostList();
        int retailPrice = (int) doorType.getPrice(priceGroup);

        costList.addLine("Розничная цена",
                100,
                false,
                (int) retailPrice);

        int discountCost = (int) ((retailPrice * discount) / 100);

        costList.addLine("Скидка",
                100,
                false,
                (int) -discountCost);

        return this;
    }

    public DoorEntity costOfChangesAtTemplate() {

        addCostResizing();
        addCostForColorChange();
        addCostForShieldKitChange();
        addCostForFurnitureKitChange();

        return this;
    }

    private DoorEntity addCostResizing() {

        int defaultWidth = template.getWidthDoor().stream().findFirst().orElse(new LimitationDoor()).getDefaultValue();
        List<LineCostList> listWidth = new ArrayList<>();

        if (widthDoor != defaultWidth) {
            List<LimitationDoor> sizeCostWidth = template.getSizeCostWidth();
            listWidth = sizeCostWidth.stream()
                    .map((lim) -> toMarkup(lim, widthDoor, defaultWidth, false))
                    .filter(line -> line.getCost() > 0)
                    .collect(Collectors.toList());
        }

        List<LineCostList> listHeight = new ArrayList<>();
        int Heightsize = heightDoor;
        int defaultHeight = template.getHeightDoor().stream().findFirst().orElse(new LimitationDoor()).getDefaultValue();

        if (Heightsize != defaultHeight) {
            List<LimitationDoor> sizeCostHeight = template.getSizeCostHeight();
            final boolean ALREADY_COUNTED = !listWidth.isEmpty();
            listHeight = sizeCostHeight.stream()
                    .map((lim) -> toMarkup(lim, Heightsize, defaultHeight, ALREADY_COUNTED))
                    .filter(line -> line.getCost() > 0)
                    .collect(Collectors.toList());
        }

        costList.addAllLine(listWidth);
        costList.addAllLine(listHeight);

        return this;
    }

    private DoorEntity addCostForColorChange() {

        LimitationDoor defaultColor = TemplateService.getDefaultLine(template.getColors());

        if (!defaultColor.getFirstItem().equals(doorColor)) {
            LimitationDoor currentColorLim = template.getColors().stream()
                    .filter(line -> line.getFirstItem().equals(doorColor))
                    .findFirst()
                    .orElse(new LimitationDoor());

            costList.addLine(
                    LimitationDoor.getDescription(currentColorLim),
                    200,
                    false,
                    currentColorLim.getCost());
        }

        return this;
    }

    private DoorEntity addCostForShieldKitChange() {

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

                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        200,
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

                costList.addLine(
                        LimitationDoor.getDescription(currentDesignLim),
                        200,
                        false,
                        currentDesignLim.getCost());
            }
        }

        return this;
    }

    private DoorEntity addCostForFurnitureKitChange() {

        FurnitureKit kit = furnitureKit;

        addCostForFurniture(kit.getTopLock(), template.getTopLock());
        addCostForFurniture(kit.getLowerLock(), template.getLowerLock());
        //...

        return this;

    }

    private LineCostList toMarkup(LimitationDoor lim, int size, int defaultSize, boolean alreadyCounted) {


        int costStep = (int) lim.getStartRestriction();
        int step = (int) lim.getStep();
        int costForChange = alreadyCounted ? 0 : lim.getCost();

        if (costStep > 0 && step > 0 && (costForChange > 0 || alreadyCounted)) {

            int costDiff = ((size - defaultSize) / step) * costStep;

            return new LineCostList(lim.getTypeSettings().getName() + " " + size +
                    ", Цена за изменение: " + costForChange +
                    ", шаг цены: " + costDiff,
                    200,
                    false,
                    costForChange + costDiff);
        }

        return new LineCostList();
    }

    private DoorEntity addCostForFurniture(DoorFurniture furniture,
                                           List<LimitationDoor> listOfFurniturLimit) {

        if (furniture != null) {

            LimitationDoor defaultShieldColor = TemplateService.getDefaultLine(
                    listOfFurniturLimit
            );

            if (defaultShieldColor.getItemId() != furniture.getId()) {

                LimitationDoor currentColorLim = TemplateService.getLineByItemId(
                        listOfFurniturLimit, furniture.getId()
                );

                costList.addLine(
                        LimitationDoor.getDescription(currentColorLim),
                        200,
                        false,
                        currentColorLim.getCost());
            }
        }

        return this;
    }

    public DoorEntity addRetailMarginToCostList(int retailMargin) {

        int discountPrice = costList.getTotalCost();
        int priceWithMarkup = (int) ((discountPrice * retailMargin) / 100);

        costList.addLine("Цена с наценкой",
                500,
                false,
                (int) priceWithMarkup);

        return this;
    }

    public DoorEntity calculateStainlessSteelDoorstep() {

        if (this.stainlessSteelDoorstep == 1) {
            int cost = 0;
            List<LimitationDoor> tab = template.getStainlessSteelDoorstep();
            for (LimitationDoor lim : tab) {
                if (lim.getStartRestriction() == 1) {
                    cost = lim.getCost();
                    costList.addLine("Порог из нержавейки", 200, false, cost);
                    return this;
                }
            }
        }
        return this;
    }

    public boolean isNew() {
        if (id == 0) {
            return true;
        }
        return false;
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
            logger.warn(String.format("doorId: %s doorDesign is null", id));
            return "";
        }
    }

    public static boolean isNotNew(int doorId) {
        return doorId > 0;
    }
}
