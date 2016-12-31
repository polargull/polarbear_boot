package com.polarbear.web.login.back;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.service.login.AdminLoginService;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UrlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

@Controller("adminLoginController")
@RequestMapping("/back")
public class LoginController {
    private Log log = LogFactory.getLog(LoginController.class);
    @Autowired(required = false)
    private AdminLoginService adminLoginService;
    public static final String ADMIN_LOGIN_COOKIE = "admin_login";

    @RequestMapping(value = { "login.json", "login.do" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object login(HttpServletResponse response, HttpServletRequest request, @RequestParam("uname") String uname, @RequestParam("password") String password)
            throws LoginException, DaoException, MalformedURLException {
        log.debug("uname:" + uname + ",password:" + password);
        validate(uname, password);
        LoginData<Admin> adminLoginData = adminLoginService.login(uname, password);
        setCookie(response, request, adminLoginData);
        return new JsonResult(SUCCESS).put(adminLoginData);
    }

    private void setCookie(HttpServletResponse response, HttpServletRequest request, LoginData<Admin> adminLoginData) throws MalformedURLException {
        String domain = UrlUtil.getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        CookieHelper.setCookie(response, ADMIN_LOGIN_COOKIE, adminLoginData.getAuthEncode(), domain, 0);
    }

    private void validate(String uname, String password) throws LoginException {
        if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(password)) {
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        }
    }
}
