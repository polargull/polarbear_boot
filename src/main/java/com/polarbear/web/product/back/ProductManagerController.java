package com.polarbear.web.product.back;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.exception.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.PageList;
import com.polarbear.service.product.ProductManagerService;
import com.polarbear.util.JsonEasyUiResult;
import com.polarbear.web.interceptor.login.AdminAuth;

@Controller
@RequestMapping("/back")
public class ProductManagerController {
    private Log log = LogFactory.getLog(ProductManagerController.class);
    @Autowired(required = false)
    private ProductManagerService productManagerSvc;

    @RequestMapping(value = { "/productList.do", "/productList.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    @AdminAuth
    public Object productList(@RequestParam(value = "param", required = false, defaultValue = "") String params, @RequestParam("page") int pageNo, @RequestParam("rows") int pageSize)
            throws ValidateException, DaoException {
        PageList<Product> pageList = productManagerSvc.productList(params, pageNo, pageSize);
        return new JsonEasyUiResult<Product>(pageList);
    }
}