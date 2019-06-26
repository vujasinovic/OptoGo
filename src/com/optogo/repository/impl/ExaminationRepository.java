package com.optogo.repository.impl;

import com.optogo.model.Examination;
import com.optogo.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ExaminationRepository implements Repository<Long, Examination> {
    public static final String DEFAULT_UNIT = "default-unit";
    private EntityManager em;

    public ExaminationRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }

    @Override
    public Examination save(Examination entity) {
        em.getTransaction().begin();
        Examination saved = em.merge(entity);
        em.getTransaction().commit();
        return saved;
    }

    @Override
    public Examination update(Examination entity) {
        em.getTransaction().begin();
        Examination updateDisease = em.merge(entity);
        em.getTransaction().commit();
        return updateDisease;
    }

    @Override
    public List<Examination> findAll() {
        return (List<Examination>) em.createQuery("SELECT d FROM Disease d").getResultList();
    }

    @Override
    public Examination findById(Long id) {
        return (Examination) em.createQuery("SELECT d FROM Disease d WHERE d.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Examination entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

    }

    @Override
    public boolean existsById(Long id) {
        Examination d = (Examination) em.createQuery("SELECT d FROM Disease d WHERE d.id = :id")
                .setParameter("id", id).getSingleResult();
        return d != null;
    }
}
