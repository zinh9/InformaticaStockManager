package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConectionDB {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("InformaticaStockManager");
	
	public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    
    public static EntityManagerFactory getEmf() {
        return emf;
    }
    
    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
