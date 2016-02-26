/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ALVIS
 */
@Entity
@Table(name = "CELDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Celda.findAll", query = "SELECT c FROM Celda c"),
    @NamedQuery(name = "Celda.findByIdcelda", query = "SELECT c FROM Celda c WHERE c.idcelda = :idcelda"),
    @NamedQuery(name = "Celda.findByAncho", query = "SELECT c FROM Celda c WHERE c.ancho = :ancho"),
    @NamedQuery(name = "Celda.findByProfundidad", query = "SELECT c FROM Celda c WHERE c.profundidad = :profundidad"),
    @NamedQuery(name = "Celda.findByArea", query = "SELECT c FROM Celda c WHERE c.area = :area"),
    @NamedQuery(name = "Celda.findByVelocidad", query = "SELECT c FROM Celda c WHERE c.velocidad = :velocidad"),
    @NamedQuery(name = "Celda.findByCaudal", query = "SELECT c FROM Celda c WHERE c.caudal = :caudal")})
public class Celda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCELDA")
    private Short idcelda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ANCHO")
    private Double ancho;
    @Column(name = "PROFUNDIDAD")
    private Double profundidad;
    @Column(name = "AREA")
    private Double area;
    @Column(name = "VELOCIDAD")
    private Double velocidad;
    @Column(name = "CAUDAL")
    private Double caudal;
    @JoinColumn(name = "IDAFORO", referencedColumnName = "IDAFORO")
    @ManyToOne(optional = false)
    private Aforo idaforo;

    public Celda() {
    }

    public Celda(Short idcelda) {
        this.idcelda = idcelda;
    }

    public Short getIdcelda() {
        return idcelda;
    }

    public void setIdcelda(Short idcelda) {
        this.idcelda = idcelda;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getCaudal() {
        return caudal;
    }

    public void setCaudal(Double caudal) {
        this.caudal = caudal;
    }

    public Aforo getIdaforo() {
        return idaforo;
    }

    public void setIdaforo(Aforo idaforo) {
        this.idaforo = idaforo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcelda != null ? idcelda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Celda)) {
            return false;
        }
        Celda other = (Celda) object;
        if ((this.idcelda == null && other.idcelda != null) || (this.idcelda != null && !this.idcelda.equals(other.idcelda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Celda[ idcelda=" + idcelda + " ]";
    }
    
}
