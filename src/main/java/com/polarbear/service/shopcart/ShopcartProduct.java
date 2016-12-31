package com.polarbear.service.shopcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.polarbear.domain.product.Product;

public class ShopcartProduct {
    @JsonIgnore
    private Product product;
    private Integer num;

    public ShopcartProduct() {
    }

    public ShopcartProduct(Product product, int num) {
        this.product = product;
        this.num = num;
    }

    public Long getPid() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public String getImg() {
        return product.getImage();
    }

    public Integer getNum() {
        return num;
    }

    public double getPrice() {
        return product.getRealPrice();
    }

}