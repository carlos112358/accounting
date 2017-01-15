package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountEntry;
import java.util.Date;

/**
 * Repository for AccountEntry CRUD operations
 *
 * @author Carlos
 */
public interface EntryRepository extends GenericRepository <AccountEntry, Integer> {
    
    AccountEntry findFirstByAccountIdAndChargedDateLessThanOrderByChargedDateDesc(Account account, Date date);
}
