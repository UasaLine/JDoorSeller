package com.jds.dao.repository;

import com.jds.dao.entity.DoorFurniture;
import com.jds.dao.entity.GlassPositionEntity;
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
public class GlassPositionRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<GlassPositionEntity> get() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from glass_position_entity ";
        Query query = session.createSQLQuery(sql)
                .addEntity(GlassPositionEntity.class);
        List<GlassPositionEntity> glassPositionList = query.list();

        session.close();

        return glassPositionList;
    }

    public GlassPositionEntity getById(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from glass_position_entity where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(GlassPositionEntity.class)
                .setParameter("id", id);
        List<GlassPositionEntity> glassPositionList = query.list();

        session.close();

        if (glassPositionList.size() > 0) {
            return glassPositionList.get(0);
        }

        return new GlassPositionEntity();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public GlassPositionEntity save(GlassPositionEntity furniture) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(furniture);

        return furniture;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String delete(GlassPositionEntity glassPosition) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(glassPosition);
        return "ok";
    }
}
