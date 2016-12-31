package com.polarbear.service.user.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.exception.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;

@Service
public class UnameUniqueValidator {
    @Autowired
    BaseDao<User> userDao;

    public void validate(String uname) throws ValidateException, DaoException {
        List<User> lst = userDao.findByNamedQuery("queryUname", uname);
        if (!lst.isEmpty())
            throw new ValidateException(ValidateException.UNAME_UNIQUE_ERR);
    }
}
