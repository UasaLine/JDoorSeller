package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.entity.Metal;
import com.jds.entity.SizeOfDoorParts;
import com.jds.model.DoorPart;
import com.jds.model.FireproofDoor;
import com.jds.model.cutting.Sheet;
import com.jds.model.cutting.SheetCutting;
import com.udojava.evalex.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<DoorPart> getDoorPart(FireproofDoor fireproofDoor){

        return DoorPart.getDoopPartsList(DAO.getSizeOfDoorPartsList(1),fireproofDoor);

    }

    public void calculateTheDoor(FireproofDoor door){
        int metalPrice = calculateMetalDoor(door);
        door.setPrice(20000);

    }
    public int calculateMetalDoor(FireproofDoor door){

        List<DoorPart> partList = getDoorPart(door);
        Sheet sheet = new Sheet(2500,1250);

        SheetCutting sheetCutting = new SheetCutting(partList,sheet);
        sheetCutting.CompleteCutting();
        door.setSheets(sheetCutting.getSheets());
        return 0;
    }


}
