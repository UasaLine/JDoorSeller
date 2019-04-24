package com.jds.dao;

import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.entity.LimitationDoor;
import com.jds.entity.SizeOfDoorParts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorClass);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateLimitationDoor(LimitationDoor limitationDoor) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(limitationDoor);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateDoorType(DoorType doorType) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorType);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateSizeOfDoorParts(SizeOfDoorParts sizeOfDoorParts) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sizeOfDoorParts);

    }




    public List<DoorClass> getDoorClass() {

        Session session = sessionFactory.openSession();

        String sql = "select * from door_class";
        Query query = session.createSQLQuery(sql).addEntity(DoorClass.class);

        List<DoorClass> list = query.list();

        //List<DoorClass> list = session.createCriteria(DoorClass.class).list();


        return list;

    }
}
