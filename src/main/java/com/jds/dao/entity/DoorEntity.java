package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.modelEnum.TypeOfFurniture;
import com.jds.model.modelEnum.TypeOfSalaryConst;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Door")
public class DoorEntity implements SerializingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @ManyToOne()
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
    private int innerDoorOpen;

    //doorstep
    @Column(name = "doorstep")
    private int doorstep;

    @Column(name = "stainlessSteelDoorstep")
    private int stainlessSteelDoorstep;

    //Trim
    @Column(name = "topDoorTrim")
    private int topDoorTrim;

    @Column(name = "leftDoorTrim")
    private int leftDoorTrim;

    @Column(name = "rightDoorTrim")
    private int rightDoorTrim;

    //price
    @Column(name = "price")
    private int price;

    @Column(name = "discountPrice")
    private int discountPrice;

    @Column(name = "priceWithMarkup")
    private int priceWithMarkup;

    @Column(name = "doorColor")
    private String doorColor;

    @JsonIgnore
    @ManyToMany(mappedBy = "doors", fetch = FetchType.LAZY)
    private List<DoorsОrder> orders;

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
            furnitureKit = null;
        }
        if (shieldKit != null && !shieldKit.exists()) {
            shieldKit = null;
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

        if (doorGlass.exists()) {

            double glassSpace = doorGlass.getSpace();
            int cost = doorGlass.getCost(TypeOfFurniture.TYPE_GLASS, glassSpace);
            costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getTypeDoorGlass().getName(),
                    5, false, cost);

            if (doorGlass.getToning() != null) {
                cost = doorGlass.getCost(TypeOfFurniture.GLASS_PELLICLE, glassSpace);
                costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getToning().getName(),
                        5, false, cost);
            }

            if (doorGlass.getArmor() != null) {
                cost = doorGlass.getCost(TypeOfFurniture.ARMOR_GLASS_PELLICLE, glassSpace);
                costList.addLine("Стекло: S-" + glassSpace + ", " + doorGlass.getArmor().getName(),
                        5, false, cost);
            }


        }


        if (isDoorFanlightGlass == 1) {

        }

        return this;
    }

    public int getDoorLeaf() {
        return doorLeaf;
    }

    public void setDoorLeaf(int doorLeaf) {
        this.doorLeaf = doorLeaf;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public CostList getCostList() {
        return costList;
    }

    public void setCostList(CostList costList) {
        this.costList = costList;
    }

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

    public DoorEntity createName() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(doorType.getNameForPrint());
        stringBuilder.append(" ");
        stringBuilder.append(widthDoor + " X " + heightDoor);
        stringBuilder.append(" (" + metal + " мм) " + sideDoorOpen + " " + doorColor);
        name = stringBuilder.toString();
        return this;

    }

    public DoorEntity() {
        this.orders = new ArrayList<DoorsОrder>();
        this.availableDoorClass = new ArrayList<DoorClass>();
        this.doorGlass = new DoorGlass();
        this.costList = new CostList();
        this.furnitureKit = new FurnitureKit();
        doorLeaf = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public int getWidthDoor() {
        return widthDoor;
    }

    public void setWidthDoor(int widthDoor) {
        this.widthDoor = widthDoor;
    }

    public int getHeightDoor() {
        return heightDoor;
    }

    public void setHeightDoor(int heightDoor) {
        this.heightDoor = heightDoor;
    }

    public int getActiveDoorLeafWidth() {
        return activeDoorLeafWidth;
    }

    public void setActiveDoorLeafWidth(int activeDoorLeafWidth) {
        this.activeDoorLeafWidth = activeDoorLeafWidth;
    }

    public int getDoorFanlightHeight() {
        return doorFanlightHeight;
    }

    public void setDoorFanlightHeight(int doorFanlightheight) {
        this.doorFanlightHeight = doorFanlightheight;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }

    public int getDeepnessDoor() {
        return deepnessDoor;
    }

    public void setDeepnessDoor(int deepnessDoor) {
        this.deepnessDoor = deepnessDoor;
    }

    public int getThicknessDoorLeaf() {
        return thicknessDoorLeaf;
    }

    public void setThicknessDoorLeaf(int thicknessDoorLeaf) {
        this.thicknessDoorLeaf = thicknessDoorLeaf;
    }

    public String getSideDoorOpen() {
        return sideDoorOpen;
    }

    public void setSideDoorOpen(String sideDoorOpen) {
        this.sideDoorOpen = sideDoorOpen;
    }

    public int getInnerDoorOpen() {
        return innerDoorOpen;
    }

    public void setInnerDoorOpen(int innerDoorOpen) {
        this.innerDoorOpen = innerDoorOpen;
    }

    public int getDoorstep() {
        return doorstep;
    }

    public void setDoorstep(int doorstep) {
        this.doorstep = doorstep;
    }

    public int getStainlessSteelDoorstep() {
        return stainlessSteelDoorstep;
    }

    public void setStainlessSteelDoorstep(int stainlessSteelDoorstep) {
        this.stainlessSteelDoorstep = stainlessSteelDoorstep;
    }

    public int getTopDoorTrim() {
        return topDoorTrim;
    }

    public void setTopDoorTrim(int topDoorTrim) {
        this.topDoorTrim = topDoorTrim;
    }

    public int getLeftDoorTrim() {
        return leftDoorTrim;
    }

    public void setLeftDoorTrim(int leftDoorTrim) {
        this.leftDoorTrim = leftDoorTrim;
    }

    public int getRightDoorTrim() {
        return rightDoorTrim;
    }

    public void setRightDoorTrim(int rightDoorTrim) {
        this.rightDoorTrim = rightDoorTrim;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<DoorsОrder> getОrders() {
        return orders;
    }

    public void setОrders(List<DoorsОrder> оrders) {
        this.orders = оrders;
    }

    public void addOrder(DoorsОrder door) {
        this.orders.add(door);
    }

    public String getDoorColor() {
        return doorColor;
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

    public DoorEntity setPriceOfDoorType(@NonNull int discount,
                                         @NonNull int RetailMargin) {

        setPrice((int) doorType.getRetailPrice());
        setDiscountPrice(price - ((int) ((price * discount) / 100)));
        setPriceWithMarkup(discountPrice + ((int) ((discountPrice * RetailMargin) / 100)));

        return this;
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
                    4,
                    false,
                    (int) furnitureKit.getTopLock().getPrice());
        }
        if (furnitureKit.getTopinternaLockDecoration() != null) {
            costList.addLine("Фурнитура: накладка верх. внутреняя ",
                    4,
                    false,
                    (int) furnitureKit.getTopinternaLockDecoration().getPrice());
        }
        if (furnitureKit.getTopouterLockDecoration() != null) {
            costList.addLine("Фурнитура: накладка верх. внешняя ",
                    4,
                    false,
                    (int) furnitureKit.getTopouterLockDecoration().getPrice());
        }

        if (furnitureKit.getTopLockCylinder() != null) {
            costList.addLine("Фурнитура: цилиндр ",
                    4,
                    false,
                    (int) furnitureKit.getTopLockCylinder().getPrice());
        }

        //LowerLock
        if (furnitureKit.getLowerLock() != null) {
            costList.addLine("Фурнитура: нижний замок ",
                    4,
                    false,
                    (int) furnitureKit.getLowerLock().getPrice());
        }
        if (furnitureKit.getLowerinternaLockDecoration() != null) {
            costList.addLine("Фурнитура: накладка низ. внутренняя",
                    4,
                    false,
                    (int) furnitureKit.getLowerinternaLockDecoration().getPrice());
        }
        if (furnitureKit.getLowerouterLockDecoration() != null) {
            costList.addLine("Фурнитура: накладка низ. внешняя",
                    4,
                    false,
                    (int) furnitureKit.getLowerouterLockDecoration().getPrice());
        }
        if (furnitureKit.getLowerLockCylinder() != null) {
            costList.addLine("Фурнитура: цилиндр ",
                    4,
                    false,
                    (int) furnitureKit.getLowerLockCylinder().getPrice());
        }


        // handle
        if (furnitureKit.getHandle() != null) {
            costList.addLine("Фурнитура: ручка ",
                    4,
                    false,
                    (int) furnitureKit.getHandle().getPrice());
        }
        // Closer
        if (furnitureKit.getCloser() != null) {
            costList.addLine("Фурнитура: доводчик ",
                    4,
                    false,
                    (int) furnitureKit.getCloser().getPrice());
        }
        //endDoorLock
        if (furnitureKit.getEndDoorLock() != null) {
            costList.addLine("Фурнитура: торцевой шпингалет ",
                    4,
                    false,
                    (int) furnitureKit.getEndDoorLock().getPrice());
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

    public void setDoorColor(String doorColor) {
        this.doorColor = doorColor;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public int getIsDoorGlass() {
        return isDoorGlass;
    }

    public void setIsDoorGlass(int isDoorGlass) {
        this.isDoorGlass = isDoorGlass;
    }

    public DoorGlass getDoorGlass() {
        return doorGlass;
    }

    public void setDoorGlass(DoorGlass doorGlass) {
        this.doorGlass = doorGlass;
    }

    public List<DoorClass> getAvailableDoorClass() {
        return availableDoorClass;
    }

    public void setAvailableDoorClass(List<DoorClass> availableDoorClass) {
        this.availableDoorClass = availableDoorClass;
    }

    public void addAvailableDoorClass(DoorClass doorClass) {
        this.availableDoorClass.add(doorClass);
    }

    public void addAvailableDoorClass(List<DoorClass> doorClassList) {
        for (DoorClass doorClass : doorClassList) {
            addAvailableDoorClass(doorClass.clearNonSerializingFields());
        }
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPriceWithMarkup() {
        return priceWithMarkup;
    }

    public void setPriceWithMarkup(int priceWithMarkup) {
        this.priceWithMarkup = priceWithMarkup;
    }

    public int getSealingLine() {
        if (sealingLine == 0 && thirdSealingLine != 0) {
            sealingLine = 3;
        } else {
            sealingLine = 2;
        }
        return sealingLine;
    }

    public void setSealingLine(int sealingLine) {
        this.sealingLine = sealingLine;
    }

    public int getFirstSealingLine() {
        return firstSealingLine;
    }

    public void setFirstSealingLine(int firstSealingLine) {
        this.firstSealingLine = firstSealingLine;
    }

    public int getSecondSealingLine() {
        return secondSealingLine;
    }

    public void setSecondSealingLine(int secondSealingLine) {
        this.secondSealingLine = secondSealingLine;
    }

    public int getThirdSealingLine() {
        return thirdSealingLine;
    }

    public void setThirdSealingLine(int thirdSealingLine) {
        this.thirdSealingLine = thirdSealingLine;
    }

    public int getMDF() {
        return MDF;
    }

    public void setMDF(int MDF) {
        this.MDF = MDF;
    }

    public int getAdditionallyHingeMain() {
        return additionallyHingeMain;
    }

    public void setAdditionallyHingeMain(int additionallyHingeMain) {
        this.additionallyHingeMain = additionallyHingeMain;
    }

    public int getAdditionallyHingeNotMain() {
        return additionallyHingeNotMain;
    }

    public void setAdditionallyHingeNotMain(int additionallyHingeNotMain) {
        this.additionallyHingeNotMain = additionallyHingeNotMain;
    }

    public List<DoorsОrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DoorsОrder> orders) {
        this.orders = orders;
    }

    public int getAmplifierCloser() {
        return amplifierCloser;
    }

    public void setAmplifierCloser(int amplifierCloser) {
        this.amplifierCloser = amplifierCloser;
    }

    public FurnitureKit getFurnitureKit() {
        return furnitureKit;
    }

    public void setFurnitureKit(FurnitureKit furnitureKit) {
        this.furnitureKit = furnitureKit;
    }

    public int getIsDoorFanlightGlass() {
        return isDoorFanlightGlass;
    }

    public void setIsDoorFanlightGlass(int isDoorFanlightGlass) {
        this.isDoorFanlightGlass = isDoorFanlightGlass;
    }

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }

    public RestrictionOfSelectionFields getTemplate() {
        return template;
    }

    public void setTemplate(RestrictionOfSelectionFields template) {
        this.template = template;
    }

    public int getOrderHolder() {
        return orderHolder;
    }

    public void setOrderHolder(int orderHolder) {
        this.orderHolder = orderHolder;
    }

    public ShieldKit getShieldKit() {
        return shieldKit;
    }

    public void setShieldKit(ShieldKit shieldKit) {
        this.shieldKit = shieldKit;
    }
}
