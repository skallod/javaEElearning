/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "operation", catalog = "db1", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o")
    , @NamedQuery(name = "Operation.findByOperationId", query = "SELECT o FROM Operation o WHERE o.operationId = :operationId")
    , @NamedQuery(name = "Operation.findByDate", query = "SELECT o FROM Operation o WHERE o.date = :date")
    , @NamedQuery(name = "Operation.findByMoney", query = "SELECT o FROM Operation o WHERE o.money = :money")
    , @NamedQuery(name = "Operation.findByOperationType", query = "SELECT o FROM Operation o WHERE o.operationType = :operationType")})
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "operation_id", nullable = false)
    private Long operationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "money", nullable = false)
    private double money;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "operation_type", nullable = false, length = 255)
    private String operationType;
    @JoinColumn(name = "src_account", referencedColumnName = "account_id")
    @ManyToOne
    private Account account;
    @JoinColumn(name = "dest_account", referencedColumnName = "account_id")
    @ManyToOne
    private Account account1;
    @JoinColumn(name = "terminal_id", referencedColumnName = "teminal_id", nullable = false)
    @ManyToOne(optional = false)
    private Terminal terminal;

    public Operation() {
    }

    public Operation(Long operationId) {
        this.operationId = operationId;
    }

    public Operation(Long operationId, Date date, double money, String operationType) {
        this.operationId = operationId;
        this.date = date;
        this.money = money;
        this.operationType = operationType;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (operationId != null ? operationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.operationId == null && other.operationId != null) || (this.operationId != null && !this.operationId.equals(other.operationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.galuzin.entity.Operation[ operationId=" + operationId + " ]";
    }
    
}
