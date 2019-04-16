patient_symptom(aleksandar, pain_in_eye).
patient_symptom(aleksandar, double_vision).
patient_symptom(aleksandar, diminished_vision).

patient_symptom(dragan, swollen_eye).
patient_symptom(dragan, eyelid_swelling).
patient_symptom(dragan, pain_in_eye).

symptom_of_disease([], _D) :- !.
symptom_of_disease([H|T], D) :- symptom_disease(H, D, _P), symptom_of_disease(T, D).
diseases(P, D) :- findall(X, patient_symptom(P, X), Symptoms), symptom_of_disease(Symptoms, D).

