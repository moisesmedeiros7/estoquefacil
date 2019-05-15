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
import modelo.Produto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Estoque;

/**
 *
 * @author moises
 */
public class EstoqueJpaController implements Serializable {

    public EstoqueJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estoque estoque) {
        if (estoque.getProdutoCollection() == null) {
            estoque.setProdutoCollection(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Produto> attachedProdutoCollection = new ArrayList<Produto>();
            for (Produto produtoCollectionProdutoToAttach : estoque.getProdutoCollection()) {
                produtoCollectionProdutoToAttach = em.getReference(produtoCollectionProdutoToAttach.getClass(), produtoCollectionProdutoToAttach.getIdProduto());
                attachedProdutoCollection.add(produtoCollectionProdutoToAttach);
            }
            estoque.setProdutoCollection(attachedProdutoCollection);
            em.persist(estoque);
            for (Produto produtoCollectionProduto : estoque.getProdutoCollection()) {
                Estoque oldEstOfProdutoCollectionProduto = produtoCollectionProduto.getEst();
                produtoCollectionProduto.setEst(estoque);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
                if (oldEstOfProdutoCollectionProduto != null) {
                    oldEstOfProdutoCollectionProduto.getProdutoCollection().remove(produtoCollectionProduto);
                    oldEstOfProdutoCollectionProduto = em.merge(oldEstOfProdutoCollectionProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estoque estoque) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estoque persistentEstoque = em.find(Estoque.class, estoque.getIdEstoque());
            Collection<Produto> produtoCollectionOld = persistentEstoque.getProdutoCollection();
            Collection<Produto> produtoCollectionNew = estoque.getProdutoCollection();
            Collection<Produto> attachedProdutoCollectionNew = new ArrayList<Produto>();
            for (Produto produtoCollectionNewProdutoToAttach : produtoCollectionNew) {
                produtoCollectionNewProdutoToAttach = em.getReference(produtoCollectionNewProdutoToAttach.getClass(), produtoCollectionNewProdutoToAttach.getIdProduto());
                attachedProdutoCollectionNew.add(produtoCollectionNewProdutoToAttach);
            }
            produtoCollectionNew = attachedProdutoCollectionNew;
            estoque.setProdutoCollection(produtoCollectionNew);
            estoque = em.merge(estoque);
            for (Produto produtoCollectionOldProduto : produtoCollectionOld) {
                if (!produtoCollectionNew.contains(produtoCollectionOldProduto)) {
                    produtoCollectionOldProduto.setEst(null);
                    produtoCollectionOldProduto = em.merge(produtoCollectionOldProduto);
                }
            }
            for (Produto produtoCollectionNewProduto : produtoCollectionNew) {
                if (!produtoCollectionOld.contains(produtoCollectionNewProduto)) {
                    Estoque oldEstOfProdutoCollectionNewProduto = produtoCollectionNewProduto.getEst();
                    produtoCollectionNewProduto.setEst(estoque);
                    produtoCollectionNewProduto = em.merge(produtoCollectionNewProduto);
                    if (oldEstOfProdutoCollectionNewProduto != null && !oldEstOfProdutoCollectionNewProduto.equals(estoque)) {
                        oldEstOfProdutoCollectionNewProduto.getProdutoCollection().remove(produtoCollectionNewProduto);
                        oldEstOfProdutoCollectionNewProduto = em.merge(oldEstOfProdutoCollectionNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estoque.getIdEstoque();
                if (findEstoque(id) == null) {
                    throw new NonexistentEntityException("The estoque with id " + id + " no longer exists.");
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
            Estoque estoque;
            try {
                estoque = em.getReference(Estoque.class, id);
                estoque.getIdEstoque();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estoque with id " + id + " no longer exists.", enfe);
            }
            Collection<Produto> produtoCollection = estoque.getProdutoCollection();
            for (Produto produtoCollectionProduto : produtoCollection) {
                produtoCollectionProduto.setEst(null);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
            }
            em.remove(estoque);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estoque> findEstoqueEntities() {
        return findEstoqueEntities(true, -1, -1);
    }

    public List<Estoque> findEstoqueEntities(int maxResults, int firstResult) {
        return findEstoqueEntities(false, maxResults, firstResult);
    }

    private List<Estoque> findEstoqueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estoque.class));
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

    public Estoque findEstoque(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estoque.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstoqueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estoque> rt = cq.from(Estoque.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
