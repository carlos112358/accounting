/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "transaction")
@NamedQueries({
    @NamedQuery(name = "AccountTransaction.findAll", query = "SELECT t FROM AccountTransaction t"),
    @NamedQuery(name = "AccountTransaction.findById", query = "SELECT t FROM AccountTransaction t WHERE t.id = :id")})
public class AccountTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "entryid", referencedColumnName = "id")
    @ManyToOne
    private AccountEntry entry;
    @JoinColumn(name = "counterentryid", referencedColumnName = "id")
    @ManyToOne
    private AccountEntry counterEntry;

    public AccountTransaction() {
    }

    public AccountTransaction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountEntry getEntry() {
        return entry;
    }

    public void setEntry(AccountEntry entry) {
        this.entry = entry;
    }

    public AccountEntry getCounterEntry() {
        return counterEntry;
    }

    public void setCounterEntry(AccountEntry counterEntry) {
        this.counterEntry = counterEntry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountTransaction)) {
            return false;
        }
        AccountTransaction other = (AccountTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.accounting.accountmanager.domain.Transaction[ id=" + id + " ]";
    }
    
}
