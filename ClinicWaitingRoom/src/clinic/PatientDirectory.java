package clinic;

import java.util.List;

// Defines the operations available for storing and finding patients.
// Classes implementing this interface provide the actual behavior.
public interface PatientDirectory {

    // Adds a patient.
    // Returns true if added, or false if the ID already exists.
    boolean addPatient(Patient patient);

    // Finds a patient by ID.
    // Returns the patient, or null if the ID is not found.
    Patient findPatientById(int id);

    // Returns a list containing all patients in the directory.
    List<Patient> getAllPatients();
    
    // Returns all patients whose full name matches, ignoring capitalization.
    // Returns an empty list if no patients match.
    List<Patient> findPatientsByName(String name);

}