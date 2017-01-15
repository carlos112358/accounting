package br.com.accounting.accountmanager.domain;

import br.com.accounting.accountmanager.views.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "account")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByOwnerId", query = "SELECT a FROM Account a WHERE a.owner.id = :id"),
    @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance")})
public class Account implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private Float balance;
    
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne
    @JsonView(View.Summary.class)
    @JsonProperty("owner")
    private Owner owner;

    @OneToMany(mappedBy = "accountId")
    @JsonIgnore
    private Collection<AccountEntry> entryCollection;
    
    @OneToMany(mappedBy = "accountId")
    @JsonIgnore
    private Collection<AccountHistory> accountHistoryCollection;
    
    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @XmlTransient
    public Collection<AccountEntry> getEntryCollection() {
        return entryCollection;
    }

    public void setEntryCollection(Collection<AccountEntry> entryCollection) {
        this.entryCollection = entryCollection;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.accounting.accountmanager.domain.Account[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<AccountHistory> getAccountHistoryCollection() {
        return accountHistoryCollection;
    }

    public void setAccountHistoryCollection(Collection<AccountHistory> accountHistoryCollection) {
        this.accountHistoryCollection = accountHistoryCollection;
    }
    
}
