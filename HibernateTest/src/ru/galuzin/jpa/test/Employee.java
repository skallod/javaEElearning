package ru.galuzin.jpa.test;

import ru.galuzin.hibernate.test.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Size(max = 10)
    @Column(length = 10, unique = false , nullable = false)
    public String firstName;
//    @Size(min = 2, max = 20)
    @Column(length = 20, unique = false , nullable = false)
    public String lastName;
    @Column(name ="boss_name", nullable = true)
    public String managerName;
    @OneToOne(mappedBy = "addressID")
//    @Column(name="address")
    public Address address;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setAddresses(Address address) {
        this.address = address;
    }
}