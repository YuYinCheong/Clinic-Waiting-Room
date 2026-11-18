package clinic;

import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;

// Reads patient records from a CSV file into a patient directory.
public class FileHandler {

    // Location of the CSV file to read.
    private String filePath;

    // Creates a file handler for the supplied file location.
    public FileHandler(String path) {
        this.filePath = path;
    }
    
	 // Converts one simple CSV row into a Patient object.
	 // Expected order: patientId,name,arrivalTime,status
	 private Patient parseRecord(String line) {
	
	     String[] fields = line.split(",", -1);
	
	     if (fields.length != 4) {
	         throw new IllegalArgumentException(
	                 "Each patient row must contain exactly four fields.");
	     }
	
	     int id = Integer.parseInt(fields[0].trim());
	     String name = fields[1].trim();
	     String arrivalText = fields[2].trim();
	     String status = fields[3].trim();
	
	     if (name.isEmpty()) {
	         throw new IllegalArgumentException(
	                 "Patient name cannot be empty.");
	     }
	
	     if (!status.equals("REGISTERED")
	             && !status.equals("WAITING")
	             && !status.equals("CALLED")) {
	         throw new IllegalArgumentException(
	                 "Status must be REGISTERED, WAITING, or CALLED.");
	     }
	
	     LocalDateTime arrivalTime = null;
	
	     if (!arrivalText.isEmpty()) {
	         arrivalTime = LocalDateTime.parse(arrivalText);
	     }
	
	     if ((status.equals("WAITING") || status.equals("CALLED"))
	             && arrivalTime == null) {
	         throw new IllegalArgumentException(
	                 "Waiting or called patients must have an arrival time.");
	     }
	
	     return new Patient(id, name, arrivalTime, status);
	 }
	 
	// Loads valid patient records and returns the number successfully added.
	// Reports invalid rows and duplicate IDs, then continues reading.
	// File-reading errors are passed to the calling code.
	public int loadPatients(PatientDirectory directory) throws IOException {

	    int loadedCount = 0;

	    try (BufferedReader reader = new BufferedReader(
	            new FileReader(filePath, StandardCharsets.UTF_8))) {

	        String header = reader.readLine();

	        if (!"patientId,name,arrivalTime,status".equals(header)) {
	            throw new IOException("Missing or incorrect CSV header.");
	        }

	        String line;
	        int lineNumber = 1;

	        while ((line = reader.readLine()) != null) {

	            lineNumber++;

	            if (line.trim().isEmpty()) {
	                continue;
	            }

	            try {
	                Patient patient = parseRecord(line);

	                if (directory.addPatient(patient)) {
	                    loadedCount++;
	                } else {
	                    System.out.println("Skipped line " + lineNumber
	                            + ": duplicate patient ID "
	                            + patient.getPatientId());
	                }

	            } catch (IllegalArgumentException | DateTimeParseException e) {
	                System.out.println("Skipped line " + lineNumber
	                        + ": " + e.getMessage());
	            }
	        }
	    }

	    return loadedCount;
	}
}