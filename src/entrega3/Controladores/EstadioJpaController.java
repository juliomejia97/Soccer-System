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
import entrega3.Entidades.Ciudad;
import entrega3.Entidades.Estadio;
import entrega3.Entidades.Partido;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Silla;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class EstadioJpaController implements Serializable {

    public EstadioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estadio estadio) throws PreexistingEntityException, Exception {
        if (estadio.getPartidoCollection() == null) {
            estadio.setPartidoCollection(new ArrayList<Partido>());
        }
        if (estadio.getSillaCollection() == null) {
            estadio.setSillaCollection(new ArrayList<Silla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad idciudad = estadio.getIdciudad();
            if (idciudad != null) {
                idciudad = em.getReference(idciudad.getClass(), idciudad.getIdciudad());
                estadio.setIdciudad(idciudad);
            }
            Collection<Partido> attachedPartidoCollection = new ArrayList<Partido>();
            for (Partido partidoCollectionPartidoToAttach : estadio.getPartidoCollection()) {
                partidoCollectionPartidoToAttach = em.getReference(partidoCollectionPartidoToAttach.getClass(), partidoCollectionPartidoToAttach.getIdpartido());
                attachedPartidoCollection.add(partidoCollectionPartidoToAttach);
            }
            estadio.setPartidoCollection(attachedPartidoCollection);
            Collection<Silla> attachedSillaCollection = new ArrayList<Silla>();
            for (Silla sillaCollectionSillaToAttach : estadio.getSillaCollection()) {
                sillaCollectionSillaToAttach = em.getReference(sillaCollectionSillaToAttach.getClass(), sillaCollectionSillaToAttach.getSillaPK());
                attachedSillaCollection.add(sillaCollectionSillaToAttach);
            }
            estadio.setSillaCollection(attachedSillaCollection);
            em.persist(estadio);
            if (idciudad != null) {
                idciudad.getEstadioCollection().add(estadio);
                idciudad = em.merge(idciudad);
            }
            for (Partido partidoCollectionPartido : estadio.getPartidoCollection()) {
                Estadio oldIdestadioOfPartidoCollectionPartido = partidoCollectionPartido.getIdestadio();
                partidoCollectionPartido.setIdestadio(estadio);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
                if (oldIdestadioOfPartidoCollectionPartido != null) {
                    oldIdestadioOfPartidoCollectionPartido.getPartidoCollection().remove(partidoCollectionPartido);
                    oldIdestadioOfPartidoCollectionPartido = em.merge(oldIdestadioOfPartidoCollectionPartido);
                }
            }
            for (Silla sillaCollectionSilla : estadio.getSillaCollection()) {
                Estadio oldEstadioOfSillaCollectionSilla = sillaCollectionSilla.getEstadio();
                sillaCollectionSilla.setEstadio(estadio);
                sillaCollectionSilla = em.merge(sillaCollectionSilla);
                if (oldEstadioOfSillaCollectionSilla != null) {
                    oldEstadioOfSillaCollectionSilla.getSillaCollection().remove(sillaCollectionSilla);
                    oldEstadioOfSillaCollectionSilla = em.merge(oldEstadioOfSillaCollectionSilla);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadio(estadio.getIdestadio()) != null) {
                throw new PreexistingEntityException("Estadio " + estadio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estadio estadio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadio persistentEstadio = em.find(Estadio.class, estadio.getIdestadio());
            Ciudad idciudadOld = persistentEstadio.getIdciudad();
            Ciudad idciudadNew = estadio.getIdciudad();
            Collection<Partido> partidoCollectionOld = persistentEstadio.getPartidoCollection();
            Collection<Partido> partidoCollectionNew = estadio.getPartidoCollection();
            Collection<Silla> sillaCollectionOld = persistentEstadio.getSillaCollection();
            Collection<Silla> sillaCollectionNew = estadio.getSillaCollection();
            List<String> illegalOrphanMessages = null;
            for (Silla sillaCollectionOldSilla : sillaCollectionOld) {
                if (!sillaCollectionNew.contains(sillaCollectionOldSilla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Silla " + sillaCollectionOldSilla + " since its estadio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idciudadNew != null) {
                idciudadNew = em.getReference(idciudadNew.getClass(), idciudadNew.getIdciudad());
                estadio.setIdciudad(idciudadNew);
            }
            Collection<Partido> attachedPartidoCollectionNew = new ArrayList<Partido>();
            for (Partido partidoCollectionNewPartidoToAttach : partidoCollectionNew) {
                partidoCollectionNewPartidoToAttach = em.getReference(partidoCollectionNewPartidoToAttach.getClass(), partidoCollectionNewPartidoToAttach.getIdpartido());
                attachedPartidoCollectionNew.add(partidoCollectionNewPartidoToAttach);
            }
            partidoCollectionNew = attachedPartidoCollectionNew;
            estadio.setPartidoCollection(partidoCollectionNew);
            Collection<Silla> attachedSillaCollectionNew = new ArrayList<Silla>();
            for (Silla sillaCollectionNewSillaToAttach : sillaCollectionNew) {
                sillaCollectionNewSillaToAttach = em.getReference(sillaCollectionNewSillaToAttach.getClass(), sillaCollectionNewSillaToAttach.getSillaPK());
                attachedSillaCollectionNew.add(sillaCollectionNewSillaToAttach);
            }
            sillaCollectionNew = attachedSillaCollectionNew;
            estadio.setSillaCollection(sillaCollectionNew);
            estadio = em.merge(estadio);
            if (idciudadOld != null && !idciudadOld.equals(idciudadNew)) {
                idciudadOld.getEstadioCollection().remove(estadio);
                idciudadOld = em.merge(idciudadOld);
            }
            if (idciudadNew != null && !idciudadNew.equals(idciudadOld)) {
                idciudadNew.getEstadioCollection().add(estadio);
                idciudadNew = em.merge(idciudadNew);
            }
            for (Partido partidoCollectionOldPartido : partidoCollectionOld) {
                if (!partidoCollectionNew.contains(partidoCollectionOldPartido)) {
                    partidoCollectionOldPartido.setIdestadio(null);
                    partidoCollectionOldPartido = em.merge(partidoCollectionOldPartido);
                }
            }
            for (Partido partidoCollectionNewPartido : partidoCollectionNew) {
                if (!partidoCollectionOld.contains(partidoCollectionNewPartido)) {
                    Estadio oldIdestadioOfPartidoCollectionNewPartido = partidoCollectionNewPartido.getIdestadio();
                    partidoCollectionNewPartido.setIdestadio(estadio);
                    partidoCollectionNewPartido = em.merge(partidoCollectionNewPartido);
                    if (oldIdestadioOfPartidoCollectionNewPartido != null && !oldIdestadioOfPartidoCollectionNewPartido.equals(estadio)) {
                        oldIdestadioOfPartidoCollectionNewPartido.getPartidoCollection().remove(partidoCollectionNewPartido);
                        oldIdestadioOfPartidoCollectionNewPartido = em.merge(oldIdestadioOfPartidoCollectionNewPartido);
                    }
                }
            }
            for (Silla sillaCollectionNewSilla : sillaCollectionNew) {
                if (!sillaCollectionOld.contains(sillaCollectionNewSilla)) {
                    Estadio oldEstadioOfSillaCollectionNewSilla = sillaCollectionNewSilla.getEstadio();
                    sillaCollectionNewSilla.setEstadio(estadio);
                    sillaCollectionNewSilla = em.merge(sillaCollectionNewSilla);
                    if (oldEstadioOfSillaCollectionNewSilla != null && !oldEstadioOfSillaCollectionNewSilla.equals(estadio)) {
                        oldEstadioOfSillaCollectionNewSilla.getSillaCollection().remove(sillaCollectionNewSilla);
                        oldEstadioOfSillaCollectionNewSilla = em.merge(oldEstadioOfSillaCollectionNewSilla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadio.getIdestadio();
                if (findEstadio(id) == null) {
                    throw new NonexistentEntityException("The estadio with id " + id + " no longer exists.");
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
            Estadio estadio;
            try {
                estadio = em.getReference(Estadio.class, id);
                estadio.getIdestadio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Silla> sillaCollectionOrphanCheck = estadio.getSillaCollection();
            for (Silla sillaCollectionOrphanCheckSilla : sillaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadio (" + estadio + ") cannot be destroyed since the Silla " + sillaCollectionOrphanCheckSilla + " in its sillaCollection field has a non-nullable estadio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad idciudad = estadio.getIdciudad();
            if (idciudad != null) {
                idciudad.getEstadioCollection().remove(estadio);
                idciudad = em.merge(idciudad);
            }
            Collection<Partido> partidoCollection = estadio.getPartidoCollection();
            for (Partido partidoCollectionPartido : partidoCollection) {
                partidoCollectionPartido.setIdestadio(null);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
            }
            em.remove(estadio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estadio> findEstadioEntities() {
        return findEstadioEntities(true, -1, -1);
    }

    public List<Estadio> findEstadioEntities(int maxResults, int firstResult) {
        return findEstadioEntities(false, maxResults, firstResult);
    }

    private List<Estadio> findEstadioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadio.class));
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

    public Estadio findEstadio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estadio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadio> rt = cq.from(Estadio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int obtenerEstadioId(String nombre){
        EntityManager em = getEntityManager();
        Estadio obtener;
        Query consu = em.createNamedQuery("Estadio.findByNombreestadio");
        consu.setParameter("nombreestadio", nombre);
        obtener = (Estadio) consu.getSingleResult();
        return obtener.getIdestadio();
    }
    
}
