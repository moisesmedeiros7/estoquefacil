package beans;

import dao.PedidoJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Cliente;
import modelo.Lote;
import modelo.Pedido;
import modelo.Usuario;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean
@javax.faces.bean.SessionScoped
public class PedidoBean {
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Pedido> edidos2 = new ArrayList<>();
    private Pedido pedido = new Pedido();
    private PedidoJpaController dao = new PedidoJpaController (EMF.getEntityManagerFactory());
    private UsuarioJpaController daouser = new UsuarioJpaController (EMF.getEntityManagerFactory()); //temporario
   
            
    
    public void PedidoBean(){
        
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    
    public List<Pedido> getPedidos() {
        listarPedidos();
        return pedidos;
    }
    
  
    public void listarPedidos(){  //pegando todas as competições no Banco de Dados
        pedidos = dao.findPedidoEntities();
        
    }
    
    public void criarPedido(Lote l, Cliente c, LoteBean loteMB) throws Throwable{
     
        
        if(pedido.getQtde() <= l.getQuantidade() && pedido.getQtde() >=1){ 
         try{
         loteMB.saidaEstoque(l.getIdLote(), pedido.getQtde()); //dando baixa no estoque
         Date tempoPedido = new Date(); // setando data do pedido
         pedido.setDataPedido(tempoPedido); //pegando data e setando em pedido
         pedido.setHoraPedido(tempoPedido); //pegando hora e setando em pedido
       
         Usuario usr = daouser.findUsuario(1); //temporario, enquanto nao cria-se Login
         pedido.setLot(l); //adicionando FK de lote
         pedido.setCli(c); //adicionando FK de cliente
         pedido.setUs(usr); //adicionando FK de usuario
      
         dao.create(pedido);
         pedido = new Pedido();
         JSFUtil.adicionarMensagemDeSucesso("Pedido despachado com sucesso!");
       
         }catch(IOException ex){
           pedido = new Pedido();
        }
        }else{
        JSFUtil.adicionarMensagemDeErro("Pedido não realizado! Quantidade incompatível");} 
    }
    
     public String carregarPedido(Pedido ped) throws Exception {
          
          this.pedido = ped;
          return "editpedido.xhtml";  
      
    }
    
     public void pedidoNew(Lote l, Cliente c ){
        l = new Lote();
        c = new Cliente();
        this.pedido = new Pedido();
        
    }  
  
     public void editarPedido() throws Exception {
       
            if(pedido.getNumero()!= null){
                dao.edit(pedido);            
               pedido = new Pedido();
               JSFUtil.adicionarMensagemDeSucesso("Pedido editado com sucesso!");
            } 
     }
      
   
    public void excluirPedido(Pedido ped) throws NonexistentEntityException {
       
            if(ped.getNumero()!= null){
                dao.destroy(ped.getNumero());
                pedido = new Pedido();
                JSFUtil.adicionarMensagemDeSucesso("Pedido excluido com sucesso!");
            } 
            
    }
    
    public List<Pedido> listarPedidosPorCliente (Cliente cliente){ //lista os pedidos enviados para um cliente
        return dao.listarPedidosPorCliente(cliente);
    }
  
}

