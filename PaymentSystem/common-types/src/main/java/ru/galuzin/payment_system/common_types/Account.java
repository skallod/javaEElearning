package ru.galuzin.payment_system.common_types;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by LEONID on 14.02.2016.
 * счет - аля банковский
 */
@Entity
public class Account {
    private Long accountID;
    private Double money;
    private Person person;
    private List<Operation> operationsSrcAccount;
    private List<Operation> operationsDestAccount;


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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "srcAccount")
    public List<Operation> getOperationsSrcAccount() {
        return operationsSrcAccount;
    }
    public void setOperationsSrcAccount(List<Operation> operations) {
        this.operationsSrcAccount = operations;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destAccount")
    public List<Operation> getOperationsDestAccount() {
        return operationsDestAccount;
    }
    public void setOperationsDestAccount(List<Operation> operationsDestAccount) {
        this.operationsDestAccount = operationsDestAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", money=" + money +
                ", person=" + person +
                ", operationsSrcAccount=" + operationsSrcAccount +
                ", operationsDestAccount=" + operationsDestAccount +
                '}';
    }
}
