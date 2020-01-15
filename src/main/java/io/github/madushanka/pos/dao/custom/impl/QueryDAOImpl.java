package io.github.madushanka.pos.dao.custom.impl;


import io.github.madushanka.pos.dao.custom.QueryDAO;
import io.github.madushanka.pos.entity.CustomEntity;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {
       return (CustomEntity) session.createQuery("SELECT NEW io.github.madushanka.pos.entity.CustomEntity(C.customerId, C.name , O.date)  FROM Customer C INNER JOIN C.`Order` O WHERE O.id=?").setParameter(1,orderId).uniqueResult();
    }

    @Override
    public CustomEntity getOrderInfo2(int orderId) throws Exception {
        return (CustomEntity) session.createQuery("SELECT NEW io.github.madushanka.pos.entity.CustomEntity( O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unitPrice)) FROM Customer C INNER JOIN C.`Order` O INNER JOIN O.OrderDetail OD  WHERE O.id=? GROUP BY orderId").setParameter(1,orderId).uniqueResult();
    }

    @Override
    public List<CustomEntity> getOrdersInfo(String query) throws Exception {
        return session.createQuery("SELECT NEW io.github.madushanka.pos.entity.CustomEntity(O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unitPrice)) FROM Customer C INNER JOIN C.`Order` O INNER JOIN O.OrderDetail OD  WHERE O.id LIKE ? OR C.customerId LIKE ? OR C.name LIKE ? OR O.date LIKE ? GROUP BY O.id")
                .setParameter(1,query)
                .setParameter(2,query)
                .setParameter(3,query)
                .setParameter(4,query).list();
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }
}
