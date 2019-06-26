package com.optogo.repository.impl;

import com.optogo.model.Medication;
import com.optogo.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class MedicationRepository implements Repository<Long, Medication> {
    public static final String DEFAULT_UNIT = "default-unit";

    private EntityManager em;

    public MedicationRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }
    @Override
    public Medication save(Medication medication) {
        em.getTransaction().begin();
        Medication savedMedication = em.merge(medication);
        em.getTransaction().commit();
        return savedMedication;
    }

    @Override
    public Medication update(Medication medication) {
        em.getTransaction().begin();
        Medication updatedMedication = em.merge(medication);
        em.getTransaction().commit();
        return updatedMedication;
    }

    @Override
    public List<Medication> findAll() {
        return (List<Medication>) em.createQuery("SELECT m FROM Medication m").getResultList();
    }

    @Override
    public Medication findById(Long id) {
        return (Medication) em.createQuery("SELECT m FROM Medication m WHERE m.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Medication medication) {
        em.getTransaction().begin();
        em.remove(medication);
        em.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Medication m = (Medication) em.createQuery("SELECT m FROM Medication m WHERE m.id = :id").setParameter("id", id).getSingleResult();
        return m != null;
    }
}
