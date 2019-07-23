package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.CostList;
import com.jds.model.Door;
import com.jds.model.DoorPart;
import com.jds.model.FireproofDoor;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<DoorPart> getDoorPart(DoorEntity fireproofDoor){

        return DoorPart.getDoopPartsList(dAO.getSizeOfDoorPartsList(2),fireproofDoor);

    }

    public void calculateTheDoor(DoorEntity door){

        CostList costList = new CostList();

        calculateMetalDoor(door);

        int price = getRandomPrice(8500,25000);
        door.setPrice(price);
        door.setDiscountPrice(price - ((int) (price*0.25)));
        door.setPriceWithMarkup(door.getDiscountPrice() + ((int) (door.getDiscountPrice()*1.25)));
        door.createName();

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


        door.setSheets(sheetCutting.getSheets());
        double doorWeigh = 0;//door.getSheets().size()*dAO.door.getMetal().
        door.setWeigh(doorWeigh);

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
                door.addAvailableDoorClass(dAO.getDoorClass(3));
        }
        if (door == null) {
            door = new DoorEntity();
            door.addAvailableDoorClass(dAO.getDoorClass(3));
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

        doorType.setNamePicture(makeRightNamePictureDoorType(doorType.getNamePicture()));
        return  dAO.saveOrUpdateDoorType(doorType);
    }

    public String makeRightNamePictureDoorType(String namePicture){

        namePicture = namePicture.replace("CalculationDoorsAutonomous","images");
        namePicture = namePicture + ".jpg";
        return namePicture;
    }
}
