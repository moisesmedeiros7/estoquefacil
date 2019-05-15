package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("estoquefacil2PU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}