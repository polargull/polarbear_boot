package com.polarbear.service.product.query;

import static com.polarbear.util.Constants.PRODUCT_STATE.*;
import static com.polarbear.util.Constants.ResultState.PRODUCT_NUM_IS_0;
import static com.polarbear.util.Constants.ResultState.PRODUCT_PULL_OFF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.exception.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;

@Component
public class ProductPicker {
    @Autowired
    BaseDao<Product> productDao;

    public Product pickoutTheProduct(long pid) throws DaoException, ValidateException {
        Product p = productDao.findByIdLock(Product.class, pid);
        if (p == null || p.getState().equals(PULL_OFF.value()))
            throw new ValidateException(PRODUCT_PULL_OFF);
        if (p.getNum() == 0)
            throw new ValidateException(PRODUCT_NUM_IS_0);
        return p;
    }
}