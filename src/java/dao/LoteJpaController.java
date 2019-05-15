package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estoque;
import modelo.Lote;
import modelo.Produto;

/**
 *
 * @author moises
 */
public class LoteJpaController implements Serializable {

    public LoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Lote lote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto prod = lote.getProd();
            if (prod != null) {
                prod = em.getReference(prod.getClass(), prod.getIdProduto());
                lote.setProd(prod);
            }
            em.persist(lote);
            if (prod != null) {
                prod.getLoteCollection().add(lote);
                prod = em.merge(prod);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
    
    public void edit(Lote lote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lote persistentLote = em.find(Lote.class, lote.getIdLote());
            Produto prodOld = persistentLote.getProd();
            Produto prodNew = lote.getProd();
            if (prodNew != null) {
                prodNew = em.getReference(prodNew.getClass(), prodNew.getIdProduto());
                lote.setProd(prodNew);
            }
            lote = em.merge(lote);
            if (prodOld != null && !prodOld.equals(prodNew)) {
                prodOld.getLoteCollection().remove(lote);
                prodOld = em.merge(prodOld);
            }
            if (prodNew != null && !prodNew.equals(prodOld)) {
                prodNew.getLoteCollection().add(lote);
                prodNew = em.merge(prodNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lote.getIdLote();
                if (findLote(id) == null) {
                    throw new NonexistentEntityException("The lote with id " + id + " no longer exists.");
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
            Lote lote;
            try {
                lote = em.getReference(Lote.class, id);
                lote.getIdLote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lote with id " + id + " no longer exists.", enfe);
            }
            Produto prod = lote.getProd();
            if (prod != null) {
                prod.getLoteCollection().remove(lote);
                prod = em.merge(prod);
            }
            em.remove(lote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lote> findLoteEntities() {
        return findLoteEntities(true, -1, -1);
    }

    public List<Lote> findLoteEntities(int maxResults, int firstResult) {
        return findLoteEntities(false, maxResults, firstResult);
    }

    private List<Lote> findLoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lote.class));
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

    public Lote findLote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lote.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lote> rt = cq.from(Lote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void diminuirQtde(Integer id_l, int qtde) throws Exception{  //UPDATE Lote set Lote.quantidade = Lote.quantidade - qtde WHERE Lote.id_lote=id_l;
       EntityManager em = getEntityManager();
       TypedQuery<Lote> query;
       query = em.createQuery("SELECT l FROM Lote l WHERE l.idLote = :idLote", Lote.class);
       query.setParameter("idLote", id_l);
       
       Lote lote = new Lote();
       lote = query.getSingleResult();
       lote.setQuantidade(lote.getQuantidade()- qtde);
       edit(lote);   
    }
    
    public List<Lote> listarLotePorProduto(Produto produto){
        EntityManager em = getEntityManager();
        Integer prod = produto.getIdProduto(); //pegando o ID de estoque
        
        TypedQuery<Lote> query;
        query = em.createQuery("select l from Lote l where l.prod=:prod", Lote.class);
        query.setParameter("prod", produto);
        
        return query.getResultList();
    }
    
    public List<Lote> pesqLotePorProdComQtde(Produto prod){
        EntityManager em = getEntityManager();
        TypedQuery<Lote> query;
        query = em.createQuery("select l from Lote l where l.prod=:prod"+" and l.quantidade > 0", Lote.class);
        query.setParameter("prod", prod);
        return query.getResultList();
        
    }
    

     
 }
