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
import br.com.accounting.accountmanager.domain.AccountStatement;
import br.com.accounting.accountmanager.domain.AccountTransaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    public List<AccountStatement> getAccountHistory(int id, int period) throws ParseException {
        Account account = findById(id);
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", new Locale("pt", "BR"));
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_YEAR, -period);
        String strDate = format2.format(cal.getTime()) + " 00:00:00";
        Date startDate = format2.parse(strDate);
        
        List<AccountEntry> entryList = entryRepository.findByAccountIdAndChargedDateBetween(account, startDate, endDate);
        
        Map<String, List<AccountEntry>> historyMap = new HashMap<>();
        
        //montamos um mapa com o dia e inserimos em cada dia as movimentações feitas na conta
        entryList.forEach(e ->{
            String day = format2.format(e.getChargedDate());
            if(historyMap.get(day) == null){
                List<AccountEntry> list = new ArrayList<>();
                list.add(e);
                historyMap.put(day, list);
            } else {
                historyMap.get(day).add(e);
            }
        });
        
        //agora precisamos ordenar o mapa pelas chaves
        Map<String, List<AccountEntry>> sortedHistoryMap = new LinkedHashMap<>();
        historyMap.entrySet().stream()
                .sorted(Map.Entry.<String, List<AccountEntry>>comparingByKey())
                .forEachOrdered(x -> sortedHistoryMap.put(x.getKey(), x.getValue()));
        
        //utilizando a nova estrutura
        List <AccountStatement> accountStatement= new ArrayList<>();
        
        for(String key:sortedHistoryMap.keySet()){
            AccountStatement statement = new AccountStatement();
            statement.setEntries(sortedHistoryMap.get(key));
            statement.retrieveRetrieveData();
            accountStatement.add(statement);
        };
        
        Collections.reverse(accountStatement);
        return accountStatement;
    }

    //USES ACCOUNT HISTORY
    public AccountHistory getAccountBallanceByDate(int id, Date date) {
        return historyRepository.findFirstByAccountIdAndChangeDateLessThanOrderByChangeDateDesc(id, date);
    }

    //USES ACCOUNT ENTRY
    public AccountEntry getAccountBallanceAtDate(Account account, Date date) {
        return entryRepository.findFirstByAccountIdAndChargedDateLessThanEqualOrderByChargedDateDesc(account, date);
    }

    public AccountEntry depositIntoAccount(int id, Float value) {

        Account account = findById(id);

        if (account.getId() < 0) {
            return null;
        }

        Date currentDate = new Date();

        float balance = account.getBalance() + value;
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

    public void transferToAccount(int originAccountid, int destinyAccountid, Float value) {
        AccountEntry withdraw = depositIntoAccount(originAccountid, value * -1);
        AccountEntry addition = depositIntoAccount(destinyAccountid, value);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setEntry(addition);
        transaction.setCounterEntry(withdraw);
        transactionRepository.save(transaction);
    }
}
