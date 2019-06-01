package com.optogo.repository.impl;

import com.optogo.model.Patient;
import com.optogo.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class PatientRepository implements Repository<Patient> {
    public static final String DEFAULT_UNIT = "default-unit";

    private EntityManager em;

    public PatientRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }

    @Override
    public Patient save(Patient patient) {
        em.getTransaction().begin();
        Patient savedPatient = em.merge(patient);
        em.getTransaction().commit();
        return savedPatient;
    }

    @Override
    public Patient update(Patient patient) {
        em.getTransaction().begin();
        Patient updatedPatient = em.merge(patient);
        em.getTransaction().commit();
        return updatedPatient;
    }

    @Override
    public List<Patient> findAll() {
        return (List<Patient>) em.createQuery("SELECT p FROM Patient p").getResultList();
    }

    @Override
    public Patient findById(Long id) {
        return (Patient) em.createQuery("SELECT p FROM Patient p WHERE p.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Patient patient) {
        em.getTransaction().begin();
        em.remove(patient);
        em.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Patient p = (Patient) em.createQuery("SELECT p FROM Patient p WHERE  p.id = :id").setParameter("id", id).getSingleResult();
        return p != null;
    }
}
