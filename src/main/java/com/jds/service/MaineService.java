package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.model.FireproofDoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
