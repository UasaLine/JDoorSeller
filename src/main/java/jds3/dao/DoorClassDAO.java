package jds3.dao;

import jds3.entity.DoorClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    }

    public DoorClass getDoorClass() {

        Session session = sessionFactory.openSession();
        DoorClass doorClass = (DoorClass) session.load(DoorClass.class, 1);
        return doorClass;

    }
}
