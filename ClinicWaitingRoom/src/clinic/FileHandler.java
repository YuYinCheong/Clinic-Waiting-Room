package clinic;
import java.time.LocalDateTime;

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
}