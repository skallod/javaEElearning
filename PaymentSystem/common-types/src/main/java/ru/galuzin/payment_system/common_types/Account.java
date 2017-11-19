package ru.galuzin.payment_system.common_types;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * Created by LEONID on 14.02.2016.
 * счет - аля банковский
 */
@Entity
public class Account {
    private Long accountID;
    private Double money;
    public Person person;
    private long version;

//    private List<Operation> operationsSrcAccount;
//    private List<Operation> operationsDestAccount;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="account_gen")
    @SequenceGenerator(name = "account_gen", sequenceName = "account_id_seq")
    @Column(name="ACCOUNT_ID", unique = true, nullable = false)
    public Long getAccountID() {
        return accountID;
    }
    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }


    @Column(name = "MONEY", unique = false, nullable = false)
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PERSON",unique = false, nullable = false)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "srcAccount")
//    public List<Operation> getOperationsSrcAccount() {
//        return operationsSrcAccount;
//    }
//    public void setOperationsSrcAccount(List<Operation> operations) {
//        this.operationsSrcAccount = operations;
//    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destAccount")
//    public List<Operation> getOperationsDestAccount() {
//        return operationsDestAccount;
//    }
//    public void setOperationsDestAccount(List<Operation> operationsDestAccount) {
//        this.operationsDestAccount = operationsDestAccount;
//    }

//    int version;

//    @Version
//    @Column(name="OPTLOCK")
//    public Integer getVersion() { return version; }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", money=" + money +
                '}';
    }
}
