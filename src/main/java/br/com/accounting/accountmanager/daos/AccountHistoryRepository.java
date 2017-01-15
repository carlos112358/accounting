package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.AccountHistory;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for AccountHistory CRUD operations
 *
 * @author Carlos
 */
public interface AccountHistoryRepository extends GenericRepository <AccountHistory, Integer> {
    @Query("SELECT a FROM AccountHistory a WHERE a.accountId = ?1")
    List<AccountHistory> findByAccountId(Integer id);
    
    //Mostra o saldo em uma data determinada
    AccountHistory findFirstByAccountIdAndChangeDateLessThanOrderByChangeDateDesc(Integer id, Date date);
}
