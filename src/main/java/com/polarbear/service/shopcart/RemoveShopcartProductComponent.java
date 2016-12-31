package com.polarbear.service.shopcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.domain.product.Product;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Component
public class RemoveShopcartProductComponent {
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired
    BaseDao<Shopcart> shopcartDao;

    public int removeProductFromShopcart(Product p) throws DaoException {
        User u = CurrentThreadUserFactory.getUser();
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", u);
        ShopcartDetail shopcartDetail = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, p);
        if (shopcartDetail == null) {
            return 0;
        }
        shopcartDetailDao.delete(shopcartDetail);
        return shopcartDetail.getNum();
    }

}