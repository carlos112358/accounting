/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "entry")
@NamedQueries({
    @NamedQuery(name = "AccountEntry.findAll", query = "SELECT e FROM AccountEntry e"),
    @NamedQuery(name = "AccountEntry.findById", query = "SELECT e FROM AccountEntry e WHERE e.id = :id"),
    @NamedQuery(name = "AccountEntry.findByQuantity", query = "SELECT e FROM AccountEntry e WHERE e.quantity = :quantity"),
    @NamedQuery(name = "AccountEntry.findByChargedDate", query = "SELECT e FROM AccountEntry e WHERE e.chargedDate = :chargedDate"),
    @NamedQuery(name = "AccountEntry.findByBookingDate", query = "SELECT e FROM AccountEntry e WHERE e.bookingDate = :bookingDate")})
public class AccountEntry implements Serializable {

    @OneToMany(mappedBy = "entry")
    private Collection<AccountTransaction> transactionCollection;
    @OneToMany(mappedBy = "counterEntry")
    private Collection<AccountTransaction> transactionCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quantity")
    private Float quantity;
    @Column(name = "charged_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chargedDate;
    @Column(name = "booking_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    private Account accountId;
    
    @Column(name = "account_balance")
    private Float accountBalance;
    
    public AccountEntry() {
    }

    public AccountEntry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Date getChargedDate() {
        return chargedDate;
    }

    public void setChargedDate(Date chargedDate) {
        this.chargedDate = chargedDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
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
        if (!(object instanceof AccountEntry)) {
            return false;
        }
        AccountEntry other = (AccountEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.accounting.accountmanager.domain.Entry[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<AccountTransaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<AccountTransaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    @XmlTransient
    public Collection<AccountTransaction> getTransactionCollection1() {
        return transactionCollection1;
    }

    public void setTransactionCollection1(Collection<AccountTransaction> transactionCollection1) {
        this.transactionCollection1 = transactionCollection1;
    }

    public Float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }
    
}
