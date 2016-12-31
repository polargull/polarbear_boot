package com.polarbear.web.shopcart;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.exception.ParseException;
import com.polarbear.exception.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.service.shopcart.ShopcartService;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UrlUtil;

@Controller
@RequestMapping("/shopcart")
public class ShopcartController {
    private Log log = LogFactory.getLog(ShopcartController.class);
    public static String SHOPCART_PRODUCT_NUM = "productNum";
    public static String SHOPCART = "shopcart";

    @Autowired(required = false)
    private ShopcartService shopcartService;

    @RequestMapping(value = { "/addShopcart.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object addShopcart(HttpServletResponse response, HttpServletRequest request, @RequestParam("pid") String pid) throws ValidateException, DaoException, MalformedURLException {
        log.debug("pid=" + pid);
        validate(pid);
        int count = shopcartService.addProductToShopcart(Long.valueOf(pid));
        String domain = UrlUtil.getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        CookieHelper.setCookie(response, SHOPCART_PRODUCT_NUM, String.valueOf(count), domain, 0);
        log.debug("pid = " + pid + ", op successful!");
        return new JsonResult(SUCCESS);
    }

    @RequestMapping(value = { "/getMyShopcart.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object getMyShopcart(HttpServletRequest request) throws ValidateException, DaoException, ParseException {        
        MyShopcart myShopcart = shopcartService.getMyShopcart(CookieHelper.getCookieValue(request, SHOPCART));
        return new JsonResult(SUCCESS).put(myShopcart);
    }

    @RequestMapping(value = { "/removeProduct.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object removeProduct(@RequestParam("pid") String pid) throws ValidateException, DaoException {
        validate(pid);
        MyShopcart myShopcart = shopcartService.deleteProductFromShopcart(Long.valueOf(pid));
        return new JsonResult(SUCCESS).put(myShopcart);
    }

    @RequestMapping(value = { "/modifyProductNum.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object modifyProductNum(@RequestParam("pid") String pid, @RequestParam("num") String num) throws ValidateException, DaoException {
        validate(pid);
        validate(num);
        MyShopcart myShopcart = shopcartService.modifyProductNumFromShopcart(Long.parseLong(pid), Integer.parseInt(num));
        return new JsonResult(SUCCESS).put(myShopcart);
    }

    private void validate(String digits) throws ValidateException {
        if (!NumberUtils.isDigits(digits)) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}