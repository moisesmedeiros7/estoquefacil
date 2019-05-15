
import dao.ClienteJpaController;
import dao.EstoqueJpaController;
import dao.LoteJpaController;
import dao.PedidoJpaController;
import dao.ProdutoJpaController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.Cliente;
import modelo.Estoque;
import modelo.Lote;
import modelo.Pedido;
import modelo.Produto;
import util.EMF;

/**
 *
 * @author Medeiros
 */
public class testeBean {
    
    Estoque estoque = new Estoque();
    Produto produto = new Produto();
    Pedido pedido = new Pedido();
    Cliente cliente = new Cliente();
    private List<Produto> produtos = new ArrayList<>();
    private List<Lote> lotes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    
    private EstoqueJpaController estoque_dao = new EstoqueJpaController(EMF.getEntityManagerFactory());
    private ProdutoJpaController produto_dao = new ProdutoJpaController(EMF.getEntityManagerFactory());
    private LoteJpaController lote_dao = new LoteJpaController(EMF.getEntityManagerFactory());
    private PedidoJpaController pedido_dao = new PedidoJpaController(EMF.getEntityManagerFactory());
    private ClienteJpaController cliente_dao = new ClienteJpaController(EMF.getEntityManagerFactory());
    
    public static void main(String[] args) {
        testeBean teste = new testeBean();
	System.out.println("Main - programa principal.");
       
       
     }
    
     public void printarLista(List lista){
        Iterator it = lista.iterator();
         while (it.hasNext()) { 
            System.out.println(it.next()); 
       }
          System.out.println(lista.size());
    }
    
    public void pesquisarProdutoPorEstoque(){
        estoque = estoque_dao.findEstoque(1); //setando o ID
        produtos = produto_dao.listarProdPorEstoque(estoque);
        
        printarLista(produtos);
    }
    
    public void pesquisarLotePorProduto(){
        produto = produto_dao.findProduto(10); //setando o ID
        System.out.print("Produto: "+produto.getNome());
        lotes = lote_dao.listarLotePorProduto(produto);
        
        printarLista(lotes);
        
    }
    
    public void pesquisarPedidosPorCliente(){
        cliente = cliente_dao.findCliente(3);
        System.out.print("Cliente: "+cliente.getNome());
        pedidos = pedido_dao.listarPedidosPorCliente(cliente);
        
        printarLista(pedidos);
        
    }
    
    public void pesquisarLotesComQtde(){
        produto = produto_dao.findProduto(8);
        System.out.println("produto: "+produto.getNome());
        lotes = lote_dao.pesqLotePorProdComQtde(produto);
        
        printarLista(lotes);
    }
   
    
}
    

