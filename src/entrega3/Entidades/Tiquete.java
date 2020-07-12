/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "TIQUETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiquete.findAll", query = "SELECT t FROM Tiquete t")
    , @NamedQuery(name = "Tiquete.findByIdtiquete", query = "SELECT t FROM Tiquete t WHERE t.idtiquete = :idtiquete")
    , @NamedQuery(name = "Tiquete.findByFechahora", query = "SELECT t FROM Tiquete t WHERE t.fechahora = :fechahora")
    , @NamedQuery(name = "Tiquete.findByIdlocal", query = "SELECT t FROM Tiquete t WHERE t.idlocal = :idlocal")
    , @NamedQuery(name = "Tiquete.findByIdvisitante", query = "SELECT t FROM Tiquete t WHERE t.idvisitante = :idvisitante")})
public class Tiquete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIQUETE")
    private Integer idtiquete;
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Column(name = "IDLOCAL")
    private Integer idlocal;
    @Column(name = "IDVISITANTE")
    private Integer idvisitante;
    @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne
    private Cliente idcliente;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne
    private Partido idpartido;
    @OneToMany(mappedBy = "idtiquete")
    private Collection<Entrada> entradaCollection;
    @OneToMany(mappedBy = "idtiquete")
    private Collection<Pago> pagoCollection;

    public Tiquete() {
    }

    public Tiquete(Integer idtiquete) {
        this.idtiquete = idtiquete;
    }

    public Integer getIdtiquete() {
        return idtiquete;
    }

    public void setIdtiquete(Integer idtiquete) {
        this.idtiquete = idtiquete;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Integer getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Integer idlocal) {
        this.idlocal = idlocal;
    }

    public Integer getIdvisitante() {
        return idvisitante;
    }

    public void setIdvisitante(Integer idvisitante) {
        this.idvisitante = idvisitante;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Partido getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Partido idpartido) {
        this.idpartido = idpartido;
    }

    @XmlTransient
    public Collection<Entrada> getEntradaCollection() {
        return entradaCollection;
    }

    public void setEntradaCollection(Collection<Entrada> entradaCollection) {
        this.entradaCollection = entradaCollection;
    }

    @XmlTransient
    public Collection<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(Collection<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiquete != null ? idtiquete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiquete)) {
            return false;
        }
        Tiquete other = (Tiquete) object;
        if ((this.idtiquete == null && other.idtiquete != null) || (this.idtiquete != null && !this.idtiquete.equals(other.idtiquete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Tiquete[ idtiquete=" + idtiquete + " ]";
    }
    
}
