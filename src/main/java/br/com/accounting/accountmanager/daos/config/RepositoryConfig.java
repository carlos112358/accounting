/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.daos.config;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountEntry;
import br.com.accounting.accountmanager.domain.AccountHistory;
import br.com.accounting.accountmanager.domain.AccountTransaction;
import br.com.accounting.accountmanager.domain.Owner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 *
 * @author Carlos
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Account.class, Owner.class, AccountHistory.class, AccountEntry.class, 
                AccountTransaction.class);
    }
}


