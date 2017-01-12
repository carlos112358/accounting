package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.Owner;
import java.util.List;

/**
 * Repository for Account CRUD operations
 *
 * @author Carlos
 */
public interface AccountRepository extends GenericRepository <Account, Integer> {
    List<Account> findByOwner(Owner owner);
}
