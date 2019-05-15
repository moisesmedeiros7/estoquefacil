/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Usuario;
import modelo.Cliente;
import modelo.Pedido;

/**
 *
 * @author moises
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario us = pedido.getUs();
            if (us != null) {
                us = em.getReference(us.getClass(), us.getIdUsuario());
                pedido.setUs(us);
            }
            Cliente cli = pedido.getCli();
            if (cli != null) {
                cli = em.getReference(cli.getClass(), cli.getIdCliente());
                pedido.setCli(cli);
            }
            em.persist(pedido);
            if (us != null) {
                us.getPedidoCollection().add(pedido);
                us = em.merge(us);
            }
            if (cli != null) {
                cli.getPedidoCollection().add(pedido);
                cli = em.merge(cli);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getNumero());
            Usuario usOld = persistentPedido.getUs();
            Usuario usNew = pedido.getUs();
            Cliente cliOld = persistentPedido.getCli();
            Cliente cliNew = pedido.getCli();
            if (usNew != null) {
                usNew = em.getReference(usNew.getClass(), usNew.getIdUsuario());
                pedido.setUs(usNew);
            }
            if (cliNew != null) {
                cliNew = em.getReference(cliNew.getClass(), cliNew.getIdCliente());
                pedido.setCli(cliNew);
            }
            pedido = em.merge(pedido);
            if (usOld != null && !usOld.equals(usNew)) {
                usOld.getPedidoCollection().remove(pedido);
                usOld = em.merge(usOld);
            }
            if (usNew != null && !usNew.equals(usOld)) {
                usNew.getPedidoCollection().add(pedido);
                usNew = em.merge(usNew);
            }
            if (cliOld != null && !cliOld.equals(cliNew)) {
                cliOld.getPedidoCollection().remove(pedido);
                cliOld = em.merge(cliOld);
            }
            if (cliNew != null && !cliNew.equals(cliOld)) {
                cliNew.getPedidoCollection().add(pedido);
                cliNew = em.merge(cliNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getNumero();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getNumero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Usuario us = pedido.getUs();
            if (us != null) {
                us.getPedidoCollection().remove(pedido);
                us = em.merge(us);
            }
            Cliente cli = pedido.getCli();
            if (cli != null) {
                cli.getPedidoCollection().remove(pedido);
                cli = em.merge(cli);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Pedido> listarPedidosPorCliente(Cliente cliente){
        EntityManager em = getEntityManager();
       
        TypedQuery<Pedido> query;
        query = em.createQuery("select p from Pedido p where p.cli=:cli", Pedido.class);
        query.setParameter("cli", cliente);
        
        return query.getResultList();
    }
    
}
