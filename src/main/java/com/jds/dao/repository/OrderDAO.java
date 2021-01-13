package com.jds.dao.repository;

import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.orders.sort.OrderDateSorter;
import com.jds.model.orders.sort.OrderSorter;
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
public class OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<DoorOrder> getOrders() {
        OrderSorter sorter = new OrderDateSorter();
        return getOrders(sorter);
    }

    public List<DoorOrder> getOrders(OrderSorter sorter) {

        String sql = sorter.sort("select * from orders ");

        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class);

        List<DoorOrder> list = query.list();

        session.close();

        list.forEach(DoorOrder::clearNonSerializingFields);

        return list;
    }

    public List<DoorOrder> getOrdersByStatus(String status) {

        Session session = sessionFactory.openSession();

        String sql = "select * " +
                "from orders " +
                "where status like :status";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class)
                .setParameter("status", status);

        List<DoorOrder> list = query.list();

        session.close();

        return list;

    }

    public List<DoorOrder> getOrdersByUser(UserEntity user) {

        Session session = sessionFactory.openSession();

        String sql = "select * from orders where seller = :valId";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class)
                .setParameter("valId", user.getId());

        List<DoorOrder> list = query.list();

        session.close();

        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DoorOrder saveOrder(DoorOrder order) {
        if (order.getSellerOrderId() == 0) {
            int nextSellerId = getNextSellerId(order.getSeller());
            order.setSellerOrderId(nextSellerId);
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(order);

        return order;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrder(DoorOrder order) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(order);

    }

    public DoorOrder getOrder(int id) {

        Session session = sessionFactory.openSession();

        String sql;
        sql = "select * from orders where order_id = :log";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class)
                .setParameter("log", id);
        List<DoorOrder> list = query.list();

        session.close();

        DoorOrder order = null;
        if (list.size() > 0) {
            order = list.get(0);
        }

        order.clearNonSerializingFields();
        return order;
    }

    private int getNextSellerId(UserEntity seller) {
        Session session = sessionFactory.openSession();

        String sql = "select * " +
                "from orders " +
                "where seller = :sellerId " +
                "ORDER BY seller_order_id DESC " +
                "Limit 1";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class)
                .setParameter("sellerId", seller.getId());

        List<DoorOrder> list = query.list();

        int sellerId = 0;
        if (list.size() > 0) {
            sellerId = list.get(0).getSellerOrderId();
        }
        session.close();

        return sellerId + 1;
    }

}
