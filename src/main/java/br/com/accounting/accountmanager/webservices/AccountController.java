package br.com.accounting.accountmanager.webservices;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.AccountEntry;
import br.com.accounting.accountmanager.domain.AccountHistory;
import br.com.accounting.accountmanager.domain.Owner;
import br.com.accounting.accountmanager.services.AccountService;
import br.com.accounting.accountmanager.services.OwnerService;
import br.com.accounting.accountmanager.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
public class AccountController {

    private final static String dateFormat = "dd-MM-yyyy";
    private final static String timeFormat = "hh:mm:ss";
    @Autowired
    private AccountService accountService;

    @Autowired
    private OwnerService ownerService;

    @JsonView(View.Summary.class)
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") int id) {

        Account account = accountService.findById(id);//retorna um Account com id negativo caso não ache

        if (account.getId() < 0) {//não achou
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(account, HttpStatus.FOUND);
        }
    }

    //TODO: webservice que mostra o saldo da conta em uma determinada data //using Accounthistory
    @RequestMapping(value = "/account/{id}/balance", method = RequestMethod.GET)
    public ResponseEntity<AccountHistory> getBallanceAtCertainDate(@PathVariable("id") int id, @RequestParam(value = "date") String date, @RequestParam(value = "time", required = false) String time) {
        String dateTimeFormat = dateFormat;
        String dateTime = date;

        if (time != null) {
            dateTime += " " + time;
            dateTimeFormat += " " + timeFormat;
        }

        //format must be dd-MM-yyyy hh:mm:ss or only dd-MM-yyyy
        SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);

        try {
            Date data = new Date(format.parse(dateTime).getTime());
            AccountHistory account = accountService.getAccountBallanceByDate(id, data);
            return new ResponseEntity(account, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Parâmetro recebido não é uma data válida");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: webservice que mostra o saldo da conta em uma determinada data //using AccountEntry
    @RequestMapping(value = "/account/{id}/saldo", method = RequestMethod.GET)
    public ResponseEntity<AccountEntry> getBallanceAtDeterminedDate(@PathVariable("id") int id, @RequestParam(value = "date") String date, @RequestParam(value = "time", required = false) String time) {
        String dateTimeFormat = dateFormat;
        String dateTime = date;
        Account account = accountService.findById(id);
        if (time != null) {
            dateTime += " " + time;
            dateTimeFormat += " " + timeFormat;
        }

        //format must be dd-MM-yyyy hh:mm:ss or only dd-MM-yyyy
        SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);

        try {
            Date data = new Date(format.parse(dateTime).getTime());
            AccountEntry entry = accountService.getAccountBallanceAtDate(account, data);
            return new ResponseEntity(entry, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Parâmetro recebido não é uma data válida");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //until I figure out why the Owner nested object is not being desserialized I'll gave to use a request param
    @JsonView(View.Summary.class)
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account, @RequestParam(value = "owner") int ownerid) {
        Owner owner = ownerService.findById(ownerid);
        account.setOwner(owner);
        Account newAccount = accountService.save(account);
        return new ResponseEntity(newAccount, HttpStatus.OK);
    }

    //TODO: webservice que deposita/saca da conta(entries com valor negativo representam um saque)
    @RequestMapping(value = "/account/{id}", method = RequestMethod.POST)
    public ResponseEntity depositIntoAccount(@PathVariable("id") int id, @RequestParam(value = "quantity") Float quantity) {
        accountService.depositIntoAccount(id, quantity);
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO: webservice que transfere de uma conta para a outra
    @RequestMapping(value = "/account/{id}/transfer/{account}", method = RequestMethod.POST)
    public ResponseEntity transferToAccount(@PathVariable("id") int id, @PathVariable("account") int targetAccountId, @RequestParam(value = "quantity") Float quantity) {
        accountService.transferToAccount(id, targetAccountId, quantity);
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO: webservice que mostra o extrato por períodos
    @RequestMapping(value = "/account/{id}/history", method = RequestMethod.GET)
    public ResponseEntity<List<AccountEntry>> getAccountHistory(@PathVariable("id") int id, @RequestParam(value = "period") int period) {
        if (period < 0) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<AccountEntry> historyList;
        try {
            historyList = accountService.getAccountHistory(id, period);
            return new ResponseEntity(historyList, HttpStatus.OK);
        } catch (ParseException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "erro na conversão de datas", ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
