/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.IllegalOrphanException;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Efectivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class EfectivoJpaController implements Serializable {

    public EfectivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Efectivo efectivo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Efectivo efectivo1OrphanCheck = efectivo.getEfectivo1();
        if (efectivo1OrphanCheck != null) {
            Efectivo oldEfectivoOfEfectivo1 = efectivo1OrphanCheck.getEfectivo();
            if (oldEfectivoOfEfectivo1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Efectivo " + efectivo1OrphanCheck + " already has an item of type Efectivo whose efectivo1 column cannot be null. Please make another selection for the efectivo1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Efectivo efectivoRel = efectivo.getEfectivo();
            if (efectivoRel != null) {
                efectivoRel = em.getReference(efectivoRel.getClass(), efectivoRel.getIdpago());
                efectivo.setEfectivo(efectivoRel);
            }
            Efectivo efectivo1 = efectivo.getEfectivo1();
            if (efectivo1 != null) {
                efectivo1 = em.getReference(efectivo1.getClass(), efectivo1.getIdpago());
                efectivo.setEfectivo1(efectivo1);
            }
            em.persist(efectivo);
            if (efectivoRel != null) {
                Efectivo oldEfectivo1OfEfectivoRel = efectivoRel.getEfectivo1();
                if (oldEfectivo1OfEfectivoRel != null) {
                    oldEfectivo1OfEfectivoRel.setEfectivo(null);
                    oldEfectivo1OfEfectivoRel = em.merge(oldEfectivo1OfEfectivoRel);
                }
                efectivoRel.setEfectivo1(efectivo);
                efectivoRel = em.merge(efectivoRel);
            }
            if (efectivo1 != null) {
                efectivo1.setEfectivo(efectivo);
                efectivo1 = em.merge(efectivo1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEfectivo(efectivo.getIdpago()) != null) {
                throw new PreexistingEntityException("Efectivo " + efectivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Efectivo efectivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Efectivo persistentEfectivo = em.find(Efectivo.class, efectivo.getIdpago());
            Efectivo efectivoRelOld = persistentEfectivo.getEfectivo();
            Efectivo efectivoRelNew = efectivo.getEfectivo();
            Efectivo efectivo1Old = persistentEfectivo.getEfectivo1();
            Efectivo efectivo1New = efectivo.getEfectivo1();
            List<String> illegalOrphanMessages = null;
            if (efectivoRelOld != null && !efectivoRelOld.equals(efectivoRelNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Efectivo " + efectivoRelOld + " since its efectivo1 field is not nullable.");
            }
            if (efectivo1New != null && !efectivo1New.equals(efectivo1Old)) {
                Efectivo oldEfectivoOfEfectivo1 = efectivo1New.getEfectivo();
                if (oldEfectivoOfEfectivo1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Efectivo " + efectivo1New + " already has an item of type Efectivo whose efectivo1 column cannot be null. Please make another selection for the efectivo1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (efectivoRelNew != null) {
                efectivoRelNew = em.getReference(efectivoRelNew.getClass(), efectivoRelNew.getIdpago());
                efectivo.setEfectivo(efectivoRelNew);
            }
            if (efectivo1New != null) {
                efectivo1New = em.getReference(efectivo1New.getClass(), efectivo1New.getIdpago());
                efectivo.setEfectivo1(efectivo1New);
            }
            efectivo = em.merge(efectivo);
            if (efectivoRelNew != null && !efectivoRelNew.equals(efectivoRelOld)) {
                Efectivo oldEfectivo1OfEfectivoRel = efectivoRelNew.getEfectivo1();
                if (oldEfectivo1OfEfectivoRel != null) {
                    oldEfectivo1OfEfectivoRel.setEfectivo(null);
                    oldEfectivo1OfEfectivoRel = em.merge(oldEfectivo1OfEfectivoRel);
                }
                efectivoRelNew.setEfectivo1(efectivo);
                efectivoRelNew = em.merge(efectivoRelNew);
            }
            if (efectivo1Old != null && !efectivo1Old.equals(efectivo1New)) {
                efectivo1Old.setEfectivo(null);
                efectivo1Old = em.merge(efectivo1Old);
            }
            if (efectivo1New != null && !efectivo1New.equals(efectivo1Old)) {
                efectivo1New.setEfectivo(efectivo);
                efectivo1New = em.merge(efectivo1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = efectivo.getIdpago();
                if (findEfectivo(id) == null) {
                    throw new NonexistentEntityException("The efectivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Efectivo efectivo;
            try {
                efectivo = em.getReference(Efectivo.class, id);
                efectivo.getIdpago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The efectivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Efectivo efectivoOrphanCheck = efectivo.getEfectivo();
            if (efectivoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Efectivo (" + efectivo + ") cannot be destroyed since the Efectivo " + efectivoOrphanCheck + " in its efectivo field has a non-nullable efectivo1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Efectivo efectivo1 = efectivo.getEfectivo1();
            if (efectivo1 != null) {
                efectivo1.setEfectivo(null);
                efectivo1 = em.merge(efectivo1);
            }
            em.remove(efectivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Efectivo> findEfectivoEntities() {
        return findEfectivoEntities(true, -1, -1);
    }

    public List<Efectivo> findEfectivoEntities(int maxResults, int firstResult) {
        return findEfectivoEntities(false, maxResults, firstResult);
    }

    private List<Efectivo> findEfectivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Efectivo.class));
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

    public Efectivo findEfectivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Efectivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEfectivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Efectivo> rt = cq.from(Efectivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
