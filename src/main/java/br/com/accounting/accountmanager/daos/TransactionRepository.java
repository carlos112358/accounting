package br.com.accounting.accountmanager.daos;

import br.com.accounting.accountmanager.domain.AccountTransaction;

/**
 * Repository for Transaction CRUD operations
 *
 * @author Carlos
 */
public interface TransactionRepository extends GenericRepository <AccountTransaction, Integer> {
}
