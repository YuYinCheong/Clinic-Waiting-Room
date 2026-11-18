package clinic;

import java.io.IOException;

// Manually checks loading patient records from the sample CSV.
public class FileHandlerTest {

    public static void main(String[] args) {

        // Start with an empty directory.
        PatientDirectory directory = new PatientBST();

        // The path is relative to the program's working directory.
        // Use the supplied path, or the original sample file by default.
        String filePath = "data/patients.csv";

        if (args.length > 0) {
            filePath = args[0];
        }

        FileHandler loader = new FileHandler(filePath);

        try {
            int loadedCount = loader.loadPatients(directory);

            System.out.println("Patients loaded: " + loadedCount);
            System.out.println("All patients:");

            for (Patient patient : directory.getAllPatients()) {
                System.out.println(patient);
            }

        } catch (IOException e) {
            System.out.println("Could not load patients: "
                    + e.getMessage());
        }
    }
}