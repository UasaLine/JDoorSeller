package com.jds.dao;

import com.jds.entity.*;
import com.jds.model.FireproofDoor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class MainDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public MainDAO() {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateDoorClass(DoorClass doorClass) {

        int id = getDoorClassId(doorClass.getName());//check exists
        if (id>0){
            doorClass.setId(id);
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorClass);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMetal(Metal metal) {

        int id = getMetalId(metal.getIdManufacturerProgram());//check exists
        if (id>0){
            metal.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(metal);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrUpdateDoorType(DoorType doorType) {

        int id = getDooTypeId(doorType.getName());//check exists
        if (id>0){
            doorType.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorType);

        return doorType.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDoorFurniture(DoorFurniture doorFurniture) {

        int id = getDoorFurnitureId(doorFurniture.getIdManufacturerProgram());//check exists
        if (id>0){
            doorFurniture.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorFurniture);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateLimitationDoor(LimitationDoor limitationDoor) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(limitationDoor);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateSizeOfDoorParts(SizeOfDoorParts sizeOfDoorParts) {

        sizeOfDoorParts.decodeAfterJson();

        int id = getSizeOfDoorPartsId(sizeOfDoorParts.getName());//check exists
        if (id>0){
            sizeOfDoorParts.setId(id);
        }
        sizeOfDoorParts.getDoorType().setId(saveOrUpdateDoorType(sizeOfDoorParts.getDoorType()));

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sizeOfDoorParts);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDoorColors(DoorColors doorColors) {

        int id = getDoorColorsId(doorColors.getIdManufacturerProgram());//check exists
        if (id>0){
            doorColors.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorColors);

    }

    public int getDoorClassId(String name){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_class where name like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (DoorClass.class)
                .setParameter ("log", name);
        List<DoorClass> doorClassList = query.list();

        if(doorClassList.size()>0){
            return doorClassList.get(0).getId();
        }
        return 0;

    }
    public int getDooTypeId(String name){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_type where name like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (DoorType.class)
                .setParameter ("log", name);
        List<DoorType> doorTypeList = query.list();

        if(doorTypeList.size()>0){
            return doorTypeList.get(0).getId();
        }
        return 0;

    }
    public int getMetalId(String id){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from metal where id_manufacturer_program like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (Metal.class)
                .setParameter ("log", id);
        List<Metal> metalList = query.list();

        if(metalList.size()>0){
            return metalList.get(0).getId();
        }
        return 0;

    }
    public int getDoorFurnitureId(String id){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_furniture where id_manufacturer_program like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (DoorFurniture.class)
                .setParameter ("log", id);
        List<DoorFurniture> doorFurnitureList = query.list();

        if(doorFurnitureList.size()>0){
            return doorFurnitureList.get(0).getId();
        }
        return 0;

    }
    public int getDoorColorsId(String id){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from Door_Colors where idManufacturerProgram like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (Metal.class)
                .setParameter ("log", id);
        List<DoorColors> doorColorsList = query.list();

        if(doorColorsList.size()>0){
            return doorColorsList.get(0).getId();
        }
        return 0;

    }
    public int getSizeOfDoorPartsId(String name){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from size_door_parts where name like :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (SizeOfDoorParts.class)
                .setParameter ("log", name);
        List<SizeOfDoorParts> sizeOfDoorPartsList = query.list();

        if(sizeOfDoorPartsList.size()>0){
            return sizeOfDoorPartsList.get(0).getId();
        }
        return 0;

    }

    public List<SizeOfDoorParts> getSizeOfDoorPartsList(int doortypeId){

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from size_door_parts where doortype_id = :log";
        Query query = session.createSQLQuery (sql)
                .addEntity (SizeOfDoorParts.class)
                .setParameter ("log", doortypeId);
        return query.list();

    }

    public List<DoorClass> getDoorClass() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class";
        Query query = session.createSQLQuery(sql).addEntity(DoorClass.class);

        List<DoorClass> list = query.list();

        //List<DoorClass> list = session.createCriteria(DoorClass.class).list();


        return list;

    }

    public List<DoorType> getDoorType() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_type";
        Query query = session.createSQLQuery(sql).addEntity(DoorType.class);

        List<DoorType> list = query.list();


        return list;

    }

    public List<FireproofDoor> getlistDoor(){

        List<FireproofDoor> list = new ArrayList<>();
        list.add(new FireproofDoor());

        return list;
    }

    public List<Metal> getMetals() {

        Session session = sessionFactory.openSession();

        String sql = "select * from metal";
        Query query = session.createSQLQuery(sql).addEntity(Metal.class);

        List<Metal> list = query.list();

        return list;

    }
}
