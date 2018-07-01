package ru.galuzin.payment_system.common_types;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by LEONID on 14.02.2016.
 */
@Entity
public class Terminal {
    private Long terminalID;
    private String address;
    private Set<Operation> operations;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="TEMINAL_ID", unique = true, nullable = false)
    public Long getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(Long terminalID) {
        this.terminalID = terminalID;
    }
    @Column(name="ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal")
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
