package clinic;

import java.time.LocalDateTime;

public class PatientTest {

    public static void main(String[] args) {

    	// Create a patient who has not checked in.
        // Expected: 1001 | John Doe | null | REGISTERED
        Patient patient = new Patient(1001, "John Doe");
        System.out.println(patient);

        // Use a fixed arrival time so the output is repeatable.
        // Expected: 1001 | John Doe | 2026-11-14T09:00 | WAITING
        patient.checkIn(LocalDateTime.of(2026, 11, 14, 9, 0));
        System.out.println(patient);

        // Calling the patient changes the status but keeps the arrival time.
        // Expected: 1001 | John Doe | 2026-11-14T09:00 | CALLED
        patient.markCalled();
        System.out.println(patient);

    }
}