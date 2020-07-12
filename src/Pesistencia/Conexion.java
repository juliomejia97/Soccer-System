package Pesistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DELL
 */
public class Conexion {
    private  final EntityManagerFactory emf;
    
    public Conexion()
    {
        this.emf=Persistence.createEntityManagerFactory("Entrega3PU");
    }
    public  EntityManagerFactory getEmf()
    {
        return emf;
    }
    
    public  EntityManager getEm(){
        
            return emf.createEntityManager();
            
        
    }
    
    public  void close()
    {
        emf.close();
    }
}
