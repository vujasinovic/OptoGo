package com.optogo;

import com.optogo.repository.impl.DiseaseRepository;
import com.optogo.repository.impl.MedicationRepository;
import com.optogo.repository.impl.ProcedureRepository;
import com.optogo.repository.impl.SymptomRepository;
import com.optogo.utils.InsertDiseases;
import com.optogo.utils.InsertMedications;
import com.optogo.utils.InsertProcedures;
import com.optogo.utils.InsertSymptoms;
import com.optogo.view.scene.MainScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "OptoGo";
    public static final String VERSION = "v1.0";
    public static final String APP_ICON = "eyes.png";


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(String.format("%s %s", TITLE, VERSION));
        primaryStage.getIcons().add(new Image(APP_ICON));
        primaryStage.setScene(MainScene.create());

        primaryStage.setHeight(600);
        primaryStage.setWidth(1000);

        primaryStage.show();
    }


    public static void main(String[] args) {
        insertData();
        launch(args);
    }

    private static void insertData() {
        DiseaseRepository diseaseRepository = new DiseaseRepository();
        MedicationRepository medicationRepository = new MedicationRepository();
        ProcedureRepository procedureRepository = new ProcedureRepository();
        SymptomRepository symptomRepository = new SymptomRepository();

        if (diseaseRepository.findAll().isEmpty()) {
            InsertDiseases.main(new String[0]);
        }

        if (medicationRepository.findAll().isEmpty()) {
            InsertMedications.main(new String[0]);
        }

        if (procedureRepository.findAll().isEmpty()) {
            InsertProcedures.main(new String[0]);
        }

        if(symptomRepository.findAll().isEmpty()) {
            InsertSymptoms.main(new String[0]);
        }

    }
}
