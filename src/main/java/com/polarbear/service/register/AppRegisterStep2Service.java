package com.polarbear.service.register;

import com.polarbear.exception.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.util.date.DateUtil;
import com.polarbear.util.date.IClock;
import com.polarbear.util.date.SystemClock;
import com.polarbear.util.security.MD5Util;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import static com.polarbear.service.register.util.VerifyCodeDecoder.*;
import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.VERIFY_CODE_INVIDIT;

@Service
public class AppRegisterStep2Service {
    @Autowired
    private BaseDao<User> userDao;
    IClock                systemClock = new SystemClock();

    public LoginData<User> completeStep2(int verifyCode, String encodeVerifyCode, String pwd) throws ValidateException, NumberFormatException, DaoException, UnsupportedEncodingException {
        validateVerifyCode(verifyCode, decodeVerifyCode(encodeVerifyCode), decodeVerifyCodeCreateTime(encodeVerifyCode));
        validateCellphone(decodeCellphone(encodeVerifyCode));
        User user = storeUser(Long.valueOf(decodeCellphone(encodeVerifyCode)), pwd);
        return new LoginData<User>(user);
    }

    private void validateVerifyCode(int verifyCode, String encodeVerifyCode, long decodeVerifyCodeCreateTime) throws ValidateException {
        if (verifyCode != Integer.parseInt(encodeVerifyCode)) {
            throw new ValidateException(PARAM_ERR);
        }
        if (systemClock.now().getMillis() - decodeVerifyCodeCreateTime >= 1000 * 60) {
            throw new ValidateException(VERIFY_CODE_INVIDIT);
        }
    }

    private void validateCellphone(String cellphone) throws ValidateException {
        if (!NumberUtils.isDigits(cellphone) || cellphone.trim().length() != 11) {
            throw new ValidateException(PARAM_ERR);
        }
    }

    private User storeUser(long cellPhone, String pwd) throws DaoException {
        String md5_pwd = MD5Util.encode2hex(pwd);
        User user = new User(String.valueOf(cellPhone), md5_pwd, cellPhone, DateUtil.getCurrentSeconds());
        userDao.store(user);
        return user;
    }


}
