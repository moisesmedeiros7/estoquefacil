package beans;

import dao.ClienteJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Cliente;
import modelo.Pedido;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean
@javax.faces.bean.SessionScoped
public class ClienteBean {
    private List<Cliente> clientes = new ArrayList<>();
    private Cliente cliente = new Cliente();
    private ClienteJpaController dao = new ClienteJpaController (EMF.getEntityManagerFactory());
    
   
    public void ClienteBean(){
   
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    
    public List<Cliente> getClientes() {
        listarClientes();
        return clientes;
    }
    
  
    public void listarClientes(){  //pegando todos os clientes no Banco de Dados
        clientes = dao.findClienteEntities();
        
    }
    
      public void criarCliente(){
       
        dao.create(cliente);
        cliente = new Cliente();
        JSFUtil.adicionarMensagemDeSucesso("Cliente cadastrado com sucesso!");
    }
      
     public String carregarCliente(Cliente cli) throws Exception {
      
          this.cliente = cli;
          return "editcliente.xhtml";  
      
    } 
     
    public void clienteNew(){
        this.cliente = new Cliente();
    } 
    
  
     public void editarCliente() throws Exception {
       
            if(cliente.getIdCliente() != null){
                dao.edit(cliente);            
               cliente = new Cliente();
               JSFUtil.adicionarMensagemDeSucesso("Cliente editado com sucesso!");
            } 
     }
      
   
    public void excluirCliente(Cliente cli) throws NonexistentEntityException {
       
           PedidoBean pedidobean = new PedidoBean();
           List<Pedido> pedidos = new ArrayList<>(); 
           pedidos = pedidobean.listarPedidosPorCliente(cli);
           
           if(pedidos.size() >= 1){
               JSFUtil.adicionarMensagemDeErro("O Cliente tem pedido(s), não pode ser excluído!");
           } else if(cli.getIdCliente()!= null){
                dao.destroy(cli.getIdCliente());
                cliente = new Cliente();
                JSFUtil.adicionarMensagemDeSucesso("Cliente excluido com sucesso!");
            } 
            
    }
   
    
     
    
}