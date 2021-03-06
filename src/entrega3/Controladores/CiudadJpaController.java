/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Estadio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws PreexistingEntityException, Exception {
        if (ciudad.getEstadioCollection() == null) {
            ciudad.setEstadioCollection(new ArrayList<Estadio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Estadio> attachedEstadioCollection = new ArrayList<Estadio>();
            for (Estadio estadioCollectionEstadioToAttach : ciudad.getEstadioCollection()) {
                estadioCollectionEstadioToAttach = em.getReference(estadioCollectionEstadioToAttach.getClass(), estadioCollectionEstadioToAttach.getIdestadio());
                attachedEstadioCollection.add(estadioCollectionEstadioToAttach);
            }
            ciudad.setEstadioCollection(attachedEstadioCollection);
            em.persist(ciudad);
            for (Estadio estadioCollectionEstadio : ciudad.getEstadioCollection()) {
                Ciudad oldIdciudadOfEstadioCollectionEstadio = estadioCollectionEstadio.getIdciudad();
                estadioCollectionEstadio.setIdciudad(ciudad);
                estadioCollectionEstadio = em.merge(estadioCollectionEstadio);
                if (oldIdciudadOfEstadioCollectionEstadio != null) {
                    oldIdciudadOfEstadioCollectionEstadio.getEstadioCollection().remove(estadioCollectionEstadio);
                    oldIdciudadOfEstadioCollectionEstadio = em.merge(oldIdciudadOfEstadioCollectionEstadio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCiudad(ciudad.getIdciudad()) != null) {
                throw new PreexistingEntityException("Ciudad " + ciudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getIdciudad());
            Collection<Estadio> estadioCollectionOld = persistentCiudad.getEstadioCollection();
            Collection<Estadio> estadioCollectionNew = ciudad.getEstadioCollection();
            Collection<Estadio> attachedEstadioCollectionNew = new ArrayList<Estadio>();
            for (Estadio estadioCollectionNewEstadioToAttach : estadioCollectionNew) {
                estadioCollectionNewEstadioToAttach = em.getReference(estadioCollectionNewEstadioToAttach.getClass(), estadioCollectionNewEstadioToAttach.getIdestadio());
                attachedEstadioCollectionNew.add(estadioCollectionNewEstadioToAttach);
            }
            estadioCollectionNew = attachedEstadioCollectionNew;
            ciudad.setEstadioCollection(estadioCollectionNew);
            ciudad = em.merge(ciudad);
            for (Estadio estadioCollectionOldEstadio : estadioCollectionOld) {
                if (!estadioCollectionNew.contains(estadioCollectionOldEstadio)) {
                    estadioCollectionOldEstadio.setIdciudad(null);
                    estadioCollectionOldEstadio = em.merge(estadioCollectionOldEstadio);
                }
            }
            for (Estadio estadioCollectionNewEstadio : estadioCollectionNew) {
                if (!estadioCollectionOld.contains(estadioCollectionNewEstadio)) {
                    Ciudad oldIdciudadOfEstadioCollectionNewEstadio = estadioCollectionNewEstadio.getIdciudad();
                    estadioCollectionNewEstadio.setIdciudad(ciudad);
                    estadioCollectionNewEstadio = em.merge(estadioCollectionNewEstadio);
                    if (oldIdciudadOfEstadioCollectionNewEstadio != null && !oldIdciudadOfEstadioCollectionNewEstadio.equals(ciudad)) {
                        oldIdciudadOfEstadioCollectionNewEstadio.getEstadioCollection().remove(estadioCollectionNewEstadio);
                        oldIdciudadOfEstadioCollectionNewEstadio = em.merge(oldIdciudadOfEstadioCollectionNewEstadio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getIdciudad();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getIdciudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            Collection<Estadio> estadioCollection = ciudad.getEstadioCollection();
            for (Estadio estadioCollectionEstadio : estadioCollection) {
                estadioCollectionEstadio.setIdciudad(null);
                estadioCollectionEstadio = em.merge(estadioCollectionEstadio);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
