package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.Door;
import com.jds.model.DoorPart;
import com.jds.model.FireproofDoor;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.udojava.evalex.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaineService {

    @Autowired
    private MainDAO DAO;



    public List<DoorClass> getDoorClass(){
        return  DAO.getDoorClass();
    }

    public List<FireproofDoor> getlistDoor(){
        return DAO.getlistDoor();
    }

    public List<DoorType> getDoorType(){
        return DAO.getDoorType();
    }

    public List<Metal> getMetals(){
        return DAO.getMetals();
    }

    public List<DoorPart> getDoorPart(DoorEntity fireproofDoor){

        return DoorPart.getDoopPartsList(DAO.getSizeOfDoorPartsList(1),fireproofDoor);

    }

    public void calculateTheDoor(DoorEntity door){
        int metalPrice = calculateMetalDoor(door);
        door.setPrice(20000);

    }
    public int calculateMetalDoor(DoorEntity door){

        List<DoorPart> partList = getDoorPart(door);
        Sheet sheet = new Sheet(2500,1250);

        SheetCutting sheetCutting = new SheetCutting(partList,sheet);
        sheetCutting.CompleteCutting();
        door.setSheets(sheetCutting.getSheets());
        return 0;
    }

    public DoorEntity saveDoor(DoorEntity door){
        return DAO.saveDoor(door);
    }

   public DoorsОrder saveOrder (DoorsОrder order){
        return DAO.saveOrder(order);
   }

   public List<DoorsОrder> getOrders(){
       return DAO.getOrders();
   }

   public DoorsОrder getOrder(String id){

        int intId = Integer.parseInt(id);
        if (intId == 0) {
           return new DoorsОrder();
        }

        return DAO.getOrder(intId);
   }

   public DoorEntity getDoor(String id,String orderId){

        DoorEntity door = null;
        if (id!=null){
            if (!id.isEmpty() && !id.equals("0")){
                door = DAO.getDoor(Integer.parseInt(id));
            }
        }
        if (door == null) {
            door = new DoorEntity();
        }



       if (orderId!=null){
           if (!orderId.isEmpty() && !orderId.equals("0")){

                   DoorsОrder order = DAO.getOrder(Integer.parseInt(orderId));
                   order.addDoor(door);
                   DAO.saveOrder(order);

                   //door.addOrder(order);
               }
        }

        return door;

   }
}
