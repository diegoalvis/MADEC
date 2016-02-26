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
@Table(name = "VERTICAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vertical.findAll", query = "SELECT v FROM Vertical v"),
    @NamedQuery(name = "Vertical.findByIdvertical", query = "SELECT v FROM Vertical v WHERE v.idvertical = :idvertical"),
    @NamedQuery(name = "Vertical.findByVueltas20", query = "SELECT v FROM Vertical v WHERE v.vueltas20 = :vueltas20"),
    @NamedQuery(name = "Vertical.findByVueltas40", query = "SELECT v FROM Vertical v WHERE v.vueltas40 = :vueltas40"),
    @NamedQuery(name = "Vertical.findByVueltas60", query = "SELECT v FROM Vertical v WHERE v.vueltas60 = :vueltas60"),
    @NamedQuery(name = "Vertical.findByVueltas80", query = "SELECT v FROM Vertical v WHERE v.vueltas80 = :vueltas80"),
    @NamedQuery(name = "Vertical.findByVueltasSup", query = "SELECT v FROM Vertical v WHERE v.vueltasSup = :vueltasSup"),
    @NamedQuery(name = "Vertical.findByVueltasFondo", query = "SELECT v FROM Vertical v WHERE v.vueltasFondo = :vueltasFondo"),
    @NamedQuery(name = "Vertical.findByVelocidad", query = "SELECT v FROM Vertical v WHERE v.velocidad = :velocidad"),
    @NamedQuery(name = "Vertical.findByVelocidadSup", query = "SELECT v FROM Vertical v WHERE v.velocidadSup = :velocidadSup"),
    @NamedQuery(name = "Vertical.findByVelocidadFondo", query = "SELECT v FROM Vertical v WHERE v.velocidadFondo = :velocidadFondo"),
    @NamedQuery(name = "Vertical.findByVelocidad20", query = "SELECT v FROM Vertical v WHERE v.velocidad20 = :velocidad20"),
    @NamedQuery(name = "Vertical.findByVelocidad40", query = "SELECT v FROM Vertical v WHERE v.velocidad40 = :velocidad40"),
    @NamedQuery(name = "Vertical.findByVelocidad60", query = "SELECT v FROM Vertical v WHERE v.velocidad60 = :velocidad60"),
    @NamedQuery(name = "Vertical.findByVelocidad80", query = "SELECT v FROM Vertical v WHERE v.velocidad80 = :velocidad80"),
    @NamedQuery(name = "Vertical.findByProfundidad", query = "SELECT v FROM Vertical v WHERE v.profundidad = :profundidad"),
    @NamedQuery(name = "Vertical.findByDistOrilla", query = "SELECT v FROM Vertical v WHERE v.distOrilla = :distOrilla")})
public class Vertical implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDVERTICAL")
    private Short idvertical;
    @Column(name = "VUELTAS_20")
    private Integer vueltas20;
    @Column(name = "VUELTAS_40")
    private Integer vueltas40;
    @Column(name = "VUELTAS_60")
    private Integer vueltas60;
    @Column(name = "VUELTAS_80")
    private Integer vueltas80;
    @Column(name = "VUELTAS_SUP")
    private Integer vueltasSup;
    @Column(name = "VUELTAS_FONDO")
    private Integer vueltasFondo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VELOCIDAD")
    private Double velocidad;
    @Column(name = "VELOCIDAD_SUP")
    private Double velocidadSup;
    @Column(name = "VELOCIDAD_FONDO")
    private Double velocidadFondo;
    @Column(name = "VELOCIDAD_20")
    private Double velocidad20;
    @Column(name = "VELOCIDAD_40")
    private Double velocidad40;
    @Column(name = "VELOCIDAD_60")
    private Double velocidad60;
    @Column(name = "VELOCIDAD_80")
    private Double velocidad80;
    @Column(name = "PROFUNDIDAD")
    private Double profundidad;
    @Column(name = "DIST_ORILLA")
    private Double distOrilla;
    @JoinColumn(name = "IDAFORO", referencedColumnName = "IDAFORO")
    @ManyToOne(optional = false)
    private Aforo idaforo;

    public Vertical() {
    }

    public Vertical(Short idvertical) {
        this.idvertical = idvertical;
    }

    public Short getIdvertical() {
        return idvertical;
    }

    public void setIdvertical(Short idvertical) {
        this.idvertical = idvertical;
    }

    public Integer getVueltas20() {
        return vueltas20;
    }

    public void setVueltas20(Integer vueltas20) {
        this.vueltas20 = vueltas20;
    }

    public Integer getVueltas40() {
        return vueltas40;
    }

    public void setVueltas40(Integer vueltas40) {
        this.vueltas40 = vueltas40;
    }

    public Integer getVueltas60() {
        return vueltas60;
    }

    public void setVueltas60(Integer vueltas60) {
        this.vueltas60 = vueltas60;
    }

    public Integer getVueltas80() {
        return vueltas80;
    }

    public void setVueltas80(Integer vueltas80) {
        this.vueltas80 = vueltas80;
    }

    public Integer getVueltasSup() {
        return vueltasSup;
    }

    public void setVueltasSup(Integer vueltasSup) {
        this.vueltasSup = vueltasSup;
    }

    public Integer getVueltasFondo() {
        return vueltasFondo;
    }

    public void setVueltasFondo(Integer vueltasFondo) {
        this.vueltasFondo = vueltasFondo;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getVelocidadSup() {
        return velocidadSup;
    }

    public void setVelocidadSup(Double velocidadSup) {
        this.velocidadSup = velocidadSup;
    }

    public Double getVelocidadFondo() {
        return velocidadFondo;
    }

    public void setVelocidadFondo(Double velocidadFondo) {
        this.velocidadFondo = velocidadFondo;
    }

    public Double getVelocidad20() {
        return velocidad20;
    }

    public void setVelocidad20(Double velocidad20) {
        this.velocidad20 = velocidad20;
    }

    public Double getVelocidad40() {
        return velocidad40;
    }

    public void setVelocidad40(Double velocidad40) {
        this.velocidad40 = velocidad40;
    }

    public Double getVelocidad60() {
        return velocidad60;
    }

    public void setVelocidad60(Double velocidad60) {
        this.velocidad60 = velocidad60;
    }

    public Double getVelocidad80() {
        return velocidad80;
    }

    public void setVelocidad80(Double velocidad80) {
        this.velocidad80 = velocidad80;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getDistOrilla() {
        return distOrilla;
    }

    public void setDistOrilla(Double distOrilla) {
        this.distOrilla = distOrilla;
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
        hash += (idvertical != null ? idvertical.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vertical)) {
            return false;
        }
        Vertical other = (Vertical) object;
        if ((this.idvertical == null && other.idvertical != null) || (this.idvertical != null && !this.idvertical.equals(other.idvertical))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Vertical[ idvertical=" + idvertical + " ]";
    }
    
}
