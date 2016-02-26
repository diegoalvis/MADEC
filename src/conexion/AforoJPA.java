
package conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import entidad.Aforo;
import entidad.Celda;
import java.awt.List;
import java.util.ArrayList;
import javax.persistence.RollbackException;
/**
 *
 * @author ALVIS
 */

public class AforoJPA {

    private Aforo aforo;
    private java.util.List<Aforo> aforos;
    
  public boolean CrearAforo(Aforo aforo){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
      
        entityManager.getTransaction().begin();
        entityManager.persist(aforo);
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
            //entityManager.getTransaction().begin();
            return true;
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
            return false;
        }
    
    }
  
  
  public boolean EliminarAforo(Aforo aforo) {
        Aforo af = new Aforo();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        af = entityManager.find(aforo.getClass(), aforo.getIdaforo());
        entityManager.remove(af);
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
            return false;
        }
  }
  
  public Aforo ConsultarAforo(int id){
        Short ID = new Short((short) id);
        aforo = new Aforo();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Aforo.findByIdaforo");
            query.setParameter("idaforo", ID);
            aforo = (Aforo) query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
        }
        
       return aforo;
 
    }

    
     public java.util.List<Aforo> ListarAforos(){
        aforos = new ArrayList<Aforo>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MadecV0.9.6PU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createNamedQuery("Aforo.findAll");
            aforos = query.getResultList();
            entityManager.getTransaction().commit();
            //entityManager.getTransaction().begin();
            
        } catch (RollbackException rex) {
            rex.printStackTrace();
        }
       return aforos;
         
    }

    
}


