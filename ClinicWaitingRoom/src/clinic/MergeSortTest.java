
package clinic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Tests Merge Sort and demonstrates the clinic's data structures.
public class MergeSortTest {

    public static void main(String[] args) 
    {

        // Default CSV path can be overridden with a command-line argument.
        String filePath = "data/patients.csv";

        if (args.length > 0) 
        {
            filePath = args[0];
        }//end if

        // Test 1: Verify Merge Sort using known values.
        testMergeSort();

        // Test 2: Load the real CSV and test the project structures.
        testWithCSV(filePath);
    }//end main

    // Tests sorting with deliberately unsorted patients.
    private static void testMergeSort() 
    {

        System.out.println("\n===== TEST 1: MERGE SORT =====");

        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient(1004, "John Smith", LocalDateTime.of(2026, 11, 17, 9, 30), "WAITING"));

        patients.add(new Patient(1003, "Mary Johnson", LocalDateTime.of(2026, 11, 17, 9, 0), "WAITING"));

        patients.add(new Patient(1002, "David Lee",LocalDateTime.of(2026, 11, 17, 9, 0), "WAITING"));

        patients.add(new Patient(1001, "Sarah Wilson", LocalDateTime.of(2026, 11, 17, 8, 45), "WAITING"));

        System.out.println("\nBefore Merge Sort:");

        printPatients(patients, patients.size());

        // Run the team's sorting algorithm.
        MergeSort sorter = new MergeSort();

        List<Patient> sorted = sorter.sortByArrivalTime(patients);

        System.out.println("\nAfter Merge Sort:");

        printPatients(sorted, sorted.size());

        // Expected order based on arrival time, then ID.
        int[] expected = {1001, 1002, 1003, 1004};

        for (int i = 0; i < expected.length; i++) 
        {

            if (sorted.get(i).getPatientId() != expected[i]) 
            {
                throw new IllegalStateException("FAIL: Incorrect Merge Sort order.");
            }//end if
        }//end for

        // The original input list should remain unchanged.
        if (patients.get(0).getPatientId() != 1004) 
        {
            throw new IllegalStateException("FAIL: Merge Sort modified the original list.");
        }//end if

        // Test empty-list handling.
        if (!sorter.sortByArrivalTime(new ArrayList<Patient>()).isEmpty()) 
        {
            throw new IllegalStateException("FAIL: Empty list test.");
        }

        System.out.println("\nPASS: Merge Sort test successful.");
    }//end testmergesort

    // Tests Merge Sort using the actual clinic CSV dataset.
    private static void testWithCSV(String filePath) 
    {

        System.out.println("\n===== TEST 2: CSV DATASET =====");

        // Use the team's Red-Black Tree-backed directory.
        PatientDirectory directory = new PatientBST();

        FileHandler loader = new FileHandler(filePath);

        try {
            // Load patients into the directory.
            int loaded = loader.loadPatients(directory);

            System.out.println("Patients loaded: " + loaded);

            if (loaded == 0) 
            {
            	throw new IllegalStateException("FAIL: No patients were loaded.");
            }//end if

            // Filter existing WAITING patients.
            List<Patient> waiting = new ArrayList<>();

            for (Patient patient : directory.getAllPatients()) 
            {

                if (patient.getStatus().equals("WAITING")) 
                {
                    waiting.add(patient);
                }//end if
            }//end for

            System.out.println("WAITING patients found: " + waiting.size());

            // TreeMap returns records by ID, not arrival time.
            System.out.println("\nBefore Merge Sort (patient ID order):");

            printPatients(waiting, 10);

            // Run the team's Merge Sort algorithm.
            MergeSort sorter = new MergeSort();

            List<Patient> sorted = sorter.sortByArrivalTime(waiting);

            System.out.println("\nAfter Merge Sort (arrival-time order):");

            printPatients(sorted, 10);

            // Verify every adjacent pair is correctly ordered.
            if (!isSorted(sorted)) 
            {
                throw new IllegalStateException("FAIL: Arrival times are not sorted.");
            }//end if

            System.out.println("\nPASS: CSV patients sorted correctly.");
            // Demonstrate the team's FIFO queue.
            PatientQueue queue = new PatientQueue();

            for (Patient patient : sorted) 
            {
                queue.enqueue(patient);
            }//end for
            System.out.println("\n===== TEST 3: FIFO QUEUE =====");
            System.out.println("Patients enqueued: " + queue.getWaitingPatients().size());

            // Dequeue the earliest waiting patient.
            Patient nextPatient = queue.dequeue();

            if (nextPatient != null) 
            {
               nextPatient.markCalled();
               System.out.println("Dequeued patient: " + nextPatient.getPatientId());
               System.out.println("Updated status: " + nextPatient.getStatus());

                // Verify the patient remains in the BST.
                Patient found = directory.findPatientById(nextPatient.getPatientId());

                if (found != nextPatient || !found.getStatus().equals("CALLED")) 
                {
                    throw new IllegalStateException("FAIL: BST record was not updated.");
                }//end if
                
                System.out.println("PASS: Patient remains in the BST.");
            }//end if
            System.out.println("Remaining in queue: " + queue.getWaitingPatients().size());
            System.out.println("\nALL TESTS COMPLETED SUCCESSFULLY.");

        }//end try 
        catch (IOException e) 
        {
            System.err.println("Could not load CSV: " + e.getMessage());
            System.err.println("Check file location: " + filePath);
            System.exit(1);
        }//end catch
        
    }//endtestcsv
    
    
    // Verify chronological order, using patient ID for ties.
    private static boolean isSorted(List<Patient> patients) 
    {

        for (int i = 1; i < patients.size(); i++) 
        {
            Patient previous = patients.get(i - 1);
            Patient current = patients.get(i);
            int comparison = previous.getArrivalTime().compareTo(current.getArrivalTime());

            if (comparison > 0) 
            {
                return false;
            }//end if

            if (comparison == 0 && previous.getPatientId() > current.getPatientId()) {
                return false;
            }//end if
        }//end for

        return true;
    }//end issorted

    // Display a small sample of patients.
    private static void printPatients(List<Patient> patients, int limit) 
    {
        int count = Math.min(limit, patients.size());

        for (int i = 0; i < count; i++) 
        {
            System.out.println(patients.get(i));
        }//end for
    }//end pritnpatinets
}//end test
