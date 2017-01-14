package br.com.accounting.accountmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "accounthistory")
@NamedQueries({
    @NamedQuery(name = "AccountHistory.findAll", query = "SELECT a FROM AccountHistory a"),
    @NamedQuery(name = "AccountHistory.findByAccountId", query = "SELECT a FROM AccountHistory a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "AccountHistory.findByBalance", query = "SELECT a FROM AccountHistory a WHERE a.balance = :balance"),
    @NamedQuery(name = "AccountHistory.findByChangeDate", query = "SELECT a FROM AccountHistory a WHERE a.changeDate = :changeDate")})
public class AccountHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
//    @NotNull
    @Column(name = "account_id")
    private Integer accountId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private Float balance;
    @Column(name = "change_date")
//    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="dd-MM-yyyy hh:mm:ss", timezone="Brazil/East")
    private Date changeDate;
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;

    public AccountHistory() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountHistory(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        if (!(object instanceof AccountHistory)) {
            return false;
        }
        AccountHistory other = (AccountHistory) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.accounting.accountmanager.domain.AccountHistory[ accountId=" + accountId + " ]";
    }
    
}
