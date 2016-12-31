package com.polarbear.service.product.create;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;

public interface IProductCreater {

	public Product create(Product product, Object... param) throws DaoException;
}
