package fr.utcapitole.demo.jpa;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class DbUpdates {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void reset() {
        String query = "alter sequence hibernate_sequence restart with 1";
        entityManager.createNativeQuery(query).executeUpdate();
    }
}
