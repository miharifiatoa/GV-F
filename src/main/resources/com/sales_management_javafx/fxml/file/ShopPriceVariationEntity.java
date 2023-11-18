package com.sales_management_javafx.fxml.file;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "shop_price_variations")
public class ShopPriceVariationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shop_product_id")
    private ShopProductEntity shopProduct;
    @ManyToOne
    @JoinColumn(name = "price_variation_id")
    private ArticleEntity priceVariation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopProductEntity getShopProduct() {
        return shopProduct;
    }

    public void setShopProduct(ShopProductEntity shopProduct) {
        this.shopProduct = shopProduct;
    }

    public ArticleEntity getPriceVariation() {
        return priceVariation;
    }

    public void setPriceVariation(ArticleEntity priceVariation) {
        this.priceVariation = priceVariation;
    }
}
