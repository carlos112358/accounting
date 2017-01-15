package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountEntry;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for AccountEntry CRUD operations
 *
 * @author Carlos
 */
public interface EntryRepository extends GenericRepository <AccountEntry, Integer> {
    
    AccountEntry findFirstByAccountIdAndChargedDateLessThanEqualOrderByChargedDateDesc(Account account, Date date);
    
    @Query("SELECT e FROM AccountEntry e WHERE e.accountId = ?1 AND e.chargedDate BETWEEN ?2 AND ?3")
    public List<AccountEntry> findByAccountIdAndChargedDateBetween(Account account, Date startDate, Date endDate);
}
