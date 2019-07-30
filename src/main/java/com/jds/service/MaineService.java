package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.*;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.jds.model.modelEnum.TypeOfSalaryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class MaineService {

    @Autowired
    private MainDAO dAO;

    public List<DoorClass> getDoorClass(){
        return  dAO.getDoorClass();
    }

    public List<FireproofDoor> getlistDoor(){
        return dAO.getlistDoor();
    }

    public List<DoorType> getDoorType(){
        return dAO.getDoorType();
    }

    public List<Metal> getMetals(){
        return dAO.getMetals();
    }

    public List<DoorPart> getDoorPart(DoorEntity door){

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(door.getDoorType()),door);

    }

    public void calculateTheDoor(DoorEntity door){


        calculateMetalDoor(door);
        calculateColorDoor(door);

        BendSetting bendSetting = dAO.getbendSettingId(door.getDoorType(),door.getMetal(),door.getSealingLine());
        DoorType doorType = dAO.getDoorType(door.getDoorType());
        SalarySetting salarySetting = dAO.getSalarySetting(door.getMetal());
        door = door.calculateSalary(bendSetting,dAO.getSalaryConstantsMap(), salarySetting,doorType);



        int price = getRandomPrice(8500,25000);
        door.setPrice(price);
        door.setDiscountPrice(price - ((int) (price*0.25)));
        door.setPriceWithMarkup(door.getDiscountPrice() + ((int) (door.getDiscountPrice()*1.25)));
        door.createName();

    }

    public DoorColors saveDoorColors(DoorColors doorColors){
        doorColors.setPicturePath("images/Door/AColor1/"+doorColors.getPicturePath()+".jpg");
        return dAO.saveDoorColors(doorColors);
    }

    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(){
        return new RestrictionOfSelectionFields().stuffColors(dAO.getDoorColors());
    }

    public static int getRandomPrice(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void calculateMetalDoor(DoorEntity door){

        List<DoorPart> partList = getDoorPart(door);
        Sheet sheet = new Sheet(2500,1250);

        SheetCutting sheetCutting = new SheetCutting(partList,sheet);
        sheetCutting.CompleteCutting();
        sheetCutting.clearHardCalculationData();

        Metal metal = dAO.getMetal(door.getMetal());

        door.setSheets(sheetCutting.getSheets());
        door.calculateWeigh(metal);
        door.calculateCostMetal(metal);
    }

    public void calculateColorDoor(DoorEntity door){

        door.calculateColorDoor(dAO.getDoorColor(door.getDoorColor()));
    }


    public BendSetting saveBendSetting(BendSetting bendSetting){
        return dAO.saveBendSetting(bendSetting);
    }

    public DoorEntity saveDoor(DoorEntity door){
        return dAO.saveDoor(door);
    }

   public DoorsОrder saveOrder (DoorsОrder order){
        return dAO.saveOrder(order);
   }

   public List<DoorsОrder> getOrders(){
       return dAO.getOrders();
   }

   public DoorsОrder getOrder(String id){

        int intId = Integer.parseInt(id);
        if (intId == 0) {
           return new DoorsОrder();
        }

        return dAO.getOrder(intId);
   }

   public DoorEntity getDoor(String id,String orderId){

        DoorEntity door = null;
        if (id!=null && !id.isEmpty() && !id.equals("0")){
                door = dAO.getDoor(Integer.parseInt(id));
                door.addAvailableDoorClass(new DoorClassForFrond(dAO.getDoorClass(6)));
        }
        if (door == null) {
            door = new DoorEntity();
            door.addAvailableDoorClass(new DoorClassForFrond(dAO.getDoorClass(6)));
            //door.addAvailableDoorClass(dAO.getDoorClass(4));
            //door.addAvailableDoorClass(dAO.getDoorClass(5));
        }



        if (orderId!=null && !orderId.isEmpty() && !orderId.equals("0") && (door.getId()==0)){

                   DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
                   order.addDoor(door);
                   dAO.saveOrder(order);
        }

        return door;

   }

    public DoorsОrder deleteDoorFromOrder(String id, String orderId){

        if (orderId!=null && !orderId.isEmpty() && !orderId.equals("0") && id!=null && !id.isEmpty() && !id.equals("0")) {
            DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
            int mess = order.deleteDoor(Integer.parseInt(id));
            if (mess == 1){
                dAO.saveOrder(order);
                return order;
            }

        }

        return null;
    }

    public DoorsОrder deleteOrder(String orderId){
        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        return order;
    }

    public int saveOrUpdateDoorType (DoorType doorType){

        return  dAO.saveOrUpdateDoorType(doorType);
    }

    public SalaryConstants saveSalaryConstants(SalaryConstants constants){
        return  dAO.saveSalaryConstants(constants);
    }

    public SalarySetting saveSalarySetting(SalarySetting setting){
        return  dAO.saveSalarySetting(setting);
    }


}
