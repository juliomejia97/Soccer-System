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
import entrega3.Entidades.Categoria;
import entrega3.Entidades.Fase;
import entrega3.Entidades.Precio;
import entrega3.Entidades.PrecioPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PrecioJpaController implements Serializable {

    public PrecioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Precio precio) throws PreexistingEntityException, Exception {
        if (precio.getPrecioPK() == null) {
            precio.setPrecioPK(new PrecioPK());
        }
        precio.getPrecioPK().setIdfase(precio.getFase().getIdfase());
        precio.getPrecioPK().setIdcategoria(precio.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = precio.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdcategoria());
                precio.setCategoria(categoria);
            }
            Fase fase = precio.getFase();
            if (fase != null) {
                fase = em.getReference(fase.getClass(), fase.getIdfase());
                precio.setFase(fase);
            }
            em.persist(precio);
            if (categoria != null) {
                categoria.getPrecioCollection().add(precio);
                categoria = em.merge(categoria);
            }
            if (fase != null) {
                fase.getPrecioCollection().add(precio);
                fase = em.merge(fase);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrecio(precio.getPrecioPK()) != null) {
                throw new PreexistingEntityException("Precio " + precio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Precio precio) throws NonexistentEntityException, Exception {
        precio.getPrecioPK().setIdfase(precio.getFase().getIdfase());
        precio.getPrecioPK().setIdcategoria(precio.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Precio persistentPrecio = em.find(Precio.class, precio.getPrecioPK());
            Categoria categoriaOld = persistentPrecio.getCategoria();
            Categoria categoriaNew = precio.getCategoria();
            Fase faseOld = persistentPrecio.getFase();
            Fase faseNew = precio.getFase();
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdcategoria());
                precio.setCategoria(categoriaNew);
            }
            if (faseNew != null) {
                faseNew = em.getReference(faseNew.getClass(), faseNew.getIdfase());
                precio.setFase(faseNew);
            }
            precio = em.merge(precio);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getPrecioCollection().remove(precio);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getPrecioCollection().add(precio);
                categoriaNew = em.merge(categoriaNew);
            }
            if (faseOld != null && !faseOld.equals(faseNew)) {
                faseOld.getPrecioCollection().remove(precio);
                faseOld = em.merge(faseOld);
            }
            if (faseNew != null && !faseNew.equals(faseOld)) {
                faseNew.getPrecioCollection().add(precio);
                faseNew = em.merge(faseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrecioPK id = precio.getPrecioPK();
                if (findPrecio(id) == null) {
                    throw new NonexistentEntityException("The precio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrecioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Precio precio;
            try {
                precio = em.getReference(Precio.class, id);
                precio.getPrecioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The precio with id " + id + " no longer exists.", enfe);
            }
            Categoria categoria = precio.getCategoria();
            if (categoria != null) {
                categoria.getPrecioCollection().remove(precio);
                categoria = em.merge(categoria);
            }
            Fase fase = precio.getFase();
            if (fase != null) {
                fase.getPrecioCollection().remove(precio);
                fase = em.merge(fase);
            }
            em.remove(precio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Precio> findPrecioEntities() {
        return findPrecioEntities(true, -1, -1);
    }

    public List<Precio> findPrecioEntities(int maxResults, int firstResult) {
        return findPrecioEntities(false, maxResults, firstResult);
    }

    private List<Precio> findPrecioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Precio.class));
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

    public Precio findPrecio(PrecioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Precio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrecioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Precio> rt = cq.from(Precio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
