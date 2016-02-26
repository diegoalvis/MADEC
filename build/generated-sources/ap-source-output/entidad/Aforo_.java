package entidad;

import entidad.Celda;
import entidad.Vertical;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-06-27T15:52:07")
@StaticMetamodel(Aforo.class)
public class Aforo_ { 

    public static volatile SingularAttribute<Aforo, Double> areaTotal;
    public static volatile SingularAttribute<Aforo, Integer> segundoFin;
    public static volatile SingularAttribute<Aforo, Double> longitudTotal;
    public static volatile SingularAttribute<Aforo, Integer> tiempoAforo;
    public static volatile SingularAttribute<Aforo, String> nombre;
    public static volatile SingularAttribute<Aforo, Double> caudalTotal;
    public static volatile SingularAttribute<Aforo, Double> miraInicial;
    public static volatile SingularAttribute<Aforo, Integer> mes;
    public static volatile SingularAttribute<Aforo, Integer> horaFin;
    public static volatile SingularAttribute<Aforo, Integer> tipoAforo;
    public static volatile SingularAttribute<Aforo, Integer> anho;
    public static volatile SingularAttribute<Aforo, Integer> minutoFin;
    public static volatile SingularAttribute<Aforo, Integer> minutoInicio;
    public static volatile SingularAttribute<Aforo, Integer> horaInicio;
    public static volatile SingularAttribute<Aforo, Integer> dia;
    public static volatile CollectionAttribute<Aforo, Vertical> verticalCollection;
    public static volatile SingularAttribute<Aforo, Integer> segundoInicio;
    public static volatile SingularAttribute<Aforo, Double> miraFinal;
    public static volatile SingularAttribute<Aforo, Integer> helice;
    public static volatile CollectionAttribute<Aforo, Celda> celdaCollection;
    public static volatile SingularAttribute<Aforo, Integer> numeroVerticales;
    public static volatile SingularAttribute<Aforo, Short> idaforo;
    public static volatile SingularAttribute<Aforo, Double> profundidadMedia;
    public static volatile SingularAttribute<Aforo, Integer> numeroCeldas;
    public static volatile SingularAttribute<Aforo, Double> ubicacionVertical1;
    public static volatile SingularAttribute<Aforo, Double> velocidadMedia;

}