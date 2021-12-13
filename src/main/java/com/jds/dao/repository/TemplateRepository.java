package com.jds.dao.repository;

import com.jds.dao.entity.LimitationDoor;
import com.jds.model.enumModels.TypeOfLimitionDoor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class TemplateRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<LimitationDoor> getLimitationDoors(int doorTypeId, TypeOfLimitionDoor typeSettings) {

        Session session = sessionFactory.openSession();

        String sql = "select * from limitation_door where doortype_id = :id and typesettings like :typeSettings";
        Query query = session.createSQLQuery(sql)
                .addEntity(LimitationDoor.class)
                .setParameter("id", doorTypeId)
                .setParameter("typeSettings", typeSettings.toString());
        List<LimitationDoor> list = query.list();

        session.close();

        List<LimitationDoor> limitList = new ArrayList<>();
        if (list.size() > 0) {
            limitList = list;
        }
        return limitList;
    }
}
