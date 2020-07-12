/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import Pesistencia.Conexion;
import entrega3.Controladores.exceptions.IllegalOrphanException;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Estadio;
import entrega3.Entidades.Fase;
import entrega3.Entidades.Tiquete;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Partidoxequipo;
import entrega3.Entidades.Anotacion;
import entrega3.Entidades.Tarjeta;
import entrega3.Entidades.Alineacion;
import entrega3.Entidades.Partido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PartidoJpaController implements Serializable {

    public PartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partido partido) throws PreexistingEntityException, Exception {
        if (partido.getTiqueteCollection() == null) {
            partido.setTiqueteCollection(new ArrayList<Tiquete>());
        }
        if (partido.getPartidoxequipoCollection() == null) {
            partido.setPartidoxequipoCollection(new ArrayList<Partidoxequipo>());
        }
        if (partido.getAnotacionCollection() == null) {
            partido.setAnotacionCollection(new ArrayList<Anotacion>());
        }
        if (partido.getTarjetaCollection() == null) {
            partido.setTarjetaCollection(new ArrayList<Tarjeta>());
        }
        if (partido.getAlineacionCollection() == null) {
            partido.setAlineacionCollection(new ArrayList<Alineacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadio idestadio = partido.getIdestadio();
            if (idestadio != null) {
                idestadio = em.getReference(idestadio.getClass(), idestadio.getIdestadio());
                partido.setIdestadio(idestadio);
            }
            Fase idfase = partido.getIdfase();
            if (idfase != null) {
                idfase = em.getReference(idfase.getClass(), idfase.getIdfase());
                partido.setIdfase(idfase);
            }
            Collection<Tiquete> attachedTiqueteCollection = new ArrayList<Tiquete>();
            for (Tiquete tiqueteCollectionTiqueteToAttach : partido.getTiqueteCollection()) {
                tiqueteCollectionTiqueteToAttach = em.getReference(tiqueteCollectionTiqueteToAttach.getClass(), tiqueteCollectionTiqueteToAttach.getIdtiquete());
                attachedTiqueteCollection.add(tiqueteCollectionTiqueteToAttach);
            }
            partido.setTiqueteCollection(attachedTiqueteCollection);
            Collection<Partidoxequipo> attachedPartidoxequipoCollection = new ArrayList<Partidoxequipo>();
            for (Partidoxequipo partidoxequipoCollectionPartidoxequipoToAttach : partido.getPartidoxequipoCollection()) {
                partidoxequipoCollectionPartidoxequipoToAttach = em.getReference(partidoxequipoCollectionPartidoxequipoToAttach.getClass(), partidoxequipoCollectionPartidoxequipoToAttach.getPartidoxequipoPK());
                attachedPartidoxequipoCollection.add(partidoxequipoCollectionPartidoxequipoToAttach);
            }
            partido.setPartidoxequipoCollection(attachedPartidoxequipoCollection);
            Collection<Anotacion> attachedAnotacionCollection = new ArrayList<Anotacion>();
            for (Anotacion anotacionCollectionAnotacionToAttach : partido.getAnotacionCollection()) {
                anotacionCollectionAnotacionToAttach = em.getReference(anotacionCollectionAnotacionToAttach.getClass(), anotacionCollectionAnotacionToAttach.getIdanotacion());
                attachedAnotacionCollection.add(anotacionCollectionAnotacionToAttach);
            }
            partido.setAnotacionCollection(attachedAnotacionCollection);
            Collection<Tarjeta> attachedTarjetaCollection = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaCollectionTarjetaToAttach : partido.getTarjetaCollection()) {
                tarjetaCollectionTarjetaToAttach = em.getReference(tarjetaCollectionTarjetaToAttach.getClass(), tarjetaCollectionTarjetaToAttach.getIdtarjeta());
                attachedTarjetaCollection.add(tarjetaCollectionTarjetaToAttach);
            }
            partido.setTarjetaCollection(attachedTarjetaCollection);
            Collection<Alineacion> attachedAlineacionCollection = new ArrayList<Alineacion>();
            for (Alineacion alineacionCollectionAlineacionToAttach : partido.getAlineacionCollection()) {
                alineacionCollectionAlineacionToAttach = em.getReference(alineacionCollectionAlineacionToAttach.getClass(), alineacionCollectionAlineacionToAttach.getAlineacionPK());
                attachedAlineacionCollection.add(alineacionCollectionAlineacionToAttach);
            }
            partido.setAlineacionCollection(attachedAlineacionCollection);
            em.persist(partido);
            if (idestadio != null) {
                idestadio.getPartidoCollection().add(partido);
                idestadio = em.merge(idestadio);
            }
            if (idfase != null) {
                idfase.getPartidoCollection().add(partido);
                idfase = em.merge(idfase);
            }
            for (Tiquete tiqueteCollectionTiquete : partido.getTiqueteCollection()) {
                Partido oldIdpartidoOfTiqueteCollectionTiquete = tiqueteCollectionTiquete.getIdpartido();
                tiqueteCollectionTiquete.setIdpartido(partido);
                tiqueteCollectionTiquete = em.merge(tiqueteCollectionTiquete);
                if (oldIdpartidoOfTiqueteCollectionTiquete != null) {
                    oldIdpartidoOfTiqueteCollectionTiquete.getTiqueteCollection().remove(tiqueteCollectionTiquete);
                    oldIdpartidoOfTiqueteCollectionTiquete = em.merge(oldIdpartidoOfTiqueteCollectionTiquete);
                }
            }
            for (Partidoxequipo partidoxequipoCollectionPartidoxequipo : partido.getPartidoxequipoCollection()) {
                Partido oldPartidoOfPartidoxequipoCollectionPartidoxequipo = partidoxequipoCollectionPartidoxequipo.getPartido();
                partidoxequipoCollectionPartidoxequipo.setPartido(partido);
                partidoxequipoCollectionPartidoxequipo = em.merge(partidoxequipoCollectionPartidoxequipo);
                if (oldPartidoOfPartidoxequipoCollectionPartidoxequipo != null) {
                    oldPartidoOfPartidoxequipoCollectionPartidoxequipo.getPartidoxequipoCollection().remove(partidoxequipoCollectionPartidoxequipo);
                    oldPartidoOfPartidoxequipoCollectionPartidoxequipo = em.merge(oldPartidoOfPartidoxequipoCollectionPartidoxequipo);
                }
            }
            for (Anotacion anotacionCollectionAnotacion : partido.getAnotacionCollection()) {
                Partido oldIdpartidoOfAnotacionCollectionAnotacion = anotacionCollectionAnotacion.getIdpartido();
                anotacionCollectionAnotacion.setIdpartido(partido);
                anotacionCollectionAnotacion = em.merge(anotacionCollectionAnotacion);
                if (oldIdpartidoOfAnotacionCollectionAnotacion != null) {
                    oldIdpartidoOfAnotacionCollectionAnotacion.getAnotacionCollection().remove(anotacionCollectionAnotacion);
                    oldIdpartidoOfAnotacionCollectionAnotacion = em.merge(oldIdpartidoOfAnotacionCollectionAnotacion);
                }
            }
            for (Tarjeta tarjetaCollectionTarjeta : partido.getTarjetaCollection()) {
                Partido oldIdpartidoOfTarjetaCollectionTarjeta = tarjetaCollectionTarjeta.getIdpartido();
                tarjetaCollectionTarjeta.setIdpartido(partido);
                tarjetaCollectionTarjeta = em.merge(tarjetaCollectionTarjeta);
                if (oldIdpartidoOfTarjetaCollectionTarjeta != null) {
                    oldIdpartidoOfTarjetaCollectionTarjeta.getTarjetaCollection().remove(tarjetaCollectionTarjeta);
                    oldIdpartidoOfTarjetaCollectionTarjeta = em.merge(oldIdpartidoOfTarjetaCollectionTarjeta);
                }
            }
            for (Alineacion alineacionCollectionAlineacion : partido.getAlineacionCollection()) {
                Partido oldPartidoOfAlineacionCollectionAlineacion = alineacionCollectionAlineacion.getPartido();
                alineacionCollectionAlineacion.setPartido(partido);
                alineacionCollectionAlineacion = em.merge(alineacionCollectionAlineacion);
                if (oldPartidoOfAlineacionCollectionAlineacion != null) {
                    oldPartidoOfAlineacionCollectionAlineacion.getAlineacionCollection().remove(alineacionCollectionAlineacion);
                    oldPartidoOfAlineacionCollectionAlineacion = em.merge(oldPartidoOfAlineacionCollectionAlineacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartido(partido.getIdpartido()) != null) {
                throw new PreexistingEntityException("Partido " + partido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partido partido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido persistentPartido = em.find(Partido.class, partido.getIdpartido());
            Estadio idestadioOld = persistentPartido.getIdestadio();
            Estadio idestadioNew = partido.getIdestadio();
            Fase idfaseOld = persistentPartido.getIdfase();
            Fase idfaseNew = partido.getIdfase();
            Collection<Tiquete> tiqueteCollectionOld = persistentPartido.getTiqueteCollection();
            Collection<Tiquete> tiqueteCollectionNew = partido.getTiqueteCollection();
            Collection<Partidoxequipo> partidoxequipoCollectionOld = persistentPartido.getPartidoxequipoCollection();
            Collection<Partidoxequipo> partidoxequipoCollectionNew = partido.getPartidoxequipoCollection();
            Collection<Anotacion> anotacionCollectionOld = persistentPartido.getAnotacionCollection();
            Collection<Anotacion> anotacionCollectionNew = partido.getAnotacionCollection();
            Collection<Tarjeta> tarjetaCollectionOld = persistentPartido.getTarjetaCollection();
            Collection<Tarjeta> tarjetaCollectionNew = partido.getTarjetaCollection();
            Collection<Alineacion> alineacionCollectionOld = persistentPartido.getAlineacionCollection();
            Collection<Alineacion> alineacionCollectionNew = partido.getAlineacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Partidoxequipo partidoxequipoCollectionOldPartidoxequipo : partidoxequipoCollectionOld) {
                if (!partidoxequipoCollectionNew.contains(partidoxequipoCollectionOldPartidoxequipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidoxequipo " + partidoxequipoCollectionOldPartidoxequipo + " since its partido field is not nullable.");
                }
            }
            for (Alineacion alineacionCollectionOldAlineacion : alineacionCollectionOld) {
                if (!alineacionCollectionNew.contains(alineacionCollectionOldAlineacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alineacion " + alineacionCollectionOldAlineacion + " since its partido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idestadioNew != null) {
                idestadioNew = em.getReference(idestadioNew.getClass(), idestadioNew.getIdestadio());
                partido.setIdestadio(idestadioNew);
            }
            if (idfaseNew != null) {
                idfaseNew = em.getReference(idfaseNew.getClass(), idfaseNew.getIdfase());
                partido.setIdfase(idfaseNew);
            }
            Collection<Tiquete> attachedTiqueteCollectionNew = new ArrayList<Tiquete>();
            for (Tiquete tiqueteCollectionNewTiqueteToAttach : tiqueteCollectionNew) {
                tiqueteCollectionNewTiqueteToAttach = em.getReference(tiqueteCollectionNewTiqueteToAttach.getClass(), tiqueteCollectionNewTiqueteToAttach.getIdtiquete());
                attachedTiqueteCollectionNew.add(tiqueteCollectionNewTiqueteToAttach);
            }
            tiqueteCollectionNew = attachedTiqueteCollectionNew;
            partido.setTiqueteCollection(tiqueteCollectionNew);
            Collection<Partidoxequipo> attachedPartidoxequipoCollectionNew = new ArrayList<Partidoxequipo>();
            for (Partidoxequipo partidoxequipoCollectionNewPartidoxequipoToAttach : partidoxequipoCollectionNew) {
                partidoxequipoCollectionNewPartidoxequipoToAttach = em.getReference(partidoxequipoCollectionNewPartidoxequipoToAttach.getClass(), partidoxequipoCollectionNewPartidoxequipoToAttach.getPartidoxequipoPK());
                attachedPartidoxequipoCollectionNew.add(partidoxequipoCollectionNewPartidoxequipoToAttach);
            }
            partidoxequipoCollectionNew = attachedPartidoxequipoCollectionNew;
            partido.setPartidoxequipoCollection(partidoxequipoCollectionNew);
            Collection<Anotacion> attachedAnotacionCollectionNew = new ArrayList<Anotacion>();
            for (Anotacion anotacionCollectionNewAnotacionToAttach : anotacionCollectionNew) {
                anotacionCollectionNewAnotacionToAttach = em.getReference(anotacionCollectionNewAnotacionToAttach.getClass(), anotacionCollectionNewAnotacionToAttach.getIdanotacion());
                attachedAnotacionCollectionNew.add(anotacionCollectionNewAnotacionToAttach);
            }
            anotacionCollectionNew = attachedAnotacionCollectionNew;
            partido.setAnotacionCollection(anotacionCollectionNew);
            Collection<Tarjeta> attachedTarjetaCollectionNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaCollectionNewTarjetaToAttach : tarjetaCollectionNew) {
                tarjetaCollectionNewTarjetaToAttach = em.getReference(tarjetaCollectionNewTarjetaToAttach.getClass(), tarjetaCollectionNewTarjetaToAttach.getIdtarjeta());
                attachedTarjetaCollectionNew.add(tarjetaCollectionNewTarjetaToAttach);
            }
            tarjetaCollectionNew = attachedTarjetaCollectionNew;
            partido.setTarjetaCollection(tarjetaCollectionNew);
            Collection<Alineacion> attachedAlineacionCollectionNew = new ArrayList<Alineacion>();
            for (Alineacion alineacionCollectionNewAlineacionToAttach : alineacionCollectionNew) {
                alineacionCollectionNewAlineacionToAttach = em.getReference(alineacionCollectionNewAlineacionToAttach.getClass(), alineacionCollectionNewAlineacionToAttach.getAlineacionPK());
                attachedAlineacionCollectionNew.add(alineacionCollectionNewAlineacionToAttach);
            }
            alineacionCollectionNew = attachedAlineacionCollectionNew;
            partido.setAlineacionCollection(alineacionCollectionNew);
            partido = em.merge(partido);
            if (idestadioOld != null && !idestadioOld.equals(idestadioNew)) {
                idestadioOld.getPartidoCollection().remove(partido);
                idestadioOld = em.merge(idestadioOld);
            }
            if (idestadioNew != null && !idestadioNew.equals(idestadioOld)) {
                idestadioNew.getPartidoCollection().add(partido);
                idestadioNew = em.merge(idestadioNew);
            }
            if (idfaseOld != null && !idfaseOld.equals(idfaseNew)) {
                idfaseOld.getPartidoCollection().remove(partido);
                idfaseOld = em.merge(idfaseOld);
            }
            if (idfaseNew != null && !idfaseNew.equals(idfaseOld)) {
                idfaseNew.getPartidoCollection().add(partido);
                idfaseNew = em.merge(idfaseNew);
            }
            for (Tiquete tiqueteCollectionOldTiquete : tiqueteCollectionOld) {
                if (!tiqueteCollectionNew.contains(tiqueteCollectionOldTiquete)) {
                    tiqueteCollectionOldTiquete.setIdpartido(null);
                    tiqueteCollectionOldTiquete = em.merge(tiqueteCollectionOldTiquete);
                }
            }
            for (Tiquete tiqueteCollectionNewTiquete : tiqueteCollectionNew) {
                if (!tiqueteCollectionOld.contains(tiqueteCollectionNewTiquete)) {
                    Partido oldIdpartidoOfTiqueteCollectionNewTiquete = tiqueteCollectionNewTiquete.getIdpartido();
                    tiqueteCollectionNewTiquete.setIdpartido(partido);
                    tiqueteCollectionNewTiquete = em.merge(tiqueteCollectionNewTiquete);
                    if (oldIdpartidoOfTiqueteCollectionNewTiquete != null && !oldIdpartidoOfTiqueteCollectionNewTiquete.equals(partido)) {
                        oldIdpartidoOfTiqueteCollectionNewTiquete.getTiqueteCollection().remove(tiqueteCollectionNewTiquete);
                        oldIdpartidoOfTiqueteCollectionNewTiquete = em.merge(oldIdpartidoOfTiqueteCollectionNewTiquete);
                    }
                }
            }
            for (Partidoxequipo partidoxequipoCollectionNewPartidoxequipo : partidoxequipoCollectionNew) {
                if (!partidoxequipoCollectionOld.contains(partidoxequipoCollectionNewPartidoxequipo)) {
                    Partido oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo = partidoxequipoCollectionNewPartidoxequipo.getPartido();
                    partidoxequipoCollectionNewPartidoxequipo.setPartido(partido);
                    partidoxequipoCollectionNewPartidoxequipo = em.merge(partidoxequipoCollectionNewPartidoxequipo);
                    if (oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo != null && !oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo.equals(partido)) {
                        oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo.getPartidoxequipoCollection().remove(partidoxequipoCollectionNewPartidoxequipo);
                        oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo = em.merge(oldPartidoOfPartidoxequipoCollectionNewPartidoxequipo);
                    }
                }
            }
            for (Anotacion anotacionCollectionOldAnotacion : anotacionCollectionOld) {
                if (!anotacionCollectionNew.contains(anotacionCollectionOldAnotacion)) {
                    anotacionCollectionOldAnotacion.setIdpartido(null);
                    anotacionCollectionOldAnotacion = em.merge(anotacionCollectionOldAnotacion);
                }
            }
            for (Anotacion anotacionCollectionNewAnotacion : anotacionCollectionNew) {
                if (!anotacionCollectionOld.contains(anotacionCollectionNewAnotacion)) {
                    Partido oldIdpartidoOfAnotacionCollectionNewAnotacion = anotacionCollectionNewAnotacion.getIdpartido();
                    anotacionCollectionNewAnotacion.setIdpartido(partido);
                    anotacionCollectionNewAnotacion = em.merge(anotacionCollectionNewAnotacion);
                    if (oldIdpartidoOfAnotacionCollectionNewAnotacion != null && !oldIdpartidoOfAnotacionCollectionNewAnotacion.equals(partido)) {
                        oldIdpartidoOfAnotacionCollectionNewAnotacion.getAnotacionCollection().remove(anotacionCollectionNewAnotacion);
                        oldIdpartidoOfAnotacionCollectionNewAnotacion = em.merge(oldIdpartidoOfAnotacionCollectionNewAnotacion);
                    }
                }
            }
            for (Tarjeta tarjetaCollectionOldTarjeta : tarjetaCollectionOld) {
                if (!tarjetaCollectionNew.contains(tarjetaCollectionOldTarjeta)) {
                    tarjetaCollectionOldTarjeta.setIdpartido(null);
                    tarjetaCollectionOldTarjeta = em.merge(tarjetaCollectionOldTarjeta);
                }
            }
            for (Tarjeta tarjetaCollectionNewTarjeta : tarjetaCollectionNew) {
                if (!tarjetaCollectionOld.contains(tarjetaCollectionNewTarjeta)) {
                    Partido oldIdpartidoOfTarjetaCollectionNewTarjeta = tarjetaCollectionNewTarjeta.getIdpartido();
                    tarjetaCollectionNewTarjeta.setIdpartido(partido);
                    tarjetaCollectionNewTarjeta = em.merge(tarjetaCollectionNewTarjeta);
                    if (oldIdpartidoOfTarjetaCollectionNewTarjeta != null && !oldIdpartidoOfTarjetaCollectionNewTarjeta.equals(partido)) {
                        oldIdpartidoOfTarjetaCollectionNewTarjeta.getTarjetaCollection().remove(tarjetaCollectionNewTarjeta);
                        oldIdpartidoOfTarjetaCollectionNewTarjeta = em.merge(oldIdpartidoOfTarjetaCollectionNewTarjeta);
                    }
                }
            }
            for (Alineacion alineacionCollectionNewAlineacion : alineacionCollectionNew) {
                if (!alineacionCollectionOld.contains(alineacionCollectionNewAlineacion)) {
                    Partido oldPartidoOfAlineacionCollectionNewAlineacion = alineacionCollectionNewAlineacion.getPartido();
                    alineacionCollectionNewAlineacion.setPartido(partido);
                    alineacionCollectionNewAlineacion = em.merge(alineacionCollectionNewAlineacion);
                    if (oldPartidoOfAlineacionCollectionNewAlineacion != null && !oldPartidoOfAlineacionCollectionNewAlineacion.equals(partido)) {
                        oldPartidoOfAlineacionCollectionNewAlineacion.getAlineacionCollection().remove(alineacionCollectionNewAlineacion);
                        oldPartidoOfAlineacionCollectionNewAlineacion = em.merge(oldPartidoOfAlineacionCollectionNewAlineacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partido.getIdpartido();
                if (findPartido(id) == null) {
                    throw new NonexistentEntityException("The partido with id " + id + " no longer exists.");
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
            Partido partido;
            try {
                partido = em.getReference(Partido.class, id);
                partido.getIdpartido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Partidoxequipo> partidoxequipoCollectionOrphanCheck = partido.getPartidoxequipoCollection();
            for (Partidoxequipo partidoxequipoCollectionOrphanCheckPartidoxequipo : partidoxequipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Partidoxequipo " + partidoxequipoCollectionOrphanCheckPartidoxequipo + " in its partidoxequipoCollection field has a non-nullable partido field.");
            }
            Collection<Alineacion> alineacionCollectionOrphanCheck = partido.getAlineacionCollection();
            for (Alineacion alineacionCollectionOrphanCheckAlineacion : alineacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Alineacion " + alineacionCollectionOrphanCheckAlineacion + " in its alineacionCollection field has a non-nullable partido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estadio idestadio = partido.getIdestadio();
            if (idestadio != null) {
                idestadio.getPartidoCollection().remove(partido);
                idestadio = em.merge(idestadio);
            }
            Fase idfase = partido.getIdfase();
            if (idfase != null) {
                idfase.getPartidoCollection().remove(partido);
                idfase = em.merge(idfase);
            }
            Collection<Tiquete> tiqueteCollection = partido.getTiqueteCollection();
            for (Tiquete tiqueteCollectionTiquete : tiqueteCollection) {
                tiqueteCollectionTiquete.setIdpartido(null);
                tiqueteCollectionTiquete = em.merge(tiqueteCollectionTiquete);
            }
            Collection<Anotacion> anotacionCollection = partido.getAnotacionCollection();
            for (Anotacion anotacionCollectionAnotacion : anotacionCollection) {
                anotacionCollectionAnotacion.setIdpartido(null);
                anotacionCollectionAnotacion = em.merge(anotacionCollectionAnotacion);
            }
            Collection<Tarjeta> tarjetaCollection = partido.getTarjetaCollection();
            for (Tarjeta tarjetaCollectionTarjeta : tarjetaCollection) {
                tarjetaCollectionTarjeta.setIdpartido(null);
                tarjetaCollectionTarjeta = em.merge(tarjetaCollectionTarjeta);
            }
            em.remove(partido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partido> findPartidoEntities() {
        return findPartidoEntities(true, -1, -1);
    }

    public List<Partido> findPartidoEntities(int maxResults, int firstResult) {
        return findPartidoEntities(false, maxResults, firstResult);
    }

    private List<Partido> findPartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partido.class));
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

    public Partido findPartido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partido> rt = cq.from(Partido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public static List<String> equiposCodigoPartido()
    {
        Partido part = new Partido();
        List<String> l = new ArrayList();
        List<Object[]> resultados = part.equiposCodigoPartido();
        String aux;
        for(Object[] row: resultados)
        {
            aux=row[0]+" "+row[1]+" - "+row[2]; 
            l.add(aux);
        }
        return l;
    } 
    
    public static List<Object[]> anotacionesPartido(Integer idPartido)
    {
        Partido part = new Partido();
        List<Object[]> resultados = part.anotacionPartido(idPartido);
        //System.out.println(resultados.size());
        return resultados;
    }

    public List<Object[]> obtenerPartidos() {
         //To change body of generated methods, choose Tools | Templates.
         Partido part = new Partido();
         List<Object[]> resultados = part.obetenerPArtidos();
         return resultados;
        
    }
    
    public List<Object[]> obtenerTarjetas(Integer idPartido){
        Partido part = new Partido();
        List<Object[]> result = part.tarjetasPartido(idPartido);
        return result;
    } 
   
    
}
