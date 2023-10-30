package com.sales_management_javafx.fxml.file;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sales_management.HibernateUtil;
import org.sales_management.entity.ShopProductEntity;
import org.sales_management.interfaces.CrudInterface;
import org.sales_management.repository.ShopProductRepository;

import java.util.Collection;

public class ShopProductService implements CrudInterface<ShopProductEntity> {
    private final ShopProductRepository shopProductRepository;

    public ShopProductService() {
        this.shopProductRepository = new ShopProductRepository();
    }

    @Override
    public ShopProductEntity create(ShopProductEntity shopProduct) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            this.shopProductRepository.create(shopProduct);
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
        }
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
