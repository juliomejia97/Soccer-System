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
import entrega3.Entidades.Banco;
import entrega3.Entidades.Pago;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Cliente;
import entrega3.Entidades.Tarjetacredito;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class TarjetacreditoJpaController implements Serializable {

    public TarjetacreditoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarjetacredito tarjetacredito) throws PreexistingEntityException, Exception {
        if (tarjetacredito.getPagoCollection() == null) {
            tarjetacredito.setPagoCollection(new ArrayList<Pago>());
        }
        if (tarjetacredito.getClienteCollection() == null) {
            tarjetacredito.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco idbanco = tarjetacredito.getIdbanco();
            if (idbanco != null) {
                idbanco = em.getReference(idbanco.getClass(), idbanco.getIdbanco());
                tarjetacredito.setIdbanco(idbanco);
            }
            Collection<Pago> attachedPagoCollection = new ArrayList<Pago>();
            for (Pago pagoCollectionPagoToAttach : tarjetacredito.getPagoCollection()) {
                pagoCollectionPagoToAttach = em.getReference(pagoCollectionPagoToAttach.getClass(), pagoCollectionPagoToAttach.getIdpago());
                attachedPagoCollection.add(pagoCollectionPagoToAttach);
            }
            tarjetacredito.setPagoCollection(attachedPagoCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : tarjetacredito.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getIdcliente());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            tarjetacredito.setClienteCollection(attachedClienteCollection);
            em.persist(tarjetacredito);
            if (idbanco != null) {
                idbanco.getTarjetacreditoCollection().add(tarjetacredito);
                idbanco = em.merge(idbanco);
            }
            for (Pago pagoCollectionPago : tarjetacredito.getPagoCollection()) {
                Tarjetacredito oldNumerotarjetaOfPagoCollectionPago = pagoCollectionPago.getNumerotarjeta();
                pagoCollectionPago.setNumerotarjeta(tarjetacredito);
                pagoCollectionPago = em.merge(pagoCollectionPago);
                if (oldNumerotarjetaOfPagoCollectionPago != null) {
                    oldNumerotarjetaOfPagoCollectionPago.getPagoCollection().remove(pagoCollectionPago);
                    oldNumerotarjetaOfPagoCollectionPago = em.merge(oldNumerotarjetaOfPagoCollectionPago);
                }
            }
            for (Cliente clienteCollectionCliente : tarjetacredito.getClienteCollection()) {
                Tarjetacredito oldNumerotarjetaOfClienteCollectionCliente = clienteCollectionCliente.getNumerotarjeta();
                clienteCollectionCliente.setNumerotarjeta(tarjetacredito);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldNumerotarjetaOfClienteCollectionCliente != null) {
                    oldNumerotarjetaOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldNumerotarjetaOfClienteCollectionCliente = em.merge(oldNumerotarjetaOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjetacredito(tarjetacredito.getNumerotarjeta()) != null) {
                throw new PreexistingEntityException("Tarjetacredito " + tarjetacredito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarjetacredito tarjetacredito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjetacredito persistentTarjetacredito = em.find(Tarjetacredito.class, tarjetacredito.getNumerotarjeta());
            Banco idbancoOld = persistentTarjetacredito.getIdbanco();
            Banco idbancoNew = tarjetacredito.getIdbanco();
            Collection<Pago> pagoCollectionOld = persistentTarjetacredito.getPagoCollection();
            Collection<Pago> pagoCollectionNew = tarjetacredito.getPagoCollection();
            Collection<Cliente> clienteCollectionOld = persistentTarjetacredito.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = tarjetacredito.getClienteCollection();
            if (idbancoNew != null) {
                idbancoNew = em.getReference(idbancoNew.getClass(), idbancoNew.getIdbanco());
                tarjetacredito.setIdbanco(idbancoNew);
            }
            Collection<Pago> attachedPagoCollectionNew = new ArrayList<Pago>();
            for (Pago pagoCollectionNewPagoToAttach : pagoCollectionNew) {
                pagoCollectionNewPagoToAttach = em.getReference(pagoCollectionNewPagoToAttach.getClass(), pagoCollectionNewPagoToAttach.getIdpago());
                attachedPagoCollectionNew.add(pagoCollectionNewPagoToAttach);
            }
            pagoCollectionNew = attachedPagoCollectionNew;
            tarjetacredito.setPagoCollection(pagoCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getIdcliente());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            tarjetacredito.setClienteCollection(clienteCollectionNew);
            tarjetacredito = em.merge(tarjetacredito);
            if (idbancoOld != null && !idbancoOld.equals(idbancoNew)) {
                idbancoOld.getTarjetacreditoCollection().remove(tarjetacredito);
                idbancoOld = em.merge(idbancoOld);
            }
            if (idbancoNew != null && !idbancoNew.equals(idbancoOld)) {
                idbancoNew.getTarjetacreditoCollection().add(tarjetacredito);
                idbancoNew = em.merge(idbancoNew);
            }
            for (Pago pagoCollectionOldPago : pagoCollectionOld) {
                if (!pagoCollectionNew.contains(pagoCollectionOldPago)) {
                    pagoCollectionOldPago.setNumerotarjeta(null);
                    pagoCollectionOldPago = em.merge(pagoCollectionOldPago);
                }
            }
            for (Pago pagoCollectionNewPago : pagoCollectionNew) {
                if (!pagoCollectionOld.contains(pagoCollectionNewPago)) {
                    Tarjetacredito oldNumerotarjetaOfPagoCollectionNewPago = pagoCollectionNewPago.getNumerotarjeta();
                    pagoCollectionNewPago.setNumerotarjeta(tarjetacredito);
                    pagoCollectionNewPago = em.merge(pagoCollectionNewPago);
                    if (oldNumerotarjetaOfPagoCollectionNewPago != null && !oldNumerotarjetaOfPagoCollectionNewPago.equals(tarjetacredito)) {
                        oldNumerotarjetaOfPagoCollectionNewPago.getPagoCollection().remove(pagoCollectionNewPago);
                        oldNumerotarjetaOfPagoCollectionNewPago = em.merge(oldNumerotarjetaOfPagoCollectionNewPago);
                    }
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setNumerotarjeta(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Tarjetacredito oldNumerotarjetaOfClienteCollectionNewCliente = clienteCollectionNewCliente.getNumerotarjeta();
                    clienteCollectionNewCliente.setNumerotarjeta(tarjetacredito);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldNumerotarjetaOfClienteCollectionNewCliente != null && !oldNumerotarjetaOfClienteCollectionNewCliente.equals(tarjetacredito)) {
                        oldNumerotarjetaOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldNumerotarjetaOfClienteCollectionNewCliente = em.merge(oldNumerotarjetaOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarjetacredito.getNumerotarjeta();
                if (findTarjetacredito(id) == null) {
                    throw new NonexistentEntityException("The tarjetacredito with id " + id + " no longer exists.");
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
            Tarjetacredito tarjetacredito;
            try {
                tarjetacredito = em.getReference(Tarjetacredito.class, id);
                tarjetacredito.getNumerotarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjetacredito with id " + id + " no longer exists.", enfe);
            }
            Banco idbanco = tarjetacredito.getIdbanco();
            if (idbanco != null) {
                idbanco.getTarjetacreditoCollection().remove(tarjetacredito);
                idbanco = em.merge(idbanco);
            }
            Collection<Pago> pagoCollection = tarjetacredito.getPagoCollection();
            for (Pago pagoCollectionPago : pagoCollection) {
                pagoCollectionPago.setNumerotarjeta(null);
                pagoCollectionPago = em.merge(pagoCollectionPago);
            }
            Collection<Cliente> clienteCollection = tarjetacredito.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setNumerotarjeta(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(tarjetacredito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarjetacredito> findTarjetacreditoEntities() {
        return findTarjetacreditoEntities(true, -1, -1);
    }

    public List<Tarjetacredito> findTarjetacreditoEntities(int maxResults, int firstResult) {
        return findTarjetacreditoEntities(false, maxResults, firstResult);
    }

    private List<Tarjetacredito> findTarjetacreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarjetacredito.class));
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

    public Tarjetacredito findTarjetacredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarjetacredito.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetacreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarjetacredito> rt = cq.from(Tarjetacredito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
