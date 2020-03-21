package com.jds.dao;

import com.jds.entity.Metal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MetalRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Metal> getMetals() {

        Session session = sessionFactory.openSession();

        String sql = "select * from metal";
        Query query = session.createSQLQuery(sql).addEntity(Metal.class);

        List<Metal> list = query.list();

        session.close();

        return list;

    }

}
