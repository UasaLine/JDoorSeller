package com.jds.dao.repository;

import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.SellerSequence;
import com.jds.dao.entity.UserEntity;
import com.jds.model.enumModels.OrderStatus;
import com.jds.model.enumModels.SideSqlSorting;
import com.jds.model.orders.sort.OrderDateSorter;
import com.jds.model.orders.sort.OrderSorter;
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
@Transactional
public class OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<DoorOrder> getOrders() {
        OrderSorter sorter = new OrderDateSorter(SideSqlSorting.DESC);
        return getOrders(sorter, 1000, 0, 0);
    }

    public List<DoorOrder> getOrders(OrderSorter sorter, int limit, int offset, int filter) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder doorOrderListBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DoorOrder> doorOrderListCriteria = doorOrderListBuilder.createQuery(DoorOrder.class);
        Root<DoorOrder> orderRoot = doorOrderListCriteria.from(DoorOrder.class);

        doorOrderListCriteria.select(orderRoot);

        if (filter > 0) {
            doorOrderListCriteria.where(doorOrderListBuilder.equal(orderRoot.get("seller"), filter));
        }

        doorOrderListCriteria.orderBy(
                sorter.sort(doorOrderListBuilder, orderRoot));

        List<DoorOrder> list = session.createQuery(doorOrderListCriteria)
                .setMaxResults(limit)
                .setFirstResult(offset * limit)
                .getResultList();

        list.forEach(DoorOrder::clearLAZY);

        session.close();
        return list;

    }

    public long orderCountRows(OrderSorter sorter, int limit, int offset, int filter) {

        Session session = sessionFactory.openSession();
        Long total;

        if (filter > 0) {
            String countQ = "Select count (f.orderId) from DoorOrder f where f.seller.id = :id";
            Query countQuery = session.createQuery(countQ);
            countQuery.setParameter("id", filter);
            total = (Long) countQuery.uniqueResult();
        } else {
            String countQ = "Select count (f.orderId) from DoorOrder f ";
            Query countQuery = session.createQuery(countQ);
            total = (Long) countQuery.uniqueResult();
        }

        session.close();

        return total;
    }

    public long isWorkingOrderCountRows(int filter) {

        Session session = sessionFactory.openSession();
        long total;

        if (filter > 0) {
            String countQ = "Select count (f.orderId) from DoorOrder f where f.seller.id = :id and f.status = :status";
            Query countQuery = session.createQuery(countQ);
            countQuery.setParameter("id", filter);
            countQuery.setParameter("status", OrderStatus.TO_WORK);
            total = (Long) countQuery.uniqueResult();
        } else {
            total = 0;
        }

        session.close();

        return total;
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
        list.forEach(DoorOrder::clearNonSerializingFields);

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

        list.forEach(DoorOrder::clearNonSerializingFields);
        session.close();

        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DoorOrder saveOrder(DoorOrder order) {

        Session session = sessionFactory.getCurrentSession();

        if (!order.isSellerOrderIdExists()) {

            int sellerId = order.getSeller().getId();
            SellerSequence sequence = getSellerSequenceBySellerId(sellerId);

            order.setSellerOrderId(sequence.nextId());

            session.saveOrUpdate(sequence);
        }

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

        DoorOrder order = null;
        if (list.size() > 0) {
            order = list.get(0);
            order.clearNonSerializingFields();
        }
        session.close();

        return order;
    }

    private int getLastSellerId(int sellerId) {
        Session session = sessionFactory.openSession();

        String sql = "select * " +
                "from orders " +
                "where seller = :sellerId " +
                "ORDER BY seller_order_id DESC " +
                "Limit 1";
        Query query = session.createSQLQuery(sql)
                .addEntity(DoorOrder.class)
                .setParameter("sellerId", sellerId);

        List<DoorOrder> list = query.list();

        int orderSellerId = 0;
        if (list.size() > 0) {
            orderSellerId = list.get(0).getSellerOrderId();
        }
        session.close();

        return orderSellerId;
    }

    public SellerSequence getSellerSequenceBySellerId(int sellerId) {

        Session session = sessionFactory.openSession();
        SellerSequence sequence = session.byNaturalId(SellerSequence.class)
                .using("sellerId", sellerId)
                .load();

        session.close();

        if (sequence == null) {
            sequence = new SellerSequence(sellerId, getLastSellerId(sellerId));
        }
        return sequence;
    }

}
