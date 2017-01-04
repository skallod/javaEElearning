/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "terminal", catalog = "db1", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminal.findAll", query = "SELECT t FROM Terminal t")
    , @NamedQuery(name = "Terminal.findByTeminalId", query = "SELECT t FROM Terminal t WHERE t.teminalId = :teminalId")
    , @NamedQuery(name = "Terminal.findByAddress", query = "SELECT t FROM Terminal t WHERE t.address = :address")})
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "teminal_id", nullable = false)
    private Long teminalId;
    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terminal")
    private Collection<Operation> operationCollection;

    public Terminal() {
    }

    public Terminal(Long teminalId) {
        this.teminalId = teminalId;
    }

    public Long getTeminalId() {
        return teminalId;
    }

    public void setTeminalId(Long teminalId) {
        this.teminalId = teminalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teminalId != null ? teminalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal) object;
        if ((this.teminalId == null && other.teminalId != null) || (this.teminalId != null && !this.teminalId.equals(other.teminalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.galuzin.entity.Terminal[ teminalId=" + teminalId + " ]";
    }
    
}
