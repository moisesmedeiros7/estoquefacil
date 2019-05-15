/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estoque;
import modelo.Lote;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.Produto;

/**
 *
 * @author moises
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getLoteCollection() == null) {
            produto.setLoteCollection(new ArrayList<Lote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estoque est = produto.getEst();
            if (est != null) {
                est = em.getReference(est.getClass(), est.getIdEstoque());
                produto.setEst(est);
            }
            Collection<Lote> attachedLoteCollection = new ArrayList<Lote>();
            for (Lote loteCollectionLoteToAttach : produto.getLoteCollection()) {
                loteCollectionLoteToAttach = em.getReference(loteCollectionLoteToAttach.getClass(), loteCollectionLoteToAttach.getIdLote());
                attachedLoteCollection.add(loteCollectionLoteToAttach);
            }
            produto.setLoteCollection(attachedLoteCollection);
            em.persist(produto);
            if (est != null) {
                est.getProdutoCollection().add(produto);
                est = em.merge(est);
            }
            for (Lote loteCollectionLote : produto.getLoteCollection()) {
                Produto oldProdOfLoteCollectionLote = loteCollectionLote.getProd();
                loteCollectionLote.setProd(produto);
                loteCollectionLote = em.merge(loteCollectionLote);
                if (oldProdOfLoteCollectionLote != null) {
                    oldProdOfLoteCollectionLote.getLoteCollection().remove(loteCollectionLote);
                    oldProdOfLoteCollectionLote = em.merge(oldProdOfLoteCollectionLote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getIdProduto());
            Estoque estOld = persistentProduto.getEst();
            Estoque estNew = produto.getEst();
            Collection<Lote> loteCollectionOld = persistentProduto.getLoteCollection();
            Collection<Lote> loteCollectionNew = produto.getLoteCollection();
            if (estNew != null) {
                estNew = em.getReference(estNew.getClass(), estNew.getIdEstoque());
                produto.setEst(estNew);
            }
            Collection<Lote> attachedLoteCollectionNew = new ArrayList<Lote>();
            for (Lote loteCollectionNewLoteToAttach : loteCollectionNew) {
                loteCollectionNewLoteToAttach = em.getReference(loteCollectionNewLoteToAttach.getClass(), loteCollectionNewLoteToAttach.getIdLote());
                attachedLoteCollectionNew.add(loteCollectionNewLoteToAttach);
            }
            loteCollectionNew = attachedLoteCollectionNew;
            produto.setLoteCollection(loteCollectionNew);
            produto = em.merge(produto);
            if (estOld != null && !estOld.equals(estNew)) {
                estOld.getProdutoCollection().remove(produto);
                estOld = em.merge(estOld);
            }
            if (estNew != null && !estNew.equals(estOld)) {
                estNew.getProdutoCollection().add(produto);
                estNew = em.merge(estNew);
            }
            for (Lote loteCollectionOldLote : loteCollectionOld) {
                if (!loteCollectionNew.contains(loteCollectionOldLote)) {
                    loteCollectionOldLote.setProd(null);
                    loteCollectionOldLote = em.merge(loteCollectionOldLote);
                }
            }
            for (Lote loteCollectionNewLote : loteCollectionNew) {
                if (!loteCollectionOld.contains(loteCollectionNewLote)) {
                    Produto oldProdOfLoteCollectionNewLote = loteCollectionNewLote.getProd();
                    loteCollectionNewLote.setProd(produto);
                    loteCollectionNewLote = em.merge(loteCollectionNewLote);
                    if (oldProdOfLoteCollectionNewLote != null && !oldProdOfLoteCollectionNewLote.equals(produto)) {
                        oldProdOfLoteCollectionNewLote.getLoteCollection().remove(loteCollectionNewLote);
                        oldProdOfLoteCollectionNewLote = em.merge(oldProdOfLoteCollectionNewLote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getIdProduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getIdProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            Estoque est = produto.getEst();
            if (est != null) {
                est.getProdutoCollection().remove(produto);
                est = em.merge(est);
            }
            Collection<Lote> loteCollection = produto.getLoteCollection();
            for (Lote loteCollectionLote : loteCollection) {
                loteCollectionLote.setProd(null);
                loteCollectionLote = em.merge(loteCollectionLote);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Produto> listarProdPorEstoque(Estoque estoque){
        EntityManager em = getEntityManager();
        Integer est = estoque.getIdEstoque(); //pegando o ID de estoque
        
        TypedQuery<Produto> query;
        query = em.createQuery("select p from Produto p where p.est=:est", Produto.class);
        query.setParameter("est", estoque);
        
        return query.getResultList();
    }
    
     public List<Produto> listarProdComLotePorEstoque(Estoque estoque){
        EntityManager em = getEntityManager();
        Integer est = estoque.getIdEstoque(); //pegando o ID de estoque
        
        TypedQuery<Produto> query;
        query = em.createQuery("select p from Produto p where p.est=:est", Produto.class);
        query.setParameter("est", estoque);
        
        return query.getResultList();
    }
    
     /*
     public List<Produto> listarProdComLotePositivoPorEstoque(Estoque estoque){
        EntityManager em = getEntityManager();
       
        TypedQuery<Produto> query;
       
        query = em.createQuery("select p from Produto p where p.est=:est"+"and Lote l where l.prod=p_idProduto and l.quantidade > 0", Produto.class);
       // "select l from Lote l where l.prod=:prod"+" and l.quantidade > 0", Lote.class);
        query.setParameter("est", estoque);
        
        return query.getResultList();
    }
    */
        
}
    
    

