package com.optogo.repository.impl;

import com.optogo.model.Symptom;
import com.optogo.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class SymptomRepository implements Repository<Symptom> {
    public static final String DEFAULT_UNIT = "default-unit";

    private EntityManager em;

    public SymptomRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }

    @Override
    public Symptom save(Symptom symptom) {
        em.getTransaction().begin();
        Symptom savedSymptom = em.merge(symptom);
        em.getTransaction().commit();
        return savedSymptom;
    }

    @Override
    public Symptom update(Symptom symptom) {
        em.getTransaction().begin();
        Symptom updatedSymptom = em.merge(symptom);
        em.getTransaction().commit();
        return updatedSymptom;
    }

    @Override
    public List<Symptom> findAll() {
        return (List<Symptom>) em.createQuery("SELECT s FROM Symptom s").getResultList();
    }

    @Override
    public Symptom findById(Long id) {
        return (Symptom) em.createQuery("SELECT s FROM Symptom s WHERE s.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Symptom symptom) {
        em.getTransaction().begin();
        em.remove(symptom);
        em.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Symptom s = (Symptom) em.createQuery("SELECT s FROM Symptom s WHERE s.id = :id").setParameter("id", id).getSingleResult();
        return s != null;
    }
}
