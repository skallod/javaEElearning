package ru.galuzin.payment_system.common_types;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by LEONID on 14.02.2016.
 */
@Entity
public class Person {
    private Long personID;
    private String name;
    private String lastName;
    private Set<Account> accounts;
    private String address;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PERSON_ID", unique = true, nullable = false)
    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    @Column(name="NAME",unique = false, nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "LAST_NAME", unique = false, nullable = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    @Column(name="ADDRESS", unique = false, nullable = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
