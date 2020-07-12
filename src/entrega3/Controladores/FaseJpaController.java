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
import entrega3.Entidades.Partido;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Entrada;
import entrega3.Entidades.Fase;
import entrega3.Entidades.Precio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class FaseJpaController implements Serializable {

    public FaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fase fase) throws PreexistingEntityException, Exception {
        if (fase.getPartidoCollection() == null) {
            fase.setPartidoCollection(new ArrayList<Partido>());
        }
        if (fase.getEntradaCollection() == null) {
            fase.setEntradaCollection(new ArrayList<Entrada>());
        }
        if (fase.getPrecioCollection() == null) {
            fase.setPrecioCollection(new ArrayList<Precio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Partido> attachedPartidoCollection = new ArrayList<Partido>();
            for (Partido partidoCollectionPartidoToAttach : fase.getPartidoCollection()) {
                partidoCollectionPartidoToAttach = em.getReference(partidoCollectionPartidoToAttach.getClass(), partidoCollectionPartidoToAttach.getIdpartido());
                attachedPartidoCollection.add(partidoCollectionPartidoToAttach);
            }
            fase.setPartidoCollection(attachedPartidoCollection);
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : fase.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdentrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            fase.setEntradaCollection(attachedEntradaCollection);
            Collection<Precio> attachedPrecioCollection = new ArrayList<Precio>();
            for (Precio precioCollectionPrecioToAttach : fase.getPrecioCollection()) {
                precioCollectionPrecioToAttach = em.getReference(precioCollectionPrecioToAttach.getClass(), precioCollectionPrecioToAttach.getPrecioPK());
                attachedPrecioCollection.add(precioCollectionPrecioToAttach);
            }
            fase.setPrecioCollection(attachedPrecioCollection);
            em.persist(fase);
            for (Partido partidoCollectionPartido : fase.getPartidoCollection()) {
                Fase oldIdfaseOfPartidoCollectionPartido = partidoCollectionPartido.getIdfase();
                partidoCollectionPartido.setIdfase(fase);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
                if (oldIdfaseOfPartidoCollectionPartido != null) {
                    oldIdfaseOfPartidoCollectionPartido.getPartidoCollection().remove(partidoCollectionPartido);
                    oldIdfaseOfPartidoCollectionPartido = em.merge(oldIdfaseOfPartidoCollectionPartido);
                }
            }
            for (Entrada entradaCollectionEntrada : fase.getEntradaCollection()) {
                Fase oldIdfaseOfEntradaCollectionEntrada = entradaCollectionEntrada.getIdfase();
                entradaCollectionEntrada.setIdfase(fase);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldIdfaseOfEntradaCollectionEntrada != null) {
                    oldIdfaseOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldIdfaseOfEntradaCollectionEntrada = em.merge(oldIdfaseOfEntradaCollectionEntrada);
                }
            }
            for (Precio precioCollectionPrecio : fase.getPrecioCollection()) {
                Fase oldFaseOfPrecioCollectionPrecio = precioCollectionPrecio.getFase();
                precioCollectionPrecio.setFase(fase);
                precioCollectionPrecio = em.merge(precioCollectionPrecio);
                if (oldFaseOfPrecioCollectionPrecio != null) {
                    oldFaseOfPrecioCollectionPrecio.getPrecioCollection().remove(precioCollectionPrecio);
                    oldFaseOfPrecioCollectionPrecio = em.merge(oldFaseOfPrecioCollectionPrecio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFase(fase.getIdfase()) != null) {
                throw new PreexistingEntityException("Fase " + fase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fase fase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fase persistentFase = em.find(Fase.class, fase.getIdfase());
            Collection<Partido> partidoCollectionOld = persistentFase.getPartidoCollection();
            Collection<Partido> partidoCollectionNew = fase.getPartidoCollection();
            Collection<Entrada> entradaCollectionOld = persistentFase.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = fase.getEntradaCollection();
            Collection<Precio> precioCollectionOld = persistentFase.getPrecioCollection();
            Collection<Precio> precioCollectionNew = fase.getPrecioCollection();
            List<String> illegalOrphanMessages = null;
            for (Precio precioCollectionOldPrecio : precioCollectionOld) {
                if (!precioCollectionNew.contains(precioCollectionOldPrecio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Precio " + precioCollectionOldPrecio + " since its fase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Partido> attachedPartidoCollectionNew = new ArrayList<Partido>();
            for (Partido partidoCollectionNewPartidoToAttach : partidoCollectionNew) {
                partidoCollectionNewPartidoToAttach = em.getReference(partidoCollectionNewPartidoToAttach.getClass(), partidoCollectionNewPartidoToAttach.getIdpartido());
                attachedPartidoCollectionNew.add(partidoCollectionNewPartidoToAttach);
            }
            partidoCollectionNew = attachedPartidoCollectionNew;
            fase.setPartidoCollection(partidoCollectionNew);
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdentrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            fase.setEntradaCollection(entradaCollectionNew);
            Collection<Precio> attachedPrecioCollectionNew = new ArrayList<Precio>();
            for (Precio precioCollectionNewPrecioToAttach : precioCollectionNew) {
                precioCollectionNewPrecioToAttach = em.getReference(precioCollectionNewPrecioToAttach.getClass(), precioCollectionNewPrecioToAttach.getPrecioPK());
                attachedPrecioCollectionNew.add(precioCollectionNewPrecioToAttach);
            }
            precioCollectionNew = attachedPrecioCollectionNew;
            fase.setPrecioCollection(precioCollectionNew);
            fase = em.merge(fase);
            for (Partido partidoCollectionOldPartido : partidoCollectionOld) {
                if (!partidoCollectionNew.contains(partidoCollectionOldPartido)) {
                    partidoCollectionOldPartido.setIdfase(null);
                    partidoCollectionOldPartido = em.merge(partidoCollectionOldPartido);
                }
            }
            for (Partido partidoCollectionNewPartido : partidoCollectionNew) {
                if (!partidoCollectionOld.contains(partidoCollectionNewPartido)) {
                    Fase oldIdfaseOfPartidoCollectionNewPartido = partidoCollectionNewPartido.getIdfase();
                    partidoCollectionNewPartido.setIdfase(fase);
                    partidoCollectionNewPartido = em.merge(partidoCollectionNewPartido);
                    if (oldIdfaseOfPartidoCollectionNewPartido != null && !oldIdfaseOfPartidoCollectionNewPartido.equals(fase)) {
                        oldIdfaseOfPartidoCollectionNewPartido.getPartidoCollection().remove(partidoCollectionNewPartido);
                        oldIdfaseOfPartidoCollectionNewPartido = em.merge(oldIdfaseOfPartidoCollectionNewPartido);
                    }
                }
            }
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    entradaCollectionOldEntrada.setIdfase(null);
                    entradaCollectionOldEntrada = em.merge(entradaCollectionOldEntrada);
                }
            }
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    Fase oldIdfaseOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getIdfase();
                    entradaCollectionNewEntrada.setIdfase(fase);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldIdfaseOfEntradaCollectionNewEntrada != null && !oldIdfaseOfEntradaCollectionNewEntrada.equals(fase)) {
                        oldIdfaseOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldIdfaseOfEntradaCollectionNewEntrada = em.merge(oldIdfaseOfEntradaCollectionNewEntrada);
                    }
                }
            }
            for (Precio precioCollectionNewPrecio : precioCollectionNew) {
                if (!precioCollectionOld.contains(precioCollectionNewPrecio)) {
                    Fase oldFaseOfPrecioCollectionNewPrecio = precioCollectionNewPrecio.getFase();
                    precioCollectionNewPrecio.setFase(fase);
                    precioCollectionNewPrecio = em.merge(precioCollectionNewPrecio);
                    if (oldFaseOfPrecioCollectionNewPrecio != null && !oldFaseOfPrecioCollectionNewPrecio.equals(fase)) {
                        oldFaseOfPrecioCollectionNewPrecio.getPrecioCollection().remove(precioCollectionNewPrecio);
                        oldFaseOfPrecioCollectionNewPrecio = em.merge(oldFaseOfPrecioCollectionNewPrecio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fase.getIdfase();
                if (findFase(id) == null) {
                    throw new NonexistentEntityException("The fase with id " + id + " no longer exists.");
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
            Fase fase;
            try {
                fase = em.getReference(Fase.class, id);
                fase.getIdfase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Precio> precioCollectionOrphanCheck = fase.getPrecioCollection();
            for (Precio precioCollectionOrphanCheckPrecio : precioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fase (" + fase + ") cannot be destroyed since the Precio " + precioCollectionOrphanCheckPrecio + " in its precioCollection field has a non-nullable fase field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Partido> partidoCollection = fase.getPartidoCollection();
            for (Partido partidoCollectionPartido : partidoCollection) {
                partidoCollectionPartido.setIdfase(null);
                partidoCollectionPartido = em.merge(partidoCollectionPartido);
            }
            Collection<Entrada> entradaCollection = fase.getEntradaCollection();
            for (Entrada entradaCollectionEntrada : entradaCollection) {
                entradaCollectionEntrada.setIdfase(null);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
            }
            em.remove(fase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fase> findFaseEntities() {
        return findFaseEntities(true, -1, -1);
    }

    public List<Fase> findFaseEntities(int maxResults, int firstResult) {
        return findFaseEntities(false, maxResults, firstResult);
    }

    private List<Fase> findFaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fase.class));
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

    public Fase findFase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fase.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fase> rt = cq.from(Fase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
