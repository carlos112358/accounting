/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.services;

import br.com.accounting.accountmanager.daos.AccountHistoryRepository;
import br.com.accounting.accountmanager.daos.AccountRepository;
import br.com.accounting.accountmanager.daos.EntryRepository;
import br.com.accounting.accountmanager.daos.TransactionRepository;
import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountHistory;
import br.com.accounting.accountmanager.domain.AccountEntry;
import br.com.accounting.accountmanager.domain.AccountTransaction;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carlos
 */
@Repository
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository historyRepository;

    @Autowired
    private EntryRepository entryRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    //cria/atualiza conta
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    //lista todas as contas do usuário
    public List<Account> findByOwner(Integer ownerId) {
        return accountRepository.findByOwnerId(ownerId);
    }

    //retorna uma determinada conta por ID
    public Account findById(int id) {
        Optional<Account> opAccount = accountRepository.findOne(id);

        if (accountRepository.findOne(id).isPresent()) {
            return opAccount.get();
        } else {
            //retornando um objeto Account com id -1 em caso de erro.
            return new Account(-1);
        }
    }

    public List<AccountHistory> getAccountHistory(int id) {
        return historyRepository.findByAccountId(id);
    }
    
    //USES ACCOUNT HISTORY
    public AccountHistory getAccountBallanceByDate(int id, Date date) {
        return historyRepository.findFirstByAccountIdAndChangeDateLessThanOrderByChangeDateDesc(id, date);
    }
    
    //USES ACCOUNT ENTRY
    public AccountEntry getAccountBallanceAtDate(Account account, Date date) {
        return entryRepository.findFirstByAccountIdAndChargedDateLessThanOrderByChargedDateDesc(account, date);
    }

    public AccountEntry depositIntoAccount(int id, Float value) {
        
        Account account = findById(id);
                
        if (account.getId() < 0) {
            return null;
        }

        Date currentDate = new Date();

        float balance = account.getBalance()+value;
        //first we create an entry object for this ballance
        AccountEntry entry = new AccountEntry();
        entry.setQuantity(value);
        entry.setBookingDate(currentDate);
        
        //for now we'll work with the current date
        entry.setChargedDate(currentDate);
        entry.setAccountBalance(value);
        entry.setAccountBalance(balance);
        entry.setAccountId(account);
        entry = entryRepository.save(entry);
        
        account.setBalance(balance);
        
        //we're using this class just cause
        AccountHistory history = new AccountHistory();
        history.setAccountId(id);
        history.setBalance(balance);
        history.setChangeDate(currentDate);
        historyRepository.save(history);

        accountRepository.save(account);
        return entry;
    }
    
    public void transferToAccount(int originAccountid, int destinyAccountid, Float value){
        AccountEntry withdraw = depositIntoAccount(originAccountid, value * -1);
        AccountEntry addition = depositIntoAccount(destinyAccountid, value);
        
        AccountTransaction transaction = new AccountTransaction();
        transaction.setEntry(addition);
        transaction.setCounterEntry(withdraw);
        transactionRepository.save(transaction);
    }
}
