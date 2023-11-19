package com.sales_management_javafx.fxml.file;

import org.hibernate.Session;
import org.sales_management.session.HibernateUtil;
import org.sales_management.entity.ShopPriceVariationEntity;
import org.sales_management.interfaces.CrudInterface;

import java.util.Collection;

public class ShopPriceVariationRepository implements CrudInterface<ShopPriceVariationEntity> {
    @Override
    public ShopPriceVariationEntity create(ShopPriceVariationEntity shopPriceVariation) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(shopPriceVariation);
        return shopPriceVariation;
    }

    @Override
    public ShopPriceVariationEntity getById(Long id) {
        return null;
    }

    @Override
    public ShopPriceVariationEntity deleteById(Long id) {
        return null;
    }

    @Override
    public ShopPriceVariationEntity update(ShopPriceVariationEntity obj) {
        return null;
    }

    @Override
    public Collection<ShopPriceVariationEntity> getAll() {
        return null;
    }
}
