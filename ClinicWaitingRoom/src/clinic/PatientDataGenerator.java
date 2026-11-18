package clinic;

import java.time.LocalDateTime;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// Generates fictional patient records for testing the clinic application.
public class PatientDataGenerator {

    public static void main(String[] args) {

        // Number of patient records to generate.
        int recordCount = 555;

        // Keep generated data separate from our small test files.
        String filePath = "data/patients_555.csv";

        // Each row contains sample first names and surnames.
        // These are fictional name combinations, not real patient records.
        String[][] firstNames = {
            {"Amara", "Chidi", "Ada", "Emeka", "Ngozi"},
            {"Sofia", "Mateo", "Valentina", "Diego", "Camila"},
            {"Aiko", "Haruto", "Hana", "Ren", "Yui"},
            {"Leila", "Omar", "Nadia", "Karim", "Salma"},
            {"Anika", "Arjun", "Priya", "Rohan", "Meera"},
            {"Oliver", "Charlotte", "James", "Amelia", "William"},
            {"Isabella", "Luca", "Giulia", "Matteo", "Alessia"},
            {"Lin", "Wei", "Mei", "Jun", "Yan"},
            {"Amina", "Moussa", "Fatou", "Ibrahima", "Mariama"},
            {"Freja", "Erik", "Astrid", "Lars", "Ingrid"},
            {"Helena", "Rafael", "Beatriz", "Tiago", "Mariana"},
            {"Zofia", "Jakub", "Lena", "Piotr", "Alicja"}
        };

        // Match each surname row with the first-name row above it.
        String[][] lastNames = {
            {"Okafor", "Okeke", "Eze", "Nwosu", "Obi"},
            {"Martinez", "Garcia", "Torres", "Rivera", "Lopez"},
            {"Tanaka", "Sato", "Suzuki", "Takahashi", "Watanabe"},
            {"Hassan", "Haddad", "Mansour", "Khalil", "Nasser"},
            {"Patel", "Sharma", "Rao", "Shah", "Mehta"},
            {"Smith", "Johnson", "Brown", "Wilson", "Taylor"},
            {"Rossi", "Russo", "Ferrari", "Romano", "Ricci"},
            {"Chen", "Wang", "Li", "Zhang", "Liu"},
            {"Diallo", "Sow", "Ba", "Ndiaye", "Diop"},
            {"Andersen", "Berg", "Dahl", "Lund", "Lind"},
            {"Silva", "Santos", "Oliveira", "Costa", "Pereira"},
            {"Nowak", "Wojcik", "Zajac", "Mazur", "Krawczyk"}
        };

        System.out.println("Records to generate: " + recordCount);
        System.out.println("Output file: " + filePath);
        System.out.println("Name groups available: " + firstNames.length);
        
        // A fixed seed makes the generated data repeatable.
        Random random = new Random(42);

        // Fixed starting time for this fictional dataset.
        LocalDateTime startTime = LocalDateTime.of(2026, 11, 16, 8, 0);

        System.out.println("Preview of the first five records:");
        
     // Create the data folder if it does not already exist.
        try {
            Files.createDirectories(Path.of("data"));

            // Open the output file using UTF-8.
            // Running this generator again replaces this generated file.
            try (BufferedWriter writer = Files.newBufferedWriter(
                    Path.of(filePath), StandardCharsets.UTF_8)) {

                writer.write("patientId,name,arrivalTime,status");
                writer.newLine();

	        for (int i = 0; i < recordCount; i++) {
	
	            // Unique IDs from 1001 through 1555.
	            int id = 1001 + i;
	
	            // Cycle through the name groups for a varied dataset.
	            int group = i % firstNames.length;
	
	            int firstIndex = random.nextInt(firstNames[group].length);
	            int lastIndex = random.nextInt(lastNames[group].length);
	
	            String name = firstNames[group][firstIndex] + " "
	                    + lastNames[group][lastIndex];
	
	            String status;
	            LocalDateTime arrivalTime = null;
	
	            // Vary status across rounds of name groups.
	            if ((i / firstNames.length) % 3 == 0) {
	                status = "REGISTERED";
	            } else if ((i / firstNames.length) % 3 == 1) {
	                status = "WAITING";
	            } else {
	                status = "CALLED";
	            }
	
	            // Only checked-in patients have an arrival time.
	            if (!status.equals("REGISTERED")) {
	                arrivalTime = startTime.plusMinutes(random.nextInt(480));
	            }
	
	            Patient patient = new Patient(id, name, arrivalTime, status);
	            
	            // Write an empty CSV field when no arrival time is recorded.
	            String arrivalText = "";

	            if (arrivalTime != null) {
	                arrivalText = arrivalTime.toString();
	            }

	            writer.write(id + "," + name + "," + arrivalText + "," + status);
	            writer.newLine();
	
	            // Show a small preview instead of printing all 555 records.
	            if (i < 5) {
	                System.out.println(patient);
	            }
	        }
            } // Closes the writer after all rows have been written.

            System.out.println("Successfully wrote " + recordCount
                    + " records to " + filePath);

        } catch (IOException e) {
            System.out.println("Could not write the CSV: " + e.getMessage());
        }
    }
}