/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "account", catalog = "db1", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId")
    , @NamedQuery(name = "Account.findByMoney", query = "SELECT a FROM Account a WHERE a.money = :money")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "money", nullable = false)
    private double money;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operationCollection;
    @OneToMany(mappedBy = "account1")
    private Collection<Operation> operationCollection1;
    @JoinColumn(name = "person", referencedColumnName = "person_id", nullable = false)
    @ManyToOne(optional = false)
    private Person person;

    public Account() {
    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account(Long accountId, double money) {
        this.accountId = accountId;
        this.money = money;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection1() {
        return operationCollection1;
    }

    public void setOperationCollection1(Collection<Operation> operationCollection1) {
        this.operationCollection1 = operationCollection1;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.galuzin.entity.Account[ accountId=" + accountId + " ]";
    }
    
}
