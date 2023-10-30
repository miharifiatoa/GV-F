package com.sales_management_javafx.fxml.file;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "shop_products")
public class ShopProductEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @OneToMany(mappedBy = "shopProduct" ,fetch = FetchType.EAGER)
    private Collection<ShopPriceVariationEntity> priceVariations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Collection<ShopPriceVariationEntity> getPriceVariations() {
        return priceVariations;
    }

    public void setPriceVariations(Collection<ShopPriceVariationEntity> priceVariations) {
        this.priceVariations = priceVariations;
    }
}
