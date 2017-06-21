package ru.galuzin.payment_system.common_types;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by LEONID on 14.02.2016.
 * Операция с деньгами на счете
 * Три вида операций:
 * 1. Перевод с счета на счет
 * 2. Положить деньги на счет
 * 3. снять деньги со счета
 */
@Entity
public class Operation {
    private Long operationID;
    /**
     * аккаунт, с которого "уходят" деньги.
     * когда operationType = PUT_MONEY , srcAccount=null.
     */
    //private Account srcAccount;
    /**
     * аккаунт, на который "приходят" деньги.
     * когда operationType = GET_MONEY , destAccount=null.
     */
    //private Account destAccount;
    private String operationType;
    private Terminal terminal;
    private Double money;
    private Date date;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="OPERATION_ID", unique = true , nullable = false)
    public Long getOperationID() {
        return operationID;
    }

    public void setOperationID(Long operationID) {
        this.operationID = operationID;
    }

//    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, targetEntity = Account.class)
//    @JoinColumn(name="SRC_ACCOUNT", unique = false,nullable = true)
//    public Account getSrcAccount() {
//        return srcAccount;
//    }
//    public void setSrcAccount(Account srcAccount) {
//        this.srcAccount = srcAccount;
//    }
//
//    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, targetEntity = Account.class)
//    @JoinColumn(name="DEST_ACCOUNT", unique = false,nullable = true)
//    public Account getDestAccount() {
//        return destAccount;
//    }
//    public void setDestAccount(Account destAccount) {
//        this.destAccount = destAccount;
//    }

    @Column(name="OPERATION_TYPE", unique = false, nullable = false)
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERMINAL_ID", nullable = false)
    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
    @Column(name = "MONEY", unique = false, nullable = false)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="DATE", unique = false, nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
