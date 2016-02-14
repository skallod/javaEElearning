package ru.galuzin.payment_system.common_types;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by LEONID on 14.02.2016.
 * счет
 */
@Entity
public class Account {
    private Long accountID;
    private Double money;
    private Person person;
    private Set<Operation> operationsSrcAccount;
    private Set<Operation> operationsDestAccount;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ACCOUNT_ID", unique = true, nullable = false)
    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }
    @Column(name = "MONEY", unique = false, nullable = true)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PERSON",unique = false, nullable = true)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "srcAccount")
    public Set<Operation> getOperationsSrcAccount() {
        return operationsSrcAccount;
    }

    public void setOperationsSrcAccount(Set<Operation> operations) {
        this.operationsSrcAccount = operations;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destAccount")
    public Set<Operation> getOperationsDestAccount() {
        return operationsDestAccount;
    }

    public void setOperationsDestAccount(Set<Operation> operationsDestAccount) {
        this.operationsDestAccount = operationsDestAccount;
    }
}
