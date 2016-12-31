package com.polarbear.service.sysjob;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderSysJob {
    @Autowired
    BaseDao<Admin> adminBaseDao;

    @Scheduled(fixedDelay = 5000)
    public void defaultUpdateOrderState() {
        Admin admin = new Admin();
        admin.setName("admin");
        admin.setPwd("123");
        try {
            adminBaseDao.store(admin);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.print(">>>>>>>>>>>>>>> sysjob fixedDelay 5 sec test >>>>>>>>>>>>>>>>");
    }
}