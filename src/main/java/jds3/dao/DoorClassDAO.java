package jds3.dao;

import jds3.entity.DoorClass;
import jds3.entity.LimitationDoor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DoorClassDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public DoorClassDAO() {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addDoorClass() {

        Session session = sessionFactory.getCurrentSession();

        DoorClass doorClass = new DoorClass();
        doorClass.setName("DPD");
        doorClass.setHot(false);
        doorClass.setFireproof(false);
        doorClass.setDescription("пример");
        session.saveOrUpdate(doorClass);

        LimitationDoor limitationDoor = new LimitationDoor();
        limitationDoor.setDoorClass(doorClass);
        limitationDoor.setTypeSettings("порог");
        session.saveOrUpdate(limitationDoor);

        LimitationDoor limitationDoor2 = new LimitationDoor();
        limitationDoor2.setDoorClass(doorClass);
        limitationDoor2.setTypeSettings("ширина");
        session.saveOrUpdate(limitationDoor2);

        LimitationDoor limitationDoor3 = new LimitationDoor();
        limitationDoor3.setDoorClass(doorClass);
        limitationDoor3.setTypeSettings("высота");
        session.saveOrUpdate(limitationDoor3);

    }

    public DoorClass getDoorClass() {

        Session session = sessionFactory.openSession();
        DoorClass doorClass = (DoorClass) session.load(DoorClass.class, 4);
        return doorClass;

    }
}
