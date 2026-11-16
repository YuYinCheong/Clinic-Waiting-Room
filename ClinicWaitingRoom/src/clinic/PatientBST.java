package clinic;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

// Stores patient records by ID using Java's built-in TreeMap.
// TreeMap manages the tree structure internally.
public class PatientBST implements PatientDirectory {

    // Each unique patient ID is associated with one Patient object.
    private TreeMap<Integer, Patient> patients;

    // Creates an empty patient directory.
    public PatientBST() {
        patients = new TreeMap<>();
    }
    
    // Adds a patient if their ID is not already in the directory.
    // Returns true if added, or false if the ID already exists.
    @Override
    public boolean addPatient(Patient patient) {

        int id = patient.getPatientId();

        if (patients.containsKey(id)) {
            return false;
        }

        patients.put(id, patient);
        return true;
    }
    
    // Finds a patient using their ID.
    // Returns the patient, or null if the ID is not found.
    @Override
    public Patient findPatientById(int id) {
        return patients.get(id);
    }
    
    // Returns a new list containing all patients, ordered by patient ID.
    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }
    
    // Searches for a full name, ignoring capitalization and outer spaces.
    // Returns all matching patients, or an empty list if none match.
    @Override
    public List<Patient> findPatientsByName(String name) {

	     List<Patient> matches = new ArrayList<>();
	
	     for (Patient patient : patients.values()) {
	
	         if (patient.getName().equalsIgnoreCase(name.trim())) {
	             matches.add(patient);
	         }
	     }
	
	     return matches;
    }
}