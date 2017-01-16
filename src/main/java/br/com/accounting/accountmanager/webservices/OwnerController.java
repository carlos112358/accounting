package br.com.accounting.accountmanager.webservices;

import br.com.accounting.accountmanager.domain.Owner;
import br.com.accounting.accountmanager.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private OwnerService ownerService;
    
    //TODO: webservice que mostra os dados de uma usuário(ou só o saldo)
    @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
    public ResponseEntity<Owner> getOwner(@PathVariable("id") int ownerid) {
        Owner owner = ownerService.findById(ownerid);
        return new ResponseEntity(owner, HttpStatus.OK);
    }
    
    //TODO: webservice que cria usuário
    @RequestMapping(value = "/owner", method = RequestMethod.POST)
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner responseOwner = ownerService.save(owner);
        return new ResponseEntity(responseOwner, HttpStatus.OK);
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
}
