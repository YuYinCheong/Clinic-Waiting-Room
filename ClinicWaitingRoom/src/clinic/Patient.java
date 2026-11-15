package clinic;

import java.time.LocalDateTime;

// Represents one patient record in the clinic.
// Stores the patient's ID, name, arrival time, and current status.
public class Patient {
	
	private int patientId;
    private String name;
    // Null until the patient checks in, unless supplied during CSV loading.
    private LocalDateTime arrivalTime;
    // Status values used by the team: REGISTERED, WAITING, CALLED.
    private String status;
    
    // Creates a registered patient who has not checked in yet.
    public Patient(int patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.arrivalTime = null;
        this.status = "REGISTERED";
    }
    
    // Creates a patient using existing record information, such as values loaded from a CSV file.
    public Patient(int patientId, String name, LocalDateTime arrivalTime, String status) {
    	this.patientId = patientId;
    	this.name = name;
    	this.arrivalTime = arrivalTime;
    	this.status = status;
    }
    
    // Returns the patient's ID for identification and searching.
    public int getPatientId() {
        return patientId;
    }

    // Returns the patient's name.
    public String getName() {
        return name;
    }

    // Returns the arrival time, or null if no arrival is recorded.
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    // Returns the patient's current status.
    public String getStatus() {
        return status;
    }
    
    // Updates the patient's name.
    public void setName(String name) {
        this.name = name;
    }

    // Records arrival and changes the status to WAITING.
    // The calling code must prevent duplicate check-ins and add the patient to the waiting queue separately.
    public void checkIn(LocalDateTime time) {
        this.arrivalTime = time;
        this.status = "WAITING";
    }

    // Changes the status to CALLED.
    // The calling code handles removal from the waiting queue.
    public void markCalled() {
        this.status = "CALLED";
    }

    // Returns a readable patient record for console display.
    @Override
    public String toString() {
        return patientId + " | " + name + " | " + arrivalTime + " | " + status;
    }
    
}

