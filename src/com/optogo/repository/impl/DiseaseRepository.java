package com.optogo.repository.impl;

import com.optogo.model.Disease;
import com.optogo.repository.Repository;
import com.optogo.utils.enums.DiseaseName;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class DiseaseRepository implements Repository<Long, Disease> {
    public static final String DEFAULT_UNIT = "default-unit";

    private EntityManager em;

    public DiseaseRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }

    public Disease findByName(DiseaseName name) {
        return (Disease) em.createQuery("SELECT d FROM Disease d WHERE d.name = :name").setParameter("name", name).getSingleResult();
    }

    @Override
    public Disease save(Disease disease) {
        em.getTransaction().begin();
        Disease savedDisease = em.merge(disease);
        em.getTransaction().commit();
        return savedDisease;
    }

    @Override
    public Disease update(Disease disease) {
        em.getTransaction().begin();
        Disease updateDisease = em.merge(disease);
        em.getTransaction().commit();
        return updateDisease;
    }

    @Override
    public List<Disease> findAll() {
        return (List<Disease>) em.createQuery("SELECT d FROM Disease d").getResultList();
    }

    @Override
    public Disease findById(Long id) {
        return (Disease) em.createQuery("SELECT d FROM Disease d WHERE d.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Disease disease) {
        em.getTransaction().begin();
        em.remove(disease);
        em.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Disease d = (Disease) em.createQuery("SELECT d FROM Disease d WHERE d.id = :id").setParameter("id", id).getSingleResult();
        return d != null;
    }
}
