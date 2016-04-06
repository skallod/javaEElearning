//package ru.galuzin.payment_system.spring.business.service;
//
//import org.apache.commons.logging.Log;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import ru.galuzin.payment_system.spring.common_types.Account;
//import ru.galuzin.payment_system.spring.persistance.dao.BasicDAO;
//
///**
// * Created by User on 27.03.2016.
// */
//public class AccountService {
//    final Logger LOG = LoggerFactory.getLogger(AccountService.class);
//    @Autowired
//    private BasicDAO basicDao;
//    @Transactional
//    public void create (Account account) {
//        basicDao.addEntity(account);
//        double money = account.getMoney();
//        money *= 1.1;
//        account.setMoney(money);
//        basicDao.updateEntity(account);
//    }
//
//    public void read (){
//        LOG.info("read");
//        java.util.Collection<Account> collection = basicDao.getAllEntities(Account.class);
//        for(Account account : collection){
//            LOG.info("account = " + account);
//        }
//    }
//}
