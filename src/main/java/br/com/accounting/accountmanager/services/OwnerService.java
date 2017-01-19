package br.com.accounting.accountmanager.services;

import br.com.accounting.accountmanager.daos.OwnerRepository;
import br.com.accounting.accountmanager.domain.Owner;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Carlos
 */
@Repository
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    //cria/atualiza usuário
    @Transactional
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    //retorna uma determinada usuário por ID
    public Owner findById(int id) {
        Optional<Owner> opOwner = ownerRepository.findOne(id);

        if (ownerRepository.findOne(id).isPresent()) {
            return opOwner.get();
        } else {
            //retornando um objeto Owner com id -1 em caso de erro.
            return new Owner(-1);
        }
    }
}
