/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Medeiros
 */
@Entity
@Table(name = "estoque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estoque.findAll", query = "SELECT e FROM Estoque e"),
    @NamedQuery(name = "Estoque.findByIdEstoque", query = "SELECT e FROM Estoque e WHERE e.idEstoque = :idEstoque"),
    @NamedQuery(name = "Estoque.findByNome", query = "SELECT e FROM Estoque e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estoque.findByLoc", query = "SELECT e FROM Estoque e WHERE e.loc = :loc"),
    @NamedQuery(name = "Estoque.findByDescricao", query = "SELECT e FROM Estoque e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Estoque.findByEndereco", query = "SELECT e FROM Estoque e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "Estoque.findByTelefone", query = "SELECT e FROM Estoque e WHERE e.telefone = :telefone")})
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estoque")
    private Integer idEstoque;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "loc")
    private String loc;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "telefone")
    private String telefone;
    @OneToMany(mappedBy = "est")
    private Collection<Produto> produtoCollection;

    public Estoque() {
    }

    public Estoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }

    public Estoque(Integer idEstoque, String nome) {
        this.idEstoque = idEstoque;
        this.nome = nome;
    }

    public Integer getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @XmlTransient
    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstoque != null ? idEstoque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estoque)) {
            return false;
        }
        Estoque other = (Estoque) object;
        if ((this.idEstoque == null && other.idEstoque != null) || (this.idEstoque != null && !this.idEstoque.equals(other.idEstoque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estoque[ idEstoque=" + idEstoque + " ]";
    }
    
}
