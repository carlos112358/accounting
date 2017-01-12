package br.com.accounting.accountmanager.webservices;

import br.com.accounting.accountmanager.domain.Account;
import br.com.accounting.accountmanager.domain.Owner;
import java.util.List;
import org.springframework.http.HttpStatus;
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
public class OwnerController {
    //TODO: webservice que mostra os dados de uma usuário(ou só o saldo)
    @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
    public ResponseEntity<Owner> getAccount(@RequestBody Owner owner) {
        return new ResponseEntity(HttpStatus.OK);
    }
    
    //TODO: webservice que cria usuário
    @RequestMapping(value = "/owner", method = RequestMethod.POST)
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        return new ResponseEntity(HttpStatus.OK);
    }
    
    //TODO: webservice que cancela usuário
    @RequestMapping(value = "/owner/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOwner(@PathVariable("id") int id) {
        return new ResponseEntity(HttpStatus.OK);
    }
    
    //TODO: webservice que atualiza usuário
    @RequestMapping(value = "/owner", method = RequestMethod.PUT)
    public ResponseEntity updateOwner(@RequestBody Owner owner) {
        return new ResponseEntity(HttpStatus.OK);
    }
    
    //TODO: webservice que mostra todas as contas de um usuário
    @RequestMapping(value = "/owner/{id}/accounts", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> listAccounts(@PathVariable("id") int id) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
