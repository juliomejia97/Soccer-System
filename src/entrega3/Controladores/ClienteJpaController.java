/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.IllegalOrphanException;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Persona;
import entrega3.Entidades.Tarjetacredito;
import entrega3.Entidades.Tiquete;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (cliente.getTiqueteCollection() == null) {
            cliente.setTiqueteCollection(new ArrayList<Tiquete>());
        }
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = cliente.getPersona();
        if (personaOrphanCheck != null) {
            Cliente oldClienteOfPersona = personaOrphanCheck.getCliente();
            if (oldClienteOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Cliente whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                cliente.setPersona(persona);
            }
            Tarjetacredito numerotarjeta = cliente.getNumerotarjeta();
            if (numerotarjeta != null) {
                numerotarjeta = em.getReference(numerotarjeta.getClass(), numerotarjeta.getNumerotarjeta());
                cliente.setNumerotarjeta(numerotarjeta);
            }
            Collection<Tiquete> attachedTiqueteCollection = new ArrayList<Tiquete>();
            for (Tiquete tiqueteCollectionTiqueteToAttach : cliente.getTiqueteCollection()) {
                tiqueteCollectionTiqueteToAttach = em.getReference(tiqueteCollectionTiqueteToAttach.getClass(), tiqueteCollectionTiqueteToAttach.getIdtiquete());
                attachedTiqueteCollection.add(tiqueteCollectionTiqueteToAttach);
            }
            cliente.setTiqueteCollection(attachedTiqueteCollection);
            em.persist(cliente);
            if (persona != null) {
                persona.setCliente(cliente);
                persona = em.merge(persona);
            }
            if (numerotarjeta != null) {
                numerotarjeta.getClienteCollection().add(cliente);
                numerotarjeta = em.merge(numerotarjeta);
            }
            for (Tiquete tiqueteCollectionTiquete : cliente.getTiqueteCollection()) {
                Cliente oldIdclienteOfTiqueteCollectionTiquete = tiqueteCollectionTiquete.getIdcliente();
                tiqueteCollectionTiquete.setIdcliente(cliente);
                tiqueteCollectionTiquete = em.merge(tiqueteCollectionTiquete);
                if (oldIdclienteOfTiqueteCollectionTiquete != null) {
                    oldIdclienteOfTiqueteCollectionTiquete.getTiqueteCollection().remove(tiqueteCollectionTiquete);
                    oldIdclienteOfTiqueteCollectionTiquete = em.merge(oldIdclienteOfTiqueteCollectionTiquete);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdcliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Persona personaOld = persistentCliente.getPersona();
            Persona personaNew = cliente.getPersona();
            Tarjetacredito numerotarjetaOld = persistentCliente.getNumerotarjeta();
            Tarjetacredito numerotarjetaNew = cliente.getNumerotarjeta();
            Collection<Tiquete> tiqueteCollectionOld = persistentCliente.getTiqueteCollection();
            Collection<Tiquete> tiqueteCollectionNew = cliente.getTiqueteCollection();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Cliente oldClienteOfPersona = personaNew.getCliente();
                if (oldClienteOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Cliente whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                cliente.setPersona(personaNew);
            }
            if (numerotarjetaNew != null) {
                numerotarjetaNew = em.getReference(numerotarjetaNew.getClass(), numerotarjetaNew.getNumerotarjeta());
                cliente.setNumerotarjeta(numerotarjetaNew);
            }
            Collection<Tiquete> attachedTiqueteCollectionNew = new ArrayList<Tiquete>();
            for (Tiquete tiqueteCollectionNewTiqueteToAttach : tiqueteCollectionNew) {
                tiqueteCollectionNewTiqueteToAttach = em.getReference(tiqueteCollectionNewTiqueteToAttach.getClass(), tiqueteCollectionNewTiqueteToAttach.getIdtiquete());
                attachedTiqueteCollectionNew.add(tiqueteCollectionNewTiqueteToAttach);
            }
            tiqueteCollectionNew = attachedTiqueteCollectionNew;
            cliente.setTiqueteCollection(tiqueteCollectionNew);
            cliente = em.merge(cliente);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setCliente(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setCliente(cliente);
                personaNew = em.merge(personaNew);
            }
            if (numerotarjetaOld != null && !numerotarjetaOld.equals(numerotarjetaNew)) {
                numerotarjetaOld.getClienteCollection().remove(cliente);
                numerotarjetaOld = em.merge(numerotarjetaOld);
            }
            if (numerotarjetaNew != null && !numerotarjetaNew.equals(numerotarjetaOld)) {
                numerotarjetaNew.getClienteCollection().add(cliente);
                numerotarjetaNew = em.merge(numerotarjetaNew);
            }
            for (Tiquete tiqueteCollectionOldTiquete : tiqueteCollectionOld) {
                if (!tiqueteCollectionNew.contains(tiqueteCollectionOldTiquete)) {
                    tiqueteCollectionOldTiquete.setIdcliente(null);
                    tiqueteCollectionOldTiquete = em.merge(tiqueteCollectionOldTiquete);
                }
            }
            for (Tiquete tiqueteCollectionNewTiquete : tiqueteCollectionNew) {
                if (!tiqueteCollectionOld.contains(tiqueteCollectionNewTiquete)) {
                    Cliente oldIdclienteOfTiqueteCollectionNewTiquete = tiqueteCollectionNewTiquete.getIdcliente();
                    tiqueteCollectionNewTiquete.setIdcliente(cliente);
                    tiqueteCollectionNewTiquete = em.merge(tiqueteCollectionNewTiquete);
                    if (oldIdclienteOfTiqueteCollectionNewTiquete != null && !oldIdclienteOfTiqueteCollectionNewTiquete.equals(cliente)) {
                        oldIdclienteOfTiqueteCollectionNewTiquete.getTiqueteCollection().remove(tiqueteCollectionNewTiquete);
                        oldIdclienteOfTiqueteCollectionNewTiquete = em.merge(oldIdclienteOfTiqueteCollectionNewTiquete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona.setCliente(null);
                persona = em.merge(persona);
            }
            Tarjetacredito numerotarjeta = cliente.getNumerotarjeta();
            if (numerotarjeta != null) {
                numerotarjeta.getClienteCollection().remove(cliente);
                numerotarjeta = em.merge(numerotarjeta);
            }
            Collection<Tiquete> tiqueteCollection = cliente.getTiqueteCollection();
            for (Tiquete tiqueteCollectionTiquete : tiqueteCollection) {
                tiqueteCollectionTiquete.setIdcliente(null);
                tiqueteCollectionTiquete = em.merge(tiqueteCollectionTiquete);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
