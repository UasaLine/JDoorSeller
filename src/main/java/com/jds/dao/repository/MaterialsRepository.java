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

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<MaterialFormula> getMaterialFormula() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from material_formula ";
        Query query = session.createSQLQuery(sql)
                .addEntity(MaterialFormula.class);
        List<MaterialFormula> materialFormulas = query.list();

        session.close();

        return materialFormulas;

    }

    public List<RawMaterials> getRawMaterials() {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from raw_materials ";
        Query query = session.createSQLQuery(sql)
                .addEntity(RawMaterials.class);
        List<RawMaterials> rawMaterialsList = query.list();

        session.close();

        return rawMaterialsList;

    }

    public List<SpecificationSetting> getSpecificationSetting(double metal, int typyDoorId) {
        Session session = sessionFactory.openSession();

        String sql = "select * from specification_setting where metal = :metalVal and doortype_id = :idDoorT";
        Query query = session.createSQLQuery(sql)
                .addEntity(SpecificationSetting.class)
                .setParameter("metalVal", metal)
                .setParameter("idDoorT", typyDoorId);
        List<SpecificationSetting> list = query.list();

        session.close();

        List<SpecificationSetting> specificationSettingList = new ArrayList<>();
        if (list.size() > 0) {
            specificationSettingList = list;
        }
        return specificationSettingList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MaterialEntity saveMaterialsEntity(MaterialEntity materials) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(materials);
        return materials;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MaterialComponents saveComponents(MaterialComponents components) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(components);

        return components;
    }

    public MaterialEntity getMaterialsByManufactureId(String idManufacturerProgram) {

        Session session = sessionFactory.openSession();

        String sql = "select * from materials where id_manufacturer_program like :idManufacturerProgram";
        Query query = session.createSQLQuery(sql)
                .addEntity(MaterialEntity.class)
                .setParameter("idManufacturerProgram", idManufacturerProgram);
        List<MaterialEntity> list = query.list();

        session.close();

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    public MaterialEntity getMaterial(int id) {
        Session session = sessionFactory.openSession();
        MaterialEntity material = session.get(MaterialEntity.class, id);
        material.clearNonSerializingFields();
        return material;
    }

    public List<MaterialEntity> getMaterials() {

        Session session = sessionFactory.openSession();

        String sql = "select * from materials";
        Query query = session.createSQLQuery(sql)
                .addEntity(MaterialEntity.class);
        List<MaterialEntity> list = query.list();

        session.close();

        list.forEach(MaterialEntity::clearNonSerializingFields);

        return list;
    }


}
