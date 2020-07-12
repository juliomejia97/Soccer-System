/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import Pesistencia.Conexion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "SILLAXPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sillaxpartido.findAll", query = "SELECT s FROM Sillaxpartido s")
    , @NamedQuery(name = "Sillaxpartido.findByIdpartido", query = "SELECT s FROM Sillaxpartido s WHERE s.sillaxpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Sillaxpartido.findByIdestadio", query = "SELECT s FROM Sillaxpartido s WHERE s.sillaxpartidoPK.idestadio = :idestadio")
    , @NamedQuery(name = "Sillaxpartido.findByIdsilla", query = "SELECT s FROM Sillaxpartido s WHERE s.sillaxpartidoPK.idsilla = :idsilla")
    , @NamedQuery(name = "Sillaxpartido.findByOcupado", query = "SELECT s FROM Sillaxpartido s WHERE s.ocupado = :ocupado")})
public class Sillaxpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SillaxpartidoPK sillaxpartidoPK;
    @Column(name = "OCUPADO")
    private Short ocupado;

    public Sillaxpartido() {
    }

    public Sillaxpartido(SillaxpartidoPK sillaxpartidoPK) {
        this.sillaxpartidoPK = sillaxpartidoPK;
    }

    public Sillaxpartido(int idpartido, int idestadio, int idsilla) {
        this.sillaxpartidoPK = new SillaxpartidoPK(idpartido, idestadio, idsilla);
    }

    public SillaxpartidoPK getSillaxpartidoPK() {
        return sillaxpartidoPK;
    }

    public void setSillaxpartidoPK(SillaxpartidoPK sillaxpartidoPK) {
        this.sillaxpartidoPK = sillaxpartidoPK;
    }

    public Short getOcupado() {
        return ocupado;
    }

    public void setOcupado(Short ocupado) {
        this.ocupado = ocupado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sillaxpartidoPK != null ? sillaxpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sillaxpartido)) {
            return false;
        }
        Sillaxpartido other = (Sillaxpartido) object;
        if ((this.sillaxpartidoPK == null && other.sillaxpartidoPK != null) || (this.sillaxpartidoPK != null && !this.sillaxpartidoPK.equals(other.sillaxpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Sillaxpartido[ sillaxpartidoPK=" + sillaxpartidoPK + " ]";
    }

    public List<Object[]> mostrarSillasXPartido(Integer idPartido, String nombreEstadio) {
        Conexion con = new Conexion();
        EntityManager em = con.getEm();
        Query consu = em.createQuery("select c.nombrecategoria, p.precio, count(sp.ocupado) \n" +
        "from Categoria c, Sillaxpartido sp, Silla s, Precio p\n" +
        "where c.idcategoria = s.idcategoria.idcategoria and sp.sillaxpartidoPK.idsilla = s.sillaPK.idsilla\n" +
        "and p.categoria.idcategoria = s.idcategoria.idcategoria and p.fase.idfase = 1 and \n" +
        "s.estadio.idestadio=sp.sillaxpartidoPK.idestadio and sp.sillaxpartidoPK.idpartido = :ppartido and sp.ocupado = 0 \n" +
        "group by c.nombrecategoria, p.precio");
        consu.setParameter("ppartido", idPartido);
        List<Object[]> results = consu.getResultList();
        return results;
    }
    public List<Integer> mostrarSillasXPartidoC(Integer idPartido, String nombreEstadio, Integer idCategoria) {
        Conexion con = new Conexion();
        EntityManager em = con.getEm();
        Query consu = em.createQuery("select sp.sillaxpartidoPK.idsilla\n" +
        "from Sillaxpartido sp, Silla s\n" +
        "where sp.sillaxpartidoPK.idsilla = s.sillaPK.idsilla \n" +
        "and sp.sillaxpartidoPK.idestadio = s.sillaPK.idestadio \n" +
        "and s.idcategoria.idcategoria = :pcategoria and sp.sillaxpartidoPK.idpartido= :ppartido and sp.ocupado = 0");
        consu.setParameter("ppartido", idPartido);
        consu.setParameter("pcategoria", idCategoria);
        List<Integer> results = consu.getResultList();
        
        return results;
    }
}
