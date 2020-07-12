/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Cliente;
import entrega3.Entidades.Partido;
import entrega3.Entidades.Entrada;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Pago;
import entrega3.Entidades.Tiquete;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class TiqueteJpaController implements Serializable {

    public TiqueteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tiquete tiquete) throws PreexistingEntityException, Exception {
        if (tiquete.getEntradaCollection() == null) {
            tiquete.setEntradaCollection(new ArrayList<Entrada>());
        }
        if (tiquete.getPagoCollection() == null) {
            tiquete.setPagoCollection(new ArrayList<Pago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = tiquete.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                tiquete.setIdcliente(idcliente);
            }
            Partido idpartido = tiquete.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                tiquete.setIdpartido(idpartido);
            }
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : tiquete.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdentrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            tiquete.setEntradaCollection(attachedEntradaCollection);
            Collection<Pago> attachedPagoCollection = new ArrayList<Pago>();
            for (Pago pagoCollectionPagoToAttach : tiquete.getPagoCollection()) {
                pagoCollectionPagoToAttach = em.getReference(pagoCollectionPagoToAttach.getClass(), pagoCollectionPagoToAttach.getIdpago());
                attachedPagoCollection.add(pagoCollectionPagoToAttach);
            }
            tiquete.setPagoCollection(attachedPagoCollection);
            em.persist(tiquete);
            if (idcliente != null) {
                idcliente.getTiqueteCollection().add(tiquete);
                idcliente = em.merge(idcliente);
            }
            if (idpartido != null) {
                idpartido.getTiqueteCollection().add(tiquete);
                idpartido = em.merge(idpartido);
            }
            for (Entrada entradaCollectionEntrada : tiquete.getEntradaCollection()) {
                Tiquete oldIdtiqueteOfEntradaCollectionEntrada = entradaCollectionEntrada.getIdtiquete();
                entradaCollectionEntrada.setIdtiquete(tiquete);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldIdtiqueteOfEntradaCollectionEntrada != null) {
                    oldIdtiqueteOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldIdtiqueteOfEntradaCollectionEntrada = em.merge(oldIdtiqueteOfEntradaCollectionEntrada);
                }
            }
            for (Pago pagoCollectionPago : tiquete.getPagoCollection()) {
                Tiquete oldIdtiqueteOfPagoCollectionPago = pagoCollectionPago.getIdtiquete();
                pagoCollectionPago.setIdtiquete(tiquete);
                pagoCollectionPago = em.merge(pagoCollectionPago);
                if (oldIdtiqueteOfPagoCollectionPago != null) {
                    oldIdtiqueteOfPagoCollectionPago.getPagoCollection().remove(pagoCollectionPago);
                    oldIdtiqueteOfPagoCollectionPago = em.merge(oldIdtiqueteOfPagoCollectionPago);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiquete(tiquete.getIdtiquete()) != null) {
                throw new PreexistingEntityException("Tiquete " + tiquete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tiquete tiquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiquete persistentTiquete = em.find(Tiquete.class, tiquete.getIdtiquete());
            Cliente idclienteOld = persistentTiquete.getIdcliente();
            Cliente idclienteNew = tiquete.getIdcliente();
            Partido idpartidoOld = persistentTiquete.getIdpartido();
            Partido idpartidoNew = tiquete.getIdpartido();
            Collection<Entrada> entradaCollectionOld = persistentTiquete.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = tiquete.getEntradaCollection();
            Collection<Pago> pagoCollectionOld = persistentTiquete.getPagoCollection();
            Collection<Pago> pagoCollectionNew = tiquete.getPagoCollection();
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                tiquete.setIdcliente(idclienteNew);
            }
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                tiquete.setIdpartido(idpartidoNew);
            }
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdentrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            tiquete.setEntradaCollection(entradaCollectionNew);
            Collection<Pago> attachedPagoCollectionNew = new ArrayList<Pago>();
            for (Pago pagoCollectionNewPagoToAttach : pagoCollectionNew) {
                pagoCollectionNewPagoToAttach = em.getReference(pagoCollectionNewPagoToAttach.getClass(), pagoCollectionNewPagoToAttach.getIdpago());
                attachedPagoCollectionNew.add(pagoCollectionNewPagoToAttach);
            }
            pagoCollectionNew = attachedPagoCollectionNew;
            tiquete.setPagoCollection(pagoCollectionNew);
            tiquete = em.merge(tiquete);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getTiqueteCollection().remove(tiquete);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getTiqueteCollection().add(tiquete);
                idclienteNew = em.merge(idclienteNew);
            }
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.getTiqueteCollection().remove(tiquete);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.getTiqueteCollection().add(tiquete);
                idpartidoNew = em.merge(idpartidoNew);
            }
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    entradaCollectionOldEntrada.setIdtiquete(null);
                    entradaCollectionOldEntrada = em.merge(entradaCollectionOldEntrada);
                }
            }
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    Tiquete oldIdtiqueteOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getIdtiquete();
                    entradaCollectionNewEntrada.setIdtiquete(tiquete);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldIdtiqueteOfEntradaCollectionNewEntrada != null && !oldIdtiqueteOfEntradaCollectionNewEntrada.equals(tiquete)) {
                        oldIdtiqueteOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldIdtiqueteOfEntradaCollectionNewEntrada = em.merge(oldIdtiqueteOfEntradaCollectionNewEntrada);
                    }
                }
            }
            for (Pago pagoCollectionOldPago : pagoCollectionOld) {
                if (!pagoCollectionNew.contains(pagoCollectionOldPago)) {
                    pagoCollectionOldPago.setIdtiquete(null);
                    pagoCollectionOldPago = em.merge(pagoCollectionOldPago);
                }
            }
            for (Pago pagoCollectionNewPago : pagoCollectionNew) {
                if (!pagoCollectionOld.contains(pagoCollectionNewPago)) {
                    Tiquete oldIdtiqueteOfPagoCollectionNewPago = pagoCollectionNewPago.getIdtiquete();
                    pagoCollectionNewPago.setIdtiquete(tiquete);
                    pagoCollectionNewPago = em.merge(pagoCollectionNewPago);
                    if (oldIdtiqueteOfPagoCollectionNewPago != null && !oldIdtiqueteOfPagoCollectionNewPago.equals(tiquete)) {
                        oldIdtiqueteOfPagoCollectionNewPago.getPagoCollection().remove(pagoCollectionNewPago);
                        oldIdtiqueteOfPagoCollectionNewPago = em.merge(oldIdtiqueteOfPagoCollectionNewPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiquete.getIdtiquete();
                if (findTiquete(id) == null) {
                    throw new NonexistentEntityException("The tiquete with id " + id + " no longer exists.");
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
            Tiquete tiquete;
            try {
                tiquete = em.getReference(Tiquete.class, id);
                tiquete.getIdtiquete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiquete with id " + id + " no longer exists.", enfe);
            }
            Cliente idcliente = tiquete.getIdcliente();
            if (idcliente != null) {
                idcliente.getTiqueteCollection().remove(tiquete);
                idcliente = em.merge(idcliente);
            }
            Partido idpartido = tiquete.getIdpartido();
            if (idpartido != null) {
                idpartido.getTiqueteCollection().remove(tiquete);
                idpartido = em.merge(idpartido);
            }
            Collection<Entrada> entradaCollection = tiquete.getEntradaCollection();
            for (Entrada entradaCollectionEntrada : entradaCollection) {
                entradaCollectionEntrada.setIdtiquete(null);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
            }
            Collection<Pago> pagoCollection = tiquete.getPagoCollection();
            for (Pago pagoCollectionPago : pagoCollection) {
                pagoCollectionPago.setIdtiquete(null);
                pagoCollectionPago = em.merge(pagoCollectionPago);
            }
            em.remove(tiquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tiquete> findTiqueteEntities() {
        return findTiqueteEntities(true, -1, -1);
    }

    public List<Tiquete> findTiqueteEntities(int maxResults, int firstResult) {
        return findTiqueteEntities(false, maxResults, firstResult);
    }

    private List<Tiquete> findTiqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiquete.class));
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

    public Tiquete findTiquete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tiquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiquete> rt = cq.from(Tiquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
