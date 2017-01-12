/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.services;

import br.com.accounting.accountmanager.daos.AccountRepository;
import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.Owner;
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
    private AccountRepository repository;
    //cria/atualiza uma conta

    @Transactional
    public Account save(Account account) {
        return repository.save(account);
    }
    
//    @Transactional
//    public void delete(int id) {
//        repository.deleteById(id);
//    }

    //lista todas as contas do usuário
    public List<Account> findByOwner(Integer ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    //retorna uma determinada conta por ID
    public Account findById(int id) {
        Optional<Account> opAccount = repository.findOne(id);
        
        if(repository.findOne(id).isPresent()){
            return opAccount.get();
        } else {
            //retornando um objeto Account com id -1 em caso de erro.
            return new Account(-1);
        }
    }

}
