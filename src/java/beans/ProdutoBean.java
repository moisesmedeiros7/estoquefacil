package beans;

import dao.ProdutoJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Estoque;
import modelo.Lote;
import modelo.Produto;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean
@javax.faces.bean.SessionScoped
public class ProdutoBean {
    private List<Produto> produtos = new ArrayList<>();
    private List<Produto> produtos2 = new ArrayList<>();
   
    private Produto produto = new Produto();
    private Estoque estoque = new Estoque();
    private ProdutoJpaController dao = new ProdutoJpaController (EMF.getEntityManagerFactory());
   
    public void ProdutoBean(){
      
    }

    public Produto getProduto() {
        return produto;
    }

    public List<Produto> getProdutos2() {
        return produtos2;
    }

    public void setProdutos2(List<Produto> produtos2) {
        this.produtos2 = produtos2;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    
    public List<Produto> getProdutos() {
        listarProdutos();
        return produtos;
    }

    public void listarProdutos(){  //pegando todas os produtos no Banco de Dados
        produtos = dao.findProdutoEntities();
        
    }
    
      public void criarProduto(Estoque estoque){
       produto.setStatus("ativo");
       produto.setEst(estoque);
       dao.create(produto);
       produto = new Produto();
       JSFUtil.adicionarMensagemDeSucesso("Produto cadastrado com sucesso!");
    
    }
    
     public String carregarProduto(Produto prod) throws Exception {
      
          this.produto = prod;
          return "editproduto.xhtml";  
      
    }
    
     public void produtoNew(){
        this.produto = new Produto();
    }  
  
     public void editarProduto() throws Exception {
       
            if(produto.getIdProduto() != null){
                dao.edit(produto);            
               produto = new Produto();
               JSFUtil.adicionarMensagemDeSucesso("Produto editado com sucesso!");
            } 
     }
      
    public Produto pesquisarProduto(Integer id){
           return dao.findProduto(id);
    } 
        
   
    public void excluirProduto(Produto prod) throws NonexistentEntityException {
           LoteBean lotebean = new LoteBean();
           List<Lote> lotes = new ArrayList<>(); 
           lotes = lotebean.pesqLotePorProduto(prod); //pegando uma lista dos lotes que o produto tem vinculado
           
           if(lotes.size() >= 1){  // se a lista tiver ao menos um lote associado não deixa excluir
               JSFUtil.adicionarMensagemDeErro("O produto tem lote(s) cadastrado(s), não pode ser excluído!");
           } else if(prod.getIdProduto()!= null){
                dao.destroy(prod.getIdProduto());
                produto = new Produto();
                JSFUtil.adicionarMensagemDeSucesso("Produto excluido com sucesso!");
            } 
            
    }
    
    public List<Produto> pesqProdPorEstoque(Estoque e){ //PESQUISA PRODUTO PELO ESTOQUE E RETORNA A LISTA
        return dao.listarProdPorEstoque(e); 
        
       // return "addlote.xhtml";
    }
   
    public void carregarProdutosPeloEstoque(Estoque e){ //MÉTODO QUE PESQUISA PRODUTO PELO ESTOQUE E SETA EM PRODUTOS2
        produtos2 = pesqProdPorEstoque(e);              
 
    }
  

}

