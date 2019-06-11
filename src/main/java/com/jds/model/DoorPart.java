package com.jds.model;

import com.jds.entity.SizeOfDoorParts;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DoorPart {

    String name;
    int width;
    int height;
    int quantity;

    public static List<DoorPart> getDoopPartsList(List<SizeOfDoorParts> list, FireproofDoor Door){

        List<DoorPart> partList = new ArrayList<>();


        for(SizeOfDoorParts parts:list){

            String size = parsePatternForCalculation(parts.getCondition(),Door,true);
            int booCondition = eval(size);

            if (booCondition==1 ) {

                DoorPart doorPart = new DoorPart();

                size = parsePatternForCalculation(parts.getHeight(),Door,false);
                doorPart.setHeight(eval(size));

                size = parsePatternForCalculation(parts.getWidth(),Door,false);
                doorPart.setWidth(eval(size));

                doorPart.setName(parts.getName());
                doorPart.setQuantity(Integer.parseInt(parts.getQuantity()));

                partList.add(doorPart);
            }


        }

        return partList;
    }

    public static String parsePatternForCalculation(String condition,FireproofDoor door,Boolean provision){

        //H - widthDoor
        condition = condition.replace("H",String.valueOf(door.getHeightDoor()));

        //L - widthDoor
        condition = condition.replace("L",String.valueOf(door.getWidthDoor()));

        //g - deepnessDoor
        condition = condition.replace("g",String.valueOf(door.getDeepnessDoor()));

        //t - thicknessDoorLeaf
        condition = condition.replace("t",String.valueOf(door.getThicknessDoorLeaf()));

        //OS - аctivDoorLeafWidth
        condition = condition.replace("OS",String.valueOf(door.getАctivDoorLeafWidth()));

        //K - glassWidth
        condition = condition.replace("K",String.valueOf(door.getGlassWidth()));

        //J - glassWidth
        condition = condition.replace("J",String.valueOf(door.getGlassHeight()));

        if(provision) {
            //"и" - and
            condition = condition.replace("и", "&&");// only for test
        }
        else {
            //" " - +
            condition = condition.replace(" ", "+");// only for test
        }

        return condition;
    }

    public static int eval(String function){
        if (function.isEmpty()){
            return 1;
        }
        return new Expression(function).eval().intValue();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
