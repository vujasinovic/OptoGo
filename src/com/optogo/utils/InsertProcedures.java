package com.optogo.utils;


import com.optogo.model.Procedure;
import com.optogo.repository.impl.ProcedureRepository;
import com.optogo.utils.enums.ProcedureName;

public class InsertProcedures {

    public static void main(String[] args) {
        ProcedureName[] names = ProcedureName.class.getEnumConstants();
        ProcedureRepository repository = new ProcedureRepository();
        for (int i = 0; i < names.length; i++) {
            Procedure symptom = new Procedure();
            symptom.setTitle(names[i]);
            repository.save(symptom);
        }
    }

}
