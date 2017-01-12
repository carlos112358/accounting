package br.com.accounting.accountmanager.daos;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic Repository with the basic CRUD operations
 *
 * @author Carlos
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
@Transactional(readOnly = true)
public interface GenericRepository<T, ID extends Serializable> extends Repository <T, ID> {
    @Transactional
    void delete(T deleted);

    List<T> findAll();

    Optional<T> findOne(ID id);
    
    @Transactional
    T save(T persisted);
}
