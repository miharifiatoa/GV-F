package com.sales_management_javafx.fxml.file;

import org.hibernate.Session;
import org.sales_management.session.HibernateUtil;
import org.sales_management.entity.ShopProductEntity;
import org.sales_management.interfaces.CrudInterface;

import java.util.Collection;

public class ShopProductRepository implements CrudInterface<ShopProductEntity> {
    @Override
    public ShopProductEntity create(ShopProductEntity shopProduct) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(shopProduct);
        return shopProduct;
    }

    @Override
    public ShopProductEntity getById(Long id) {
        return null;
    }

    @Override
    public ShopProductEntity deleteById(Long id) {
        return null;
    }

    @Override
    public ShopProductEntity update(ShopProductEntity obj) {
        return null;
    }

    @Override
    public Collection<ShopProductEntity> getAll() {
        return null;
    }
}
