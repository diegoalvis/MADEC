/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ALVIS
 */
@Entity
@Table(name = "AFORO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aforo.findAll", query = "SELECT a FROM Aforo a"),
    @NamedQuery(name = "Aforo.findByIdaforo", query = "SELECT a FROM Aforo a WHERE a.idaforo = :idaforo"),
    @NamedQuery(name = "Aforo.findByNombre", query = "SELECT a FROM Aforo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Aforo.findByTipoAforo", query = "SELECT a FROM Aforo a WHERE a.tipoAforo = :tipoAforo"),
    @NamedQuery(name = "Aforo.findByNumeroCeldas", query = "SELECT a FROM Aforo a WHERE a.numeroCeldas = :numeroCeldas"),
    @NamedQuery(name = "Aforo.findByHelice", query = "SELECT a FROM Aforo a WHERE a.helice = :helice"),
    @NamedQuery(name = "Aforo.findByTiempoAforo", query = "SELECT a FROM Aforo a WHERE a.tiempoAforo = :tiempoAforo"),
    @NamedQuery(name = "Aforo.findByHoraInicio", query = "SELECT a FROM Aforo a WHERE a.horaInicio = :horaInicio"),
    @NamedQuery(name = "Aforo.findByMinutoInicio", query = "SELECT a FROM Aforo a WHERE a.minutoInicio = :minutoInicio"),
    @NamedQuery(name = "Aforo.findBySegundoInicio", query = "SELECT a FROM Aforo a WHERE a.segundoInicio = :segundoInicio"),
    @NamedQuery(name = "Aforo.findByHoraFin", query = "SELECT a FROM Aforo a WHERE a.horaFin = :horaFin"),
    @NamedQuery(name = "Aforo.findByMinutoFin", query = "SELECT a FROM Aforo a WHERE a.minutoFin = :minutoFin"),
    @NamedQuery(name = "Aforo.findBySegundoFin", query = "SELECT a FROM Aforo a WHERE a.segundoFin = :segundoFin"),
    @NamedQuery(name = "Aforo.findByDia", query = "SELECT a FROM Aforo a WHERE a.dia = :dia"),
    @NamedQuery(name = "Aforo.findByMes", query = "SELECT a FROM Aforo a WHERE a.mes = :mes"),
    @NamedQuery(name = "Aforo.findByAnho", query = "SELECT a FROM Aforo a WHERE a.anho = :anho"),
    @NamedQuery(name = "Aforo.findByLongitudTotal", query = "SELECT a FROM Aforo a WHERE a.longitudTotal = :longitudTotal"),
    @NamedQuery(name = "Aforo.findByProfundidadMedia", query = "SELECT a FROM Aforo a WHERE a.profundidadMedia = :profundidadMedia"),
    @NamedQuery(name = "Aforo.findByVelocidadMedia", query = "SELECT a FROM Aforo a WHERE a.velocidadMedia = :velocidadMedia"),
    @NamedQuery(name = "Aforo.findByAreaTotal", query = "SELECT a FROM Aforo a WHERE a.areaTotal = :areaTotal"),
    @NamedQuery(name = "Aforo.findByCaudalTotal", query = "SELECT a FROM Aforo a WHERE a.caudalTotal = :caudalTotal"),
    @NamedQuery(name = "Aforo.findByMiraInicial", query = "SELECT a FROM Aforo a WHERE a.miraInicial = :miraInicial"),
    @NamedQuery(name = "Aforo.findByMiraFinal", query = "SELECT a FROM Aforo a WHERE a.miraFinal = :miraFinal"),
    @NamedQuery(name = "Aforo.findByUbicacionVertical1", query = "SELECT a FROM Aforo a WHERE a.ubicacionVertical1 = :ubicacionVertical1"),
    @NamedQuery(name = "Aforo.findByNumeroVerticales", query = "SELECT a FROM Aforo a WHERE a.numeroVerticales = :numeroVerticales")})
public class Aforo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAFORO")
    private Short idaforo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIPO_AFORO")
    private Integer tipoAforo;
    @Column(name = "NUMERO_CELDAS")
    private Integer numeroCeldas;
    @Column(name = "HELICE")
    private Integer helice;
    @Column(name = "TIEMPO_AFORO")
    private Integer tiempoAforo;
    @Column(name = "HORA_INICIO")
    private Integer horaInicio;
    @Column(name = "MINUTO_INICIO")
    private Integer minutoInicio;
    @Column(name = "SEGUNDO_INICIO")
    private Integer segundoInicio;
    @Column(name = "HORA_FIN")
    private Integer horaFin;
    @Column(name = "MINUTO_FIN")
    private Integer minutoFin;
    @Column(name = "SEGUNDO_FIN")
    private Integer segundoFin;
    @Column(name = "DIA")
    private Integer dia;
    @Column(name = "MES")
    private Integer mes;
    @Column(name = "ANHO")
    private Integer anho;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LONGITUD_TOTAL")
    private Double longitudTotal;
    @Column(name = "PROFUNDIDAD_MEDIA")
    private Double profundidadMedia;
    @Column(name = "VELOCIDAD_MEDIA")
    private Double velocidadMedia;
    @Column(name = "AREA_TOTAL")
    private Double areaTotal;
    @Column(name = "CAUDAL_TOTAL")
    private Double caudalTotal;
    @Column(name = "MIRA_INICIAL")
    private Double miraInicial;
    @Column(name = "MIRA_FINAL")
    private Double miraFinal;
    @Column(name = "UBICACION_VERTICAL1")
    private Double ubicacionVertical1;
    @Column(name = "NUMERO_VERTICALES")
    private Integer numeroVerticales;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idaforo")
    private Collection<Vertical> verticalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idaforo")
    private Collection<Celda> celdaCollection;

    public Aforo() {
    }

    public Aforo(Short idaforo) {
        this.idaforo = idaforo;
    }

    public Short getIdaforo() {
        return idaforo;
    }

    public void setIdaforo(Short idaforo) {
        this.idaforo = idaforo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipoAforo() {
        return tipoAforo;
    }

    public void setTipoAforo(Integer tipoAforo) {
        this.tipoAforo = tipoAforo;
    }

    public Integer getNumeroCeldas() {
        return numeroCeldas;
    }

    public void setNumeroCeldas(Integer numeroCeldas) {
        this.numeroCeldas = numeroCeldas;
    }

    public Integer getHelice() {
        return helice;
    }

    public void setHelice(Integer helice) {
        this.helice = helice;
    }

    public Integer getTiempoAforo() {
        return tiempoAforo;
    }

    public void setTiempoAforo(Integer tiempoAforo) {
        this.tiempoAforo = tiempoAforo;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getMinutoInicio() {
        return minutoInicio;
    }

    public void setMinutoInicio(Integer minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    public Integer getSegundoInicio() {
        return segundoInicio;
    }

    public void setSegundoInicio(Integer segundoInicio) {
        this.segundoInicio = segundoInicio;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getMinutoFin() {
        return minutoFin;
    }

    public void setMinutoFin(Integer minutoFin) {
        this.minutoFin = minutoFin;
    }

    public Integer getSegundoFin() {
        return segundoFin;
    }

    public void setSegundoFin(Integer segundoFin) {
        this.segundoFin = segundoFin;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Double getLongitudTotal() {
        return longitudTotal;
    }

    public void setLongitudTotal(Double longitudTotal) {
        this.longitudTotal = longitudTotal;
    }

    public Double getProfundidadMedia() {
        return profundidadMedia;
    }

    public void setProfundidadMedia(Double profundidadMedia) {
        this.profundidadMedia = profundidadMedia;
    }

    public Double getVelocidadMedia() {
        return velocidadMedia;
    }

    public void setVelocidadMedia(Double velocidadMedia) {
        this.velocidadMedia = velocidadMedia;
    }

    public Double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Double getCaudalTotal() {
        return caudalTotal;
    }

    public void setCaudalTotal(Double caudalTotal) {
        this.caudalTotal = caudalTotal;
    }

    public Double getMiraInicial() {
        return miraInicial;
    }

    public void setMiraInicial(Double miraInicial) {
        this.miraInicial = miraInicial;
    }

    public Double getMiraFinal() {
        return miraFinal;
    }

    public void setMiraFinal(Double miraFinal) {
        this.miraFinal = miraFinal;
    }

    public Double getUbicacionVertical1() {
        return ubicacionVertical1;
    }

    public void setUbicacionVertical1(Double ubicacionVertical1) {
        this.ubicacionVertical1 = ubicacionVertical1;
    }

    public Integer getNumeroVerticales() {
        return numeroVerticales;
    }

    public void setNumeroVerticales(Integer numeroVerticales) {
        this.numeroVerticales = numeroVerticales;
    }

    @XmlTransient
    public Collection<Vertical> getVerticalCollection() {
        return verticalCollection;
    }

    public void setVerticalCollection(Collection<Vertical> verticalCollection) {
        this.verticalCollection = verticalCollection;
    }

    @XmlTransient
    public Collection<Celda> getCeldaCollection() {
        return celdaCollection;
    }

    public void setCeldaCollection(Collection<Celda> celdaCollection) {
        this.celdaCollection = celdaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaforo != null ? idaforo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aforo)) {
            return false;
        }
        Aforo other = (Aforo) object;
        if ((this.idaforo == null && other.idaforo != null) || (this.idaforo != null && !this.idaforo.equals(other.idaforo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Aforo[ idaforo=" + idaforo + " ]";
    }
    
}
