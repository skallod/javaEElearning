package ru.galuzin.payment_system.business.service;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.galuzin.payment_system.common_types.Account;
import ru.galuzin.payment_system.persistance.dao.BasicDAO;

import java.util.List;

/**
 * Created by User on 27.03.2016.
 */
public class AccountService {
    @Autowired
    private BasicDAO basicDao;
    @Transactional
    public void create (Account account) {
        basicDao.addEntity(account);
        double money = account.getMoney();
        money *= 1.1;
        account.setMoney(money);
        basicDao.updateEntity(account);
    }
    
    public void read (){
        java.util.Collection<Account> collection = basicDao.getAllEntities(Account.class);
        for(Account account : collection){
            System.out.println("account = " + account);
        }
    }
//            update
//    delete
//                    find
}
