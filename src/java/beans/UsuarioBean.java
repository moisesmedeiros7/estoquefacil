package beans;

import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import modelo.Usuario;
import util.EMF;
import util.JSFUtil;

/**
 *
 * @author moises
 */
@ManagedBean
@javax.faces.bean.SessionScoped
public class UsuarioBean {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Usuario> usuarios2 = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private UsuarioJpaController dao = new UsuarioJpaController (EMF.getEntityManagerFactory());
   
    public void UsuarioBean(){
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    
    public List<Usuario> getUsuarios() {
        listarUsuarios();
        return usuarios;
    }
    
  
    public void listarUsuarios(){  //pegando todas as competições no Banco de Dados
        usuarios = dao.findUsuarioEntities();
        
    }
    
      public void criarUsuario(Usuario usr){
       
       dao.create(usr);
       usr = new Usuario();
       JSFUtil.adicionarMensagemDeSucesso("Estoque adicionado com sucesso!");
    }
    
     public String carregarUsuario(Usuario usr) throws Exception {
      
          this.usuario = usr;
          return "editusuario.xhtml";  
      
    }
    
     public void usuarioNew(){
        this.usuario = new Usuario();
    }  
  
     public void editarUsuario() throws Exception {
       
            if(usuario.getIdUsuario() != null){
                dao.edit(usuario);            
               usuario = new Usuario();
            } 
     }
      
   
    public void excluirUsuario(Usuario usr) throws NonexistentEntityException {
       
            if(usr.getIdUsuario()!= null){
                dao.destroy(usr.getIdUsuario());
                usuario = new Usuario();
            } 
            
    }
    
    public Usuario pesquisarUsuarioPorID(Integer id){
        usuario =  dao.findUsuario(id);
        return usuario;
    }
    
    public String pesquisarUsuarioPorId(Integer id){
        usuario = dao.findUsuario(id);
        return usuario.getNome();
    }
    
    
}

