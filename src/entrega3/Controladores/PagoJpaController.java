/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Pago;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Tarjetacredito;
import entrega3.Entidades.Tiquete;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PagoJpaController implements Serializable {

    public PagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pago pago) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjetacredito numerotarjeta = pago.getNumerotarjeta();
            if (numerotarjeta != null) {
                numerotarjeta = em.getReference(numerotarjeta.getClass(), numerotarjeta.getNumerotarjeta());
                pago.setNumerotarjeta(numerotarjeta);
            }
            Tiquete idtiquete = pago.getIdtiquete();
            if (idtiquete != null) {
                idtiquete = em.getReference(idtiquete.getClass(), idtiquete.getIdtiquete());
                pago.setIdtiquete(idtiquete);
            }
            em.persist(pago);
            if (numerotarjeta != null) {
                numerotarjeta.getPagoCollection().add(pago);
                numerotarjeta = em.merge(numerotarjeta);
            }
            if (idtiquete != null) {
                idtiquete.getPagoCollection().add(pago);
                idtiquete = em.merge(idtiquete);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPago(pago.getIdpago()) != null) {
                throw new PreexistingEntityException("Pago " + pago + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pago pago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pago persistentPago = em.find(Pago.class, pago.getIdpago());
            Tarjetacredito numerotarjetaOld = persistentPago.getNumerotarjeta();
            Tarjetacredito numerotarjetaNew = pago.getNumerotarjeta();
            Tiquete idtiqueteOld = persistentPago.getIdtiquete();
            Tiquete idtiqueteNew = pago.getIdtiquete();
            if (numerotarjetaNew != null) {
                numerotarjetaNew = em.getReference(numerotarjetaNew.getClass(), numerotarjetaNew.getNumerotarjeta());
                pago.setNumerotarjeta(numerotarjetaNew);
            }
            if (idtiqueteNew != null) {
                idtiqueteNew = em.getReference(idtiqueteNew.getClass(), idtiqueteNew.getIdtiquete());
                pago.setIdtiquete(idtiqueteNew);
            }
            pago = em.merge(pago);
            if (numerotarjetaOld != null && !numerotarjetaOld.equals(numerotarjetaNew)) {
                numerotarjetaOld.getPagoCollection().remove(pago);
                numerotarjetaOld = em.merge(numerotarjetaOld);
            }
            if (numerotarjetaNew != null && !numerotarjetaNew.equals(numerotarjetaOld)) {
                numerotarjetaNew.getPagoCollection().add(pago);
                numerotarjetaNew = em.merge(numerotarjetaNew);
            }
            if (idtiqueteOld != null && !idtiqueteOld.equals(idtiqueteNew)) {
                idtiqueteOld.getPagoCollection().remove(pago);
                idtiqueteOld = em.merge(idtiqueteOld);
            }
            if (idtiqueteNew != null && !idtiqueteNew.equals(idtiqueteOld)) {
                idtiqueteNew.getPagoCollection().add(pago);
                idtiqueteNew = em.merge(idtiqueteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pago.getIdpago();
                if (findPago(id) == null) {
                    throw new NonexistentEntityException("The pago with id " + id + " no longer exists.");
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
            Pago pago;
            try {
                pago = em.getReference(Pago.class, id);
                pago.getIdpago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pago with id " + id + " no longer exists.", enfe);
            }
            Tarjetacredito numerotarjeta = pago.getNumerotarjeta();
            if (numerotarjeta != null) {
                numerotarjeta.getPagoCollection().remove(pago);
                numerotarjeta = em.merge(numerotarjeta);
            }
            Tiquete idtiquete = pago.getIdtiquete();
            if (idtiquete != null) {
                idtiquete.getPagoCollection().remove(pago);
                idtiquete = em.merge(idtiquete);
            }
            em.remove(pago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pago> findPagoEntities() {
        return findPagoEntities(true, -1, -1);
    }

    public List<Pago> findPagoEntities(int maxResults, int firstResult) {
        return findPagoEntities(false, maxResults, firstResult);
    }

    private List<Pago> findPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pago.class));
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

    public Pago findPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pago> rt = cq.from(Pago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
