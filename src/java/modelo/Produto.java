/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Medeiros
 */
@Entity
@Table(name = "produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByIdProduto", query = "SELECT p FROM Produto p WHERE p.idProduto = :idProduto"),
    @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome"),
    @NamedQuery(name = "Produto.findByUnidade", query = "SELECT p FROM Produto p WHERE p.unidade = :unidade"),
    @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Produto.findByStatus", query = "SELECT p FROM Produto p WHERE p.status = :status"),
    @NamedQuery(name = "Produto.findByLicitavel", query = "SELECT p FROM Produto p WHERE p.licitavel = :licitavel"),
    @NamedQuery(name = "Produto.findByDatavencLic", query = "SELECT p FROM Produto p WHERE p.datavencLic = :datavencLic")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_produto")
    private Integer idProduto;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "unidade")
    private String unidade;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "status")
    private String status;
    @Column(name = "licitavel")
    private String licitavel;
    @Column(name = "data_vencLic")
    @Temporal(TemporalType.DATE)
    private Date datavencLic;
    @JoinColumn(name = "est", referencedColumnName = "id_estoque")
    @ManyToOne
    private Estoque est;
    @OneToMany(mappedBy = "prod")
    private Collection<Lote> loteCollection;

    public Produto() {
    }

    public Produto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Produto(Integer idProduto, String nome) {
        this.idProduto = idProduto;
        this.nome = nome;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicitavel() {
        return licitavel;
    }

    public void setLicitavel(String licitavel) {
        this.licitavel = licitavel;
    }

    public Date getDatavencLic() {
        return datavencLic;
    }

    public void setDatavencLic(Date datavencLic) {
        this.datavencLic = datavencLic;
    }

    public Estoque getEst() {
        return est;
    }

    public void setEst(Estoque est) {
        this.est = est;
    }

    @XmlTransient
    public Collection<Lote> getLoteCollection() {
        return loteCollection;
    }

    public void setLoteCollection(Collection<Lote> loteCollection) {
        this.loteCollection = loteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Produto[ idProduto=" + idProduto + " ]";
    }
    
}
