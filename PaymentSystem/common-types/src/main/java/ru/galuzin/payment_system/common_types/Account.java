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
    private Set<Operation> operations;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "srcAccount")
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
