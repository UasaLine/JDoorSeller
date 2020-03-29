package com.jds.dao;

import com.jds.dao.entity.ColorEntity;
import lombok.NonNull;
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
public class ColorRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public int getColorsIdByManufacturerId(String id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where idManufacturerProgram like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(ColorEntity.class)
                .setParameter("log", id);
        List<ColorEntity> doorColorsList = query.list();

        session.close();

        if (doorColorsList.size() > 0) {
            return doorColorsList.get(0).getId();
        }
        return 0;

    }

    public ColorEntity getDoorColorByName(String name) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where name like :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(ColorEntity.class)
                .setParameter("log", name);
        List<ColorEntity> doorColorsList = query.list();

        session.close();

        if (doorColorsList.size() > 0) {
            return doorColorsList.get(0);
        }
        return null;

    }

    public List<ColorEntity> getColors() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors ";
        Query query = session.createSQLQuery(sql)
                .addEntity(ColorEntity.class);
        List<ColorEntity> doorColorsList = query.list();

        session.close();

        return doorColorsList;
    }

    public ColorEntity getColorById(@NonNull int id) {
        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from door_colors where id = :id";
        Query query = session.createSQLQuery(sql)
                .addEntity(ColorEntity.class)
                .setParameter("id", id);
        List<ColorEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return new ColorEntity();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ColorEntity saveColors(ColorEntity doorColors) {

        int id = getColorsIdByManufacturerId(doorColors.getIdManufacturerProgram());//check exists
        if (id > 0) {
            doorColors.setId(id);
        }

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(doorColors);

        return doorColors;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteColor(@NonNull ColorEntity colors) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(colors);

        return "ok";

    }
}
