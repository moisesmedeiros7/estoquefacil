package beans;

import dao.LoteJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Lote;
import modelo.Produto;
import modelo.Usuario;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean (name = "loteBean")
@javax.faces.bean.SessionScoped
public class LoteBean {
    private List<Lote> lotes = new ArrayList<>();
    private List<Lote> lotes2 = new ArrayList<>();
    private Lote lote = new Lote();
    private LoteJpaController dao = new LoteJpaController (EMF.getEntityManagerFactory());
   
    public void LoteBean(){
        
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }
    
    public List<Lote> getLotes() {
        listarLotes();
        return lotes;
    }
    
  
    public void listarLotes(){  //pegando todas Banco de Dados
        lotes = dao.findLoteEntities();
        
    }
    
    public Lote findLote(Integer id){
        return dao.findLote(id);
    }
    
      public void criarLote(Produto produto){
     
       Date data = new Date();
       lote.setDataEntrada(data);
       lote.setProd(produto);
       lote.setEntradaInicial(lote.getQuantidade());
     
       dao.create(lote);
       this.lote = new Lote();
       JSFUtil.adicionarMensagemDeSucesso("Lote adicionado ao estoque com sucesso!");     
    }
    
     public String carregarLote(Lote lot) throws Exception {
      
          this.lote = lot;
          return "editlote.xhtml";  
      
    } 
    
     public void loteNew(){
        this.lote = new Lote();
    }  
  
     public void editarLote() throws Exception {
       Date data = new Date();
       
            if(lote.getIdLote() != null){
                lote.setDataModificacao(data);
                dao.edit(lote);            
               lote = new Lote();
               JSFUtil.adicionarMensagemDeSucesso("Lote editado com sucesso!");
            } 
           
     }
      
   
    public void excluirLote(Lote lot) throws NonexistentEntityException {
            
            if(lot.getIdLote()!= null){
                dao.destroy(lot.getIdLote());
                lote = new Lote();
                JSFUtil.adicionarMensagemDeSucesso("Lote excluido com sucesso!");
            } 
            
    }
    
    public void saidaEstoque (Integer l, int qtde) throws Exception{  //UPDATE Lote set Lote.quantidade = Lote.quantidade - qtde WHERE Lote.id_lote=id_l;
       dao.diminuirQtde(l, qtde);
    }

    public List<Lote> getLotes2() {
        return lotes2;
    }

    public void setLotes2(List<Lote> lotes2) {
        this.lotes2 = lotes2;
    }

    public LoteJpaController getDao() {
        return dao;
    }

    public void setDao(LoteJpaController dao) {
        this.dao = dao;
    }
    
      
    public List<Lote> pesqLotePorProduto(Produto p){
        return dao.listarLotePorProduto(p);
        
    }
    
    public List<Lote> pesqLotePorProdComQtde (Produto p){ // recebe um produto e lista quantos lotes com quantidade
        return dao.pesqLotePorProdComQtde(p);             // maior que zero ele tem.
    }
    
    public void carregarLoteDataTable(Produto p){ //método para carregar em lotes2, usado no datatable
        lotes2 = pesqLotePorProdComQtde(p);
    }
  
    
        
}