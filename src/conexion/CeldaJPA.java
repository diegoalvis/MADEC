
package conexion;

import entidad.Celda;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 *
 * @author ALVIS
 */
public class CeldaJPA {

    private Celda celda;
    private java.util.List<Celda> celdas;
    private java.util.List<Celda> cels;
    
    public boolean CrearCelda(Celda celda){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
      
        entityManager.getTransaction().begin();
        entityManager.persist(celda);
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            return true;
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
            return false;
        }
    
    }
  
  
 
    public java.util.List<Celda> ListarCeldas(short Id){
        celdas = new ArrayList<Celda>();
        cels = new ArrayList<Celda>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Celda.findAll");
            celdas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            for(int i=0; i<celdas.size(); i++)
            {
                if(celdas.get(i).getIdaforo().getIdaforo() == Id) 
                {
                    cels.add(celdas.get(i));
                }
            }
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
        }
       return cels;
         
    }
  
  public boolean EliminarCelda(Celda celda) {
  
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(celda));
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
     