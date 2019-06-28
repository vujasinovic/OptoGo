package com.optogo.repository.impl;

import com.optogo.model.Procedure;
import com.optogo.repository.Repository;
import com.optogo.utils.enums.ProcedureName;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ProcedureRepository implements Repository<Long, Procedure> {
    public static final String DEFAULT_UNIT = "default-unit";

    private EntityManager em;

    public ProcedureRepository() {
        em = Persistence.createEntityManagerFactory(DEFAULT_UNIT).createEntityManager();
    }

    public List<Procedure> findAllByName(List<String> names) {
        List<Procedure> procedures = new ArrayList<>();

        for (String name : names) {
            procedures.add(findByName(ProcedureName.valueOf(name.toUpperCase())));
        }

        return procedures;
    }

    public Procedure findByName(ProcedureName name) {
        return (Procedure) em.createQuery("SELECT s FROM Procedure s WHERE s.title = :title").setParameter("title", name).getSingleResult();
    }

    @Override
    public Procedure save(Procedure procedure) {
        em.getTransaction().begin();
        Procedure savedProcedure = em.merge(procedure);
        em.getTransaction().commit();
        return savedProcedure;
    }

    @Override
    public Procedure update(Procedure procedure) {
        em.getTransaction().begin();
        Procedure updatedProcedure = em.merge(procedure);
        em.getTransaction().commit();
        return updatedProcedure;
    }

    @Override
    public List<Procedure> findAll() {
        return (List<Procedure>) em.createQuery("SELECT p FROM Procedure p").getResultList();
    }

    @Override
    public Procedure findById(Long id) {
        return (Procedure) em.createQuery("SELECT p FROM Procedure p WHERE id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    public void delete(Procedure procedure) {
        em.getTransaction().begin();
        em.remove(procedure);
        em.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Procedure p = (Procedure) em.createQuery("SELECT p FROM Procedure p WHERE id = :id").setParameter("id", id).getSingleResult();
        return p != null;
    }
}
