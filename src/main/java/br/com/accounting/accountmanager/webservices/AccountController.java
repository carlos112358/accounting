package br.com.accounting.accountmanager.webservices;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountHistory;
import br.com.accounting.accountmanager.domain.Entry;
import br.com.accounting.accountmanager.services.AccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") int id) {

        Account account = accountService.findById(id);//retorna um Account com id negativo caso não ache

        if (account.getId() < 0) {//não achou
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(account, HttpStatus.FOUND);
        }
    }

    //TODO: webservice que mostra o saldo da conta em uma determinada data
    @RequestMapping(value = "/account/{id}/balance/{date}", method = RequestMethod.GET)
    public ResponseEntity<AccountHistory> getBallanceAtCertainDate(@PathVariable("id") int id,
            @PathVariable("date") String date) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.save(account);
        return new ResponseEntity(newAccount, HttpStatus.OK);
    }

    //Webservice que deleta a conta? melhor não mas tá aí pra quem quiser
//    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity deleteAccount(@PathVariable("id") int id) {
//        accountService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    //TODO: webservice que deposita/saca da conta(entries com valor negativo representam um saque)
    @RequestMapping(value = "/account/{id}", method = RequestMethod.POST)
    public ResponseEntity depositIntoAccount(@PathVariable("id") int id, @RequestBody Entry entry) {
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO: webservice que transfere de uma conta para a outra
    @RequestMapping(value = "/account/transfer/{id}", method = RequestMethod.PUT)
    public ResponseEntity transferToAccount(@PathVariable("id") int id, @RequestBody Account account) {
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO: webservice que mostra o extrato por períodos
    @RequestMapping(value = "/account/{id}/history", method = RequestMethod.GET)
    public ResponseEntity<List<AccountHistory>> getAccountHistory(@PathVariable("id") int id) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
