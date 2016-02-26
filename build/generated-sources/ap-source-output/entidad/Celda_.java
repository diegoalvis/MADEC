package entidad;

import entidad.Aforo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-06-27T15:52:07")
@StaticMetamodel(Celda.class)
public class Celda_ { 

    public static volatile SingularAttribute<Celda, Double> area;
    public static volatile SingularAttribute<Celda, Double> caudal;
    public static volatile SingularAttribute<Celda, Double> profundidad;
    public static volatile SingularAttribute<Celda, Aforo> idaforo;
    public static volatile SingularAttribute<Celda, Double> ancho;
    public static volatile SingularAttribute<Celda, Short> idcelda;
    public static volatile SingularAttribute<Celda, Double> velocidad;

}