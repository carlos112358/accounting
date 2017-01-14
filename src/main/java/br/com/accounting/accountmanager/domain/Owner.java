/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "owner")
@NamedQueries({
    @NamedQuery(name = "Owner.findAll", query = "SELECT o FROM Owner o"),
    @NamedQuery(name = "Owner.findById", query = "SELECT o FROM Owner o WHERE o.id = :id"),
    @NamedQuery(name = "Owner.findByNome", query = "SELECT o FROM Owner o WHERE o.nome = :nome"),
    @NamedQuery(name = "Owner.findByCpf", query = "SELECT o FROM Owner o WHERE o.cpf = :cpf"),
    @NamedQuery(name = "Owner.findByTelefone", query = "SELECT o FROM Owner o WHERE o.telefone = :telefone"),
    @NamedQuery(name = "Owner.findByEndereco", query = "SELECT o FROM Owner o WHERE o.endereco = :endereco"),
    @NamedQuery(name = "Owner.findByOwnercol", query = "SELECT o FROM Owner o WHERE o.ownercol = :ownercol")})
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 45)
    @Column(name = "CPF")
    private String cpf;
    @Size(max = 45)
    @Column(name = "telefone")
    private String telefone;
    @Size(max = 45)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 45)
    @Column(name = "ownercol")
    private String ownercol;
    @OneToMany(mappedBy = "owner")
    private Collection<Account> accountCollection;

    public Owner() {
    }

    public Owner(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getOwnercol() {
        return ownercol;
    }

    public void setOwnercol(String ownercol) {
        this.ownercol = ownercol;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.accounting.accountmanager.domain.Owner[ id=" + id + " ]";
    }
    
}
