package com.optogo.repository.impl;

import com.optogo.model.MedicalPrescription;
import com.optogo.repository.Repository;

import java.util.List;

public class MedicalPrescriptionRepository implements Repository<Long, MedicalPrescription> {
    @Override
    public MedicalPrescription save(MedicalPrescription entity) {
        return null;
    }

    @Override
    public MedicalPrescription update(MedicalPrescription entity) {
        return null;
    }

    @Override
    public List<MedicalPrescription> findAll() {
        return null;
    }

    @Override
    public MedicalPrescription findById(Long id) {
        return null;
    }

    @Override
    public void delete(MedicalPrescription entity) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
