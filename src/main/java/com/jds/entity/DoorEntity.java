package com.jds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.CostList;
import com.jds.model.Door;
import com.jds.model.DoorClassForFrond;
import com.jds.model.cutting.Sheet;
import com.jds.model.modelEnum.TypeOfSalaryConst;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Door")
public class DoorEntity implements Door {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_id", nullable = false)
    private int id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "doorType")
    private int doorType;

    @Column(name = "widthDoor")
    private int widthDoor;

    @Column(name = "heightDoor")
    private int heightDoor;

    @Column(name = "аctivDoorLeafWidth")
    private int activeDoorLeafWidth;

    @Column(name = "doorFanlightHeight")
    private int doorFanlightHeight;

    @Column(name = "metal")
    private double metal;

    @Column(name = "deepnessDoor")
    private int deepnessDoor;

    @Column(name = "thicknessDoorLeaf")
    private int thicknessDoorLeaf;

    @Column(name = "sideDoorOpen")
    private String sideDoorOpen;

    @Column(name = "innerDoorOpen")
    private int innerDoorOpen;

    @Column(name = "doorstep")
    private int doorstep;

    @Column(name = "stainlessSteelDoorstep")
    private int stainlessSteelDoorstep;

    @Column(name = "topDoorTrim")
    private int topDoorTrim;

    @Column(name = "leftDoorTrim")
    private int leftDoorTrim;

    @Column(name = "rightDoorTrim")
    private int rightDoorTrim;

    @Column(name = "price")
    private int price;

    @JsonIgnore
    @ManyToMany(mappedBy = "doors",fetch = FetchType.LAZY)
    private List<DoorsОrder> оrders;

    @Column(name = "doorColor")
    private String doorColor;

    @Transient
    private List<Sheet> sheets;

    @Transient
    private List<DoorClassForFrond> availableDoorClass;

    @Transient
    private int discountPrice;

    @Transient
    private int priceWithMarkup;

    @Transient
    private int isDoorGlass;

    @Transient
    private DoorGlass doorGlass;

    @Transient
    private CostList costList;

    @Transient
    private double weigh;

    @Transient
    private double space;

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


    public void calculateWeigh(Metal metal){

        space = 0;

        for (Sheet sheet:sheets){
            space +=sheet.getAmountWorkSpace();
        }
        this.weigh = space*metal.getIndexHeft();
    }

    public void calculateCostMetal(Metal metal){
        int costMetal = sheets.size()*metal.getIndexHeft()*metal.getPrice();
        costList.addLine("металл[листов: "+sheets.size()+", вес: "+metal.getIndexHeft()+", цена:"+metal.getPrice()+"]"
                ,1
                ,false
                ,costMetal);
    }

    public void calculateColorDoor(DoorColors doorColors){

        double spaceColor =  getSpace();//-S_ребер;?
        double costColor = (doorColors.getPricePaintingMeterOfSpace()*spaceColor)/5;

        costList.addLine("Цвет[площадь:"+spaceColor
                        +", цена метра2:"+doorColors.getPricePaintingMeterOfSpace()
                        +", формула:площадь*(цена за кг)/5]",
                2,
                false,
                (int)costColor);
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

    public void createName(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Дверь противопожарная ДПД EIWS60 ");
        stringBuilder.append(widthDoor+" X "+heightDoor);
        stringBuilder.append(" ("+metal+" мм) "+sideDoorOpen+" "+doorColor);
        name = stringBuilder.toString();
    }

    public DoorEntity() {
        this.оrders = new ArrayList<DoorsОrder>();
        this.availableDoorClass = new ArrayList<DoorClassForFrond>();
        this.doorGlass = new DoorGlass();
        this.costList = new CostList();
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

    public int getDoorType() {
        return doorType;
    }

    public void setDoorType(int doorType) {
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
        return оrders;
    }

    public void setОrders(List<DoorsОrder> оrders) {
        this.оrders = оrders;
    }

    public void addOrder (DoorsОrder door){
        this.оrders.add(door);
    }

    public String getDoorColor() {
        return doorColor;
    }

    public DoorEntity calculateSalary(BendSetting bendSetting,
                                      Map<TypeOfSalaryConst,Double> ConstMap,
                                      SalarySetting salarySetting,
                                      DoorType doorType,
                                      DoorColors doorColors){

        //constructorSalary
        //********************************************************
        costList.addLine("Конструктор:",
                        3,
                        false,
                (int) (ConstMap.get(TypeOfSalaryConst.CONSTRUCTOR_COST) + ConstMap.get(TypeOfSalaryConst.MARKUP_CONSTRUCTOR)));

        //cuttingSalary
        //********************************************************
        costList.addLine("Резка:",
                3,
                false,
                (int)(this.sheets.size()*ConstMap.get(TypeOfSalaryConst.CUTTING_COST_PER_SHEET)));


        //bendingSalary
        //********************************************************
        if(bendSetting.getBend()==0){
            System.out.println("ERROR calculateSalary  - Количество гибов = 0!");
        }
        double bendingCost = ConstMap.get(TypeOfSalaryConst.BENDING_COST);
        double cost = bendingCost*bendSetting.getBend();
        costList.addLine("Гибка: ["+bendSetting.getBend()+" Х "+bendingCost+" = "+cost,
                3,
                false,
                (int)cost);

        //guillotine
        //********************************************************
        if(bendSetting.getGuillotine()==0){
            System.out.println("ERROR calculateSalary  - Количество резов гильотины = 0!");
        }
        cost = bendSetting.getGuillotine()*ConstMap.get(TypeOfSalaryConst.GUILLOTINE_COST);
        costList.addLine("Гильотина: "+bendSetting.getGuillotine()
                        +" Х "+ConstMap.get(TypeOfSalaryConst.GUILLOTINE_COST)+" = "+cost,
                3,
                false,
                (int)cost);


        //contact welding
        //********************************************************
        cost = 0;
        if (metal<1.4){
            if(doorType.getDoorLeaf()==1){
                cost = salarySetting.getContactWeldingForOneLeaf();
            }
            else if(doorType.getDoorLeaf()==2){
                cost = salarySetting.getContactWeldingForTwoLeaf();
            }
            costList.addLine("Контактная Сварка: "+cost,
                    3,
                    false,
                    (int)cost);

        }

        //welding
        //********************************************************
        weldingCost(bendSetting,
                ConstMap,
                salarySetting,
                doorType,
                doorColors);


        //clean
        //*****************************************
        cleanCost(bendSetting,
                    ConstMap,
                    salarySetting,
                    doorType,
                    doorColors);


        //Polymer coloring
        //*****************************************
        polymerColoringCost(bendSetting,
                            ConstMap,
                            salarySetting,
                            doorType,
                            doorColors);

        //assembly
        assemblyCost(bendSetting,
                ConstMap,
                salarySetting,
                doorType,
                doorColors);
        return this;
    }


    private void weldingCost(BendSetting bendSetting,
                             Map<TypeOfSalaryConst,Double> ConstMap,
                             SalarySetting salarySetting,
                             DoorType doorType,
                             DoorColors doorColors){
        //main
        double cost = 0;
        double spaceDoor = ((double)(widthDoor*heightDoor))/1000000;
        if(doorType.getDoorLeaf()==1){
            if((widthDoor<=ConstMap.get(TypeOfSalaryConst.WIDTH_LIMIT_ONE_LEAF))
                    &&(heightDoor<=ConstMap.get(TypeOfSalaryConst.HEIGHT_LIMIT_ONE_LEAF))){

                if(doorType.getDoorClass().getHot()==1){
                    cost = salarySetting.getWeldingForOneLeafHot();
                }
                else if(MDF==1) {
                    cost = salarySetting.getWeldingForOneLeafMDF();
                }
                else {
                    cost = salarySetting.getWeldingForOneLeaf();
                }
            }
            else {
                cost = spaceDoor*ConstMap.get(TypeOfSalaryConst.COST_OVER_LIMIT_ONE_LEAF);
            }
        }
        else if(doorType.getDoorLeaf()==2){
            if((widthDoor<=ConstMap.get(TypeOfSalaryConst.WIDTH_LIMIT_TWO_LEAF))
                    &&(heightDoor<=ConstMap.get(TypeOfSalaryConst.HEIGHT_LIMIT_TWO_LEAF))){

                if(doorType.getDoorClass().getHot()==1){
                    cost = salarySetting.getWeldingForTwoLeafHot();
                }
                else if(MDF==1) {
                    cost = salarySetting.getWeldingForTwoLeafMDF();
                }
                else {
                    cost = salarySetting.getWeldingForTwoLeaf();
                }

            }
            else {
                cost = spaceDoor*ConstMap.get(TypeOfSalaryConst.COST_OVER_LIMIT_TWO_LEAF);
            }
        }
        costList.addLine("Сварка: "+cost,
                3,
                false,
                (int)cost);

        //doorFanlight
        cost = 0;
        if (doorFanlightHeight>0){
            cost = ConstMap.get(TypeOfSalaryConst.COST_DOOR_FANLIGHT);
            costList.addLine("Фрамуга: "+cost,
                    3,
                    false,
                    (int)cost);
        }

        //handle
        //ConstMap.get(TypeOfSalaryConst.COST_WELDED_HANDLE);


        //door hinge
        cost = 0;
        if(additionallyHingeMain==1||additionallyHingeNotMain==1){

            if(additionallyHingeMain==1){
                cost+=ConstMap.get(TypeOfSalaryConst.COST_WELDING_ADDITIONAL_DOOR_HINGE);
            }
            if (additionallyHingeNotMain==1){
                cost+=ConstMap.get(TypeOfSalaryConst.COST_WELDING_ADDITIONAL_DOOR_HINGE);
            }

            costList.addLine("Доп.петля - "+cost,
                    3,
                    false,
                    (int) cost);
        }

        //Amplifier Closer
        cost=0;
        if (amplifierCloser==1){
            cost = ConstMap.get(TypeOfSalaryConst.COST_AMPLIFIER_CLOSER);
            costList.addLine("усилитель доводчика - "+cost,
                    3,
                    false,
                    (int) cost);
        }

        //Glass
        cost=0;
        if(doorGlass!=null){
            cost = ConstMap.get(TypeOfSalaryConst.WELDING_FOR_GLASS);
            costList.addLine("Стеклопакет - "+cost,
                    3,
                    false,
                    (int) cost);
        }

        //like L5
        //ConstMap.get(TypeOfSalaryConst.SQUARES_MODEL_L5;

        //thirdSealingLine
        cost = 0;
        if (thirdSealingLine>0){
            cost = ConstMap.get(TypeOfSalaryConst.COST_THIRD_SEALING_LINE);
            costList.addLine("Доп. линия уплотнения ",
                    3,
                    false,
                    (int) cost);
        }
    }


    private void cleanCost(BendSetting bendSetting,
                           Map<TypeOfSalaryConst,Double> ConstMap,
                           SalarySetting salarySetting,
                           DoorType doorType,
                           DoorColors doorColors){

        if (doorType.getDoorLeaf()==1){

            costList.addLine(" зачистка, базовая ставка ",3,false,
                    ConstMap.get(TypeOfSalaryConst.COST_CLEAN_ONE_LEAF).intValue());

            if(doorColors.getSmooth()==1){
                costList.addLine(" зачистка, гладкая краска ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_SMOOTH_PAINT_ONE_LEAF).intValue());
            }

            //if(){
            //    costList.addLine(" зачистка, L5 ",3,false,
            //            ConstMap.get(TypeOfSalaryConst.COST_CLEAN_SQUARES_L5_PAINT_ONE_LEAF).intValue());
            //}

            if(additionallyHingeMain==1){
                costList.addLine(" зачистка, доп петля ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_ADDITIONALLY_HINGE_ONE_LEAF).intValue());
            }

            if(thirdSealingLine>0){
                costList.addLine(" зачистка, Доп. линия уплотнения ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_THIRD_SEALING_LINE_ONE_LEAF).intValue());
            }

            if(thirdSealingLine>0){
                costList.addLine(" зачистка, Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_HOT_ONE_LEAF).intValue());
            }


        }
        else if(doorType.getDoorLeaf()==2){

            costList.addLine(" зачистка, базовая ставка ",3,false,
                    ConstMap.get(TypeOfSalaryConst.COST_CLEAN_TWO_LEAF).intValue());

            if(doorColors.getSmooth()==1){
                costList.addLine(" зачистка, гладкая краска ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_SMOOTH_PAINT_TWO_LEAF).intValue());
            }

            //if(){
            //    costList.addLine(" зачистка, L5 ",3,false,
            //            ConstMap.get(TypeOfSalaryConst.COST_CLEAN_SQUARES_L5_PAINT_TWO_LEAF).intValue());
            //}

            if(additionallyHingeMain==1){
                costList.addLine(" зачистка, доп петля ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_ADDITIONALLY_HINGE_TWO_LEAF).intValue());
            }

            if(thirdSealingLine>0){
                costList.addLine(" зачистка, Доп. линия уплотнения ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_THIRD_SEALING_LINE_TWO_LEAF).intValue());
            }

            if(thirdSealingLine>0){
                costList.addLine(" зачистка, Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_CLEAN_HOT_TWO_LEAF).intValue());
            }

        }
    }

    private void polymerColoringCost (BendSetting bendSetting,
                                      Map<TypeOfSalaryConst,Double> ConstMap,
                                      SalarySetting salarySetting,
                                      DoorType doorType,
                                      DoorColors doorColors){
        if (doorType.getDoorLeaf()==1) {
            if(doorType.getDoorClass().getHot()==1){
                costList.addLine(" Полимерка : Створок-1шт,Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_HOT_ONE_LEAF).intValue());
            }
            else if(MDF==1){
                costList.addLine(" Полимерка : Створок-1шт,МД ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_MDF_ONE_LEAF).intValue());
            }
            else {
                costList.addLine(" Полимерка : Створок-1шт,ММ ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_ONE_LEAF).intValue());
            }
        }
        else if (doorType.getDoorLeaf()==2){
            if(doorType.getDoorClass().getHot()==2){
                costList.addLine(" Полимерка : Створок-2шт,Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_HOT_TWO_LEAF).intValue());
            }
            else if(MDF==1){
                costList.addLine(" Полимерка : Створок-2шт,МД ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_MDF_TWO_LEAF).intValue());
            }
            else {
                costList.addLine(" Полимерка : Створок-2шт,ММ ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_POLYMER_COLOR_TWO_LEAF).intValue());
            }
        }
    }

    private void assemblyCost(BendSetting bendSetting,
                              Map<TypeOfSalaryConst,Double> ConstMap,
                              SalarySetting salarySetting,
                              DoorType doorType,
                              DoorColors doorColors){

        if (doorType.getDoorLeaf()==1){

            if(doorType.getDoorClass().getHot()==1){
                costList.addLine(" Сборка : Створок-1шт,Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_HOT_ONE_LEAF).intValue());
            }
            else if(MDF==1){
                costList.addLine(" Сборка : Створок-1шт,МД ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_MDF_ONE_LEAF).intValue());
            }
            else {
                costList.addLine(" Сборка : Створок-1шт,ММ ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_ONE_LEAF).intValue());
            }
        }
        else if(doorType.getDoorLeaf()==2){

            if(doorType.getDoorClass().getHot()==1){
                costList.addLine(" Сборка : Створок-2шт,Термо ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_HOT_TWO_LEAF).intValue());
            }
            else if(MDF==1){
                costList.addLine(" Сборка : Створок-2шт,МД ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_MDF_TWO_LEAF).intValue());
            }
            else {
                costList.addLine(" Сборка : Створок-2шт,ММ ",3,false,
                        ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_TWO_LEAF).intValue());
            }
        }

        if(doorGlass!=null){
            costList.addLine(" Сборка : стеклопакет ",3,false,
                    ConstMap.get(TypeOfSalaryConst.COST_ASSEMBLY_GLASS).intValue());
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

    public List<DoorClassForFrond> getAvailableDoorClass() {
        return availableDoorClass;
    }

    public void setAvailableDoorClass(List<DoorClassForFrond> availableDoorClass) {
        this.availableDoorClass = availableDoorClass;
    }

    public void addAvailableDoorClass(DoorClassForFrond doorClass){
        this.availableDoorClass.add(doorClass);
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
        if (sealingLine==0 && thirdSealingLine !=0){
            sealingLine= 3;
        }
        else {
            sealingLine= 2;
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
}
