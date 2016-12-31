package com.polarbear.web.exception;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.NULLPOINTER;
import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.URL_FORMATE_ERR;

import java.net.MalformedURLException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.exception.NullObjectException;
import com.polarbear.exception.ParseException;
import com.polarbear.exception.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.JsonResult;

@ControllerAdvice
public class MVCExceptionHandler {
    private Log log = LogFactory.getLog(MVCExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    Object handleRequestParameterMissException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + PARAM_ERR.emsg());
        return new JsonResult(PARAM_ERR);
    }

    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Object handleBindException(BindException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + PARAM_ERR.emsg());
        return new JsonResult(PARAM_ERR);
    }

    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody
    Object handleRequestException(NullPointerException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + NULLPOINTER);
        return new JsonResult(NULLPOINTER);
    }

    @ExceptionHandler(ValidateException.class)
    public @ResponseBody
    Object handleValidateException(ValidateException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }

    @ExceptionHandler(ParseException.class)
    public @ResponseBody
    Object handleParseException(ParseException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }

    @ExceptionHandler(NullObjectException.class)
    public @ResponseBody
    Object handleNullObjectException(NullObjectException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }

    @ExceptionHandler(DaoException.class)
    public @ResponseBody
    Object handleDaoException(DaoException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }

    @ExceptionHandler(LoginException.class)
    public @ResponseBody
    Object handleLoginException(LoginException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + LOGIN_NAME_PWD_ERR);
        return new JsonResult(LOGIN_NAME_PWD_ERR);
    }

    @ExceptionHandler(MalformedURLException.class)
    public @ResponseBody
    Object handleMalformedURLException(MalformedURLException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + URL_FORMATE_ERR);
        return new JsonResult(URL_FORMATE_ERR);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public @ResponseBody
    Object handleException(TypeMismatchException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + PARAM_ERR.emsg());
        return new JsonResult(PARAM_ERR);
    }

    @ExceptionHandler(OrderStateException.class)
    public @ResponseBody
    Object handleException(OrderStateException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }

}