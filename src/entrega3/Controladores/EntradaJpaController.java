/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Entrada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Fase;
import entrega3.Entidades.Silla;
import entrega3.Entidades.Tiquete;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class EntradaJpaController implements Serializable {

    public EntradaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrada entrada) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fase idfase = entrada.getIdfase();
            if (idfase != null) {
                idfase = em.getReference(idfase.getClass(), idfase.getIdfase());
                entrada.setIdfase(idfase);
            }
            Silla silla = entrada.getSilla();
            if (silla != null) {
                silla = em.getReference(silla.getClass(), silla.getSillaPK());
                entrada.setSilla(silla);
            }
            Tiquete idtiquete = entrada.getIdtiquete();
            if (idtiquete != null) {
                idtiquete = em.getReference(idtiquete.getClass(), idtiquete.getIdtiquete());
                entrada.setIdtiquete(idtiquete);
            }
            em.persist(entrada);
            if (idfase != null) {
                idfase.getEntradaCollection().add(entrada);
                idfase = em.merge(idfase);
            }
            if (silla != null) {
                silla.getEntradaCollection().add(entrada);
                silla = em.merge(silla);
            }
            if (idtiquete != null) {
                idtiquete.getEntradaCollection().add(entrada);
                idtiquete = em.merge(idtiquete);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntrada(entrada.getIdentrada()) != null) {
                throw new PreexistingEntityException("Entrada " + entrada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entrada entrada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrada persistentEntrada = em.find(Entrada.class, entrada.getIdentrada());
            Fase idfaseOld = persistentEntrada.getIdfase();
            Fase idfaseNew = entrada.getIdfase();
            Silla sillaOld = persistentEntrada.getSilla();
            Silla sillaNew = entrada.getSilla();
            Tiquete idtiqueteOld = persistentEntrada.getIdtiquete();
            Tiquete idtiqueteNew = entrada.getIdtiquete();
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getIdfase());
                entrada.setIdfase(idfaseNew);
            }
            if (sillaNew != null) {
                sillaNew = em.getReference(sillaNew.getClass(), sillaNew.getSillaPK());
                entrada.setSilla(sillaNew);
            }
            if (idtiqueteNew != null) {
                idtiqueteNew = em.getReference(idtiqueteNew.getClass(), idtiqueteNew.getIdtiquete());
                entrada.setIdtiquete(idtiqueteNew);
            }
            entrada = em.merge(entrada);
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getEntradaCollection().remove(entrada);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getEntradaCollection().add(entrada);
                idfaseNew = em.merge(idfaseNew);
            }
            if (sillaOld != null && !sillaOld.equals(sillaNew)) {
                sillaOld.getEntradaCollection().remove(entrada);
                sillaOld = em.merge(sillaOld);
            }
            if (sillaNew != null && !sillaNew.equals(sillaOld)) {
                sillaNew.getEntradaCollection().add(entrada);
                sillaNew = em.merge(sillaNew);
            }
            if (idtiqueteOld != null && !idtiqueteOld.equals(idtiqueteNew)) {
                idtiqueteOld.getEntradaCollection().remove(entrada);
                idtiqueteOld = em.merge(idtiqueteOld);
            }
            if (idtiqueteNew != null && !idtiqueteNew.equals(idtiqueteOld)) {
                idtiqueteNew.getEntradaCollection().add(entrada);
                idtiqueteNew = em.merge(idtiqueteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entrada.getIdentrada();
                if (findEntrada(id) == null) {
                    throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.");
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
            Entrada entrada;
            try {
                entrada = em.getReference(Entrada.class, id);
                entrada.getIdentrada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.", enfe);
            }
            Fase idfase = entrada.getIdfase();
            if (idfase != null) {
                idfase.getEntradaCollection().remove(entrada);
                idfase = em.merge(idfase);
            }
            Silla silla = entrada.getSilla();
            if (silla != null) {
                silla.getEntradaCollection().remove(entrada);
                silla = em.merge(silla);
            }
            Tiquete idtiquete = entrada.getIdtiquete();
            if (idtiquete != null) {
                idtiquete.getEntradaCollection().remove(entrada);
                idtiquete = em.merge(idtiquete);
            }
            em.remove(entrada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entrada> findEntradaEntities() {
        return findEntradaEntities(true, -1, -1);
    }

    public List<Entrada> findEntradaEntities(int maxResults, int firstResult) {
        return findEntradaEntities(false, maxResults, firstResult);
    }

    private List<Entrada> findEntradaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrada.class));
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

    public Entrada findEntrada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entrada.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntradaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrada> rt = cq.from(Entrada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
