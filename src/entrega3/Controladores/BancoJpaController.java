/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Banco;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Tarjetacredito;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class BancoJpaController implements Serializable {

    public BancoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Banco banco) throws PreexistingEntityException, Exception {
        if (banco.getTarjetacreditoCollection() == null) {
            banco.setTarjetacreditoCollection(new ArrayList<Tarjetacredito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Tarjetacredito> attachedTarjetacreditoCollection = new ArrayList<Tarjetacredito>();
            for (Tarjetacredito tarjetacreditoCollectionTarjetacreditoToAttach : banco.getTarjetacreditoCollection()) {
                tarjetacreditoCollectionTarjetacreditoToAttach = em.getReference(tarjetacreditoCollectionTarjetacreditoToAttach.getClass(), tarjetacreditoCollectionTarjetacreditoToAttach.getNumerotarjeta());
                attachedTarjetacreditoCollection.add(tarjetacreditoCollectionTarjetacreditoToAttach);
            }
            banco.setTarjetacreditoCollection(attachedTarjetacreditoCollection);
            em.persist(banco);
            for (Tarjetacredito tarjetacreditoCollectionTarjetacredito : banco.getTarjetacreditoCollection()) {
                Banco oldIdbancoOfTarjetacreditoCollectionTarjetacredito = tarjetacreditoCollectionTarjetacredito.getIdbanco();
                tarjetacreditoCollectionTarjetacredito.setIdbanco(banco);
                tarjetacreditoCollectionTarjetacredito = em.merge(tarjetacreditoCollectionTarjetacredito);
                if (oldIdbancoOfTarjetacreditoCollectionTarjetacredito != null) {
                    oldIdbancoOfTarjetacreditoCollectionTarjetacredito.getTarjetacreditoCollection().remove(tarjetacreditoCollectionTarjetacredito);
                    oldIdbancoOfTarjetacreditoCollectionTarjetacredito = em.merge(oldIdbancoOfTarjetacreditoCollectionTarjetacredito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBanco(banco.getIdbanco()) != null) {
                throw new PreexistingEntityException("Banco " + banco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Banco banco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco persistentBanco = em.find(Banco.class, banco.getIdbanco());
            Collection<Tarjetacredito> tarjetacreditoCollectionOld = persistentBanco.getTarjetacreditoCollection();
            Collection<Tarjetacredito> tarjetacreditoCollectionNew = banco.getTarjetacreditoCollection();
            Collection<Tarjetacredito> attachedTarjetacreditoCollectionNew = new ArrayList<Tarjetacredito>();
            for (Tarjetacredito tarjetacreditoCollectionNewTarjetacreditoToAttach : tarjetacreditoCollectionNew) {
                tarjetacreditoCollectionNewTarjetacreditoToAttach = em.getReference(tarjetacreditoCollectionNewTarjetacreditoToAttach.getClass(), tarjetacreditoCollectionNewTarjetacreditoToAttach.getNumerotarjeta());
                attachedTarjetacreditoCollectionNew.add(tarjetacreditoCollectionNewTarjetacreditoToAttach);
            }
            tarjetacreditoCollectionNew = attachedTarjetacreditoCollectionNew;
            banco.setTarjetacreditoCollection(tarjetacreditoCollectionNew);
            banco = em.merge(banco);
            for (Tarjetacredito tarjetacreditoCollectionOldTarjetacredito : tarjetacreditoCollectionOld) {
                if (!tarjetacreditoCollectionNew.contains(tarjetacreditoCollectionOldTarjetacredito)) {
                    tarjetacreditoCollectionOldTarjetacredito.setIdbanco(null);
                    tarjetacreditoCollectionOldTarjetacredito = em.merge(tarjetacreditoCollectionOldTarjetacredito);
                }
            }
            for (Tarjetacredito tarjetacreditoCollectionNewTarjetacredito : tarjetacreditoCollectionNew) {
                if (!tarjetacreditoCollectionOld.contains(tarjetacreditoCollectionNewTarjetacredito)) {
                    Banco oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito = tarjetacreditoCollectionNewTarjetacredito.getIdbanco();
                    tarjetacreditoCollectionNewTarjetacredito.setIdbanco(banco);
                    tarjetacreditoCollectionNewTarjetacredito = em.merge(tarjetacreditoCollectionNewTarjetacredito);
                    if (oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito != null && !oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito.equals(banco)) {
                        oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito.getTarjetacreditoCollection().remove(tarjetacreditoCollectionNewTarjetacredito);
                        oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito = em.merge(oldIdbancoOfTarjetacreditoCollectionNewTarjetacredito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = banco.getIdbanco();
                if (findBanco(id) == null) {
                    throw new NonexistentEntityException("The banco with id " + id + " no longer exists.");
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
            Banco banco;
            try {
                banco = em.getReference(Banco.class, id);
                banco.getIdbanco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The banco with id " + id + " no longer exists.", enfe);
            }
            Collection<Tarjetacredito> tarjetacreditoCollection = banco.getTarjetacreditoCollection();
            for (Tarjetacredito tarjetacreditoCollectionTarjetacredito : tarjetacreditoCollection) {
                tarjetacreditoCollectionTarjetacredito.setIdbanco(null);
                tarjetacreditoCollectionTarjetacredito = em.merge(tarjetacreditoCollectionTarjetacredito);
            }
            em.remove(banco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Banco> findBancoEntities() {
        return findBancoEntities(true, -1, -1);
    }

    public List<Banco> findBancoEntities(int maxResults, int firstResult) {
        return findBancoEntities(false, maxResults, firstResult);
    }

    private List<Banco> findBancoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Banco.class));
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

    public Banco findBanco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Banco.class, id);
        } finally {
            em.close();
        }
    }

    public int getBancoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Banco> rt = cq.from(Banco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
