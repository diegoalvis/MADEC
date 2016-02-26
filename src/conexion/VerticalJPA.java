
package conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entidad.Vertical;
import java.util.ArrayList;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 *
 * @author ALVIS
 */

public class VerticalJPA {
    

    private Vertical vertical;
    private java.util.List<Vertical> verticales;
    private java.util.List<Vertical> vers;
    
    public boolean CrearVertical(Vertical vertical){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
      
        entityManager.getTransaction().begin();
        entityManager.persist(vertical);
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            return true;
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
            return false;
        }
    
    }
  
  public java.util.List<Vertical> ListarVerticales(short Id){
        verticales = new ArrayList<Vertical>();
        vers = new ArrayList<Vertical>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Vertical.findAll");
            verticales = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            for(int i=0; i<verticales.size(); i++)
            {
                if(verticales.get(i).getIdaforo().getIdaforo() == Id) 
                {
                    vers.add(verticales.get(i));
                }
            }
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
        }
       return vers;
         
    }
    
  public boolean EliminarVertical(Vertical vertical) {
  
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(vertical));
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            return true;
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
            return false;
        }
  }
}
     