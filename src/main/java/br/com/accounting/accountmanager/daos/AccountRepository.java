package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.Account;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for Account CRUD operations
 *
 * @author Carlos
 */
public interface AccountRepository extends GenericRepository <Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.owner.id = ?1")
    List<Account> findByOwnerId(Integer id);
    //void deleteById(Integer id);//probably should never be used
    
//    @Query("select from Account a where a.accountHistory.changeDate = ?1")
//    Integer selectAccountBalanceByDate(Date firstName);
}
