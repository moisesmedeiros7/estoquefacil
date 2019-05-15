package beans;

import dao.EstoqueJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Estoque;
import modelo.Produto;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean
@javax.faces.bean.SessionScoped
public class EstoqueBean {
    private List<Estoque> estoques = new ArrayList<>();
    private Estoque estoque = new Estoque();
    private EstoqueJpaController dao = new EstoqueJpaController (EMF.getEntityManagerFactory());
    
   
    public void EstoqueBean(){
   
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    
    public List<Estoque> getEstoques() {
        listarEstoques();
        return estoques;
    }
    
  
    public void listarEstoques(){  //pegando todas as competições no Banco de Dados
        estoques = dao.findEstoqueEntities();
        
    }
    
      public void criarEstoque(){
       
        dao.create(estoque);
        estoque = new Estoque();
        JSFUtil.adicionarMensagemDeSucesso("Estoque cadastrado com sucesso!");
        
    }
      
     public String carregarEstoque(Estoque est) throws Exception {
      
          this.estoque = est;
          return "editestoque.xhtml";  
      
    } 
     
    public String estoqueNew(){
        this.estoque = new Estoque();
        JSFUtil.adicionarMensagemDeErro("Cancelado");
        return "index.xhtml";
    } 
    
  
     public void editarEstoque() throws Exception {
       
            if(estoque.getIdEstoque() != null){
                dao.edit(estoque);            
               estoque = new Estoque();
               JSFUtil.adicionarMensagemDeSucesso("Estoque editado com sucesso!");
            } 
     }
      
   
    public void excluirEstoque(Estoque est) throws NonexistentEntityException {
        
            ProdutoBean produtobean = new ProdutoBean();
            List<Produto> produtos = new ArrayList<>(); 
            produtos = produtobean.pesqProdPorEstoque(est); //guarda em uma lista os produtos que um estoque tem vinculado
            
            if(produtos.size() >= 1){ // se tiver mais de um produto vinculado não deixa excluir
                JSFUtil.adicionarMensagemDeErro("O estoque tem produto(s) cadastrado(s), não pode ser excluído!");
            } else if(est.getIdEstoque()!= null){
                dao.destroy(est.getIdEstoque());
                estoque = new Estoque();
                JSFUtil.adicionarMensagemDeSucesso("Estoque excluído com sucesso!");
            } 
            
    }
   
    public Estoque pesquisarEstoque(Integer id){
        return dao.findEstoque(id);
    }
     
        
}