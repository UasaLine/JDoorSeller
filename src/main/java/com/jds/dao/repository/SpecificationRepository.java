package com.jds.dao.repository;

import com.jds.dao.entity.*;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SpecificationRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public SpecificationEntity getSpecificationByManufacturerId(String manufacturerId) {

        Session session = sessionFactory.openSession();
        SpecificationEntity spec = session.byNaturalId(SpecificationEntity.class)
                .using("manufacturerId", manufacturerId)
                .load();

        session.close();

        return spec;
    }

    public SpecificationEntity getSpecificationEntityById(@NonNull int id) {
        Session session = sessionFactory.openSession();
        SpecificationEntity specification = session.get(SpecificationEntity.class, id);
        specification.clearNonSerializingFields();

        session.close();

        return specification;
    }

    public SpecificationEntity getSpecificationEntityByDoorType(@NonNull int doorTypeId) {

        Session session = sessionFactory.openSession();

        String sql = "select * from specification where doortype_id = :id";
        Query query = session.createSQLQuery(sql)
                .setParameter("id", doorTypeId)
                .addEntity(SpecificationEntity.class);
        List<SpecificationEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public SpecificationEntity getSpecificationByDoorId(int id) {

        Session session = sessionFactory.openSession();

        String sql = "select * from specification where door_id = :id";
        Query query = session.createSQLQuery(sql)
                .setParameter("id", id)
                .addEntity(SpecificationEntity.class);
        List<SpecificationEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            SpecificationEntity spec = list.get(0);
            spec.clearNonSerializingFields();
            return spec;
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpecificationEntity saveSpecificationEntity(SpecificationEntity specification) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(specification);

        specification.setSpecificationToAllLine();

        return specification;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteSpecificationEntity(@NonNull SpecificationEntity specification) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(specification);

        return "ok";

    }


    public LineSpecification getSpecificationLineById(@NonNull int id) {

        Session session = sessionFactory.openSession();
        LineSpecification lineSpecification = session.get(LineSpecification.class, id);

        lineSpecification.clearNonSerializingFields();

        session.close();

        return lineSpecification;
    }

    public List<LineSpecification> getLineSpecificationList() {

        Session session = sessionFactory.openSession();

        String sql = "select * from line_specification";
        Query query = session.createSQLQuery(sql)
                .addEntity(LineSpecification.class);
        List<LineSpecification> list = query.list();

        session.close();

        return list;
    }

    public List<SpecificationEntity> getSpecification() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<SpecificationEntity> criteria = builder.createQuery(SpecificationEntity.class);
        Root<SpecificationEntity> root = criteria.from(SpecificationEntity.class);
        criteria.select(root);

        criteria.where(builder.equal(root.get("doorId"), 0));
        List<SpecificationEntity> list = session.createQuery(criteria).getResultList();

        session.close();

        list.forEach(SpecificationEntity::clearNonSerializingFields);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LineSpecification saveLineSpecification(LineSpecification lineSpec) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(lineSpec);

        return lineSpec;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteLineSpecification(LineSpecification lineSpecification) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(lineSpecification);

    }
}
