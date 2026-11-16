package clinic;

//Manually checks patient insertion, ID lookup, and listing.
public class PatientBSTTest {

	public static void main(String[] args) {

	     PatientBST directory = new PatientBST();
	
	     // Add patients out of ID order to check TreeMap's ordering.
	     Patient sofia = new Patient(1003, "Sofia Martinez");
	     Patient amara = new Patient(1001, "Amara Okafor");
	     Patient oliver = new Patient(1005, "Oliver Smith");
	     Patient aiko = new Patient(1002, "Aiko Tanaka");
	     Patient omar = new Patient(1004, "Omar Hassan");
	
	     System.out.println("Add Sofia: " + directory.addPatient(sofia));
	     System.out.println("Add Amara: " + directory.addPatient(amara));
	     System.out.println("Add Oliver: " + directory.addPatient(oliver));
	     System.out.println("Add Aiko: " + directory.addPatient(aiko));
	     System.out.println("Add Omar: " + directory.addPatient(omar));
	
	     // Reject the duplicate ID without replacing Amara's record.
	     Patient duplicate = new Patient(1001, "Priya Sharma");
	
	     System.out.println("Add duplicate: "
	             + directory.addPatient(duplicate));
	
	     // Check an existing ID and an ID that is not present.
	     System.out.println("Find 1001: "
	             + directory.findPatientById(1001));
	
	     System.out.println("Find 9999: "
	             + directory.findPatientById(9999));
	
	     // Confirm that only the five unique patients were added.
	     System.out.println("Total patients: "
	             + directory.getAllPatients().size());
	
	     System.out.println("All patients:");
	
	     for (Patient patient : directory.getAllPatients()) {
	         System.out.println(patient);
	     }
	     
	     // Different patients can share a name, but must have different IDs.
	     Patient anotherAmara = new Patient(1006, "Amara Okafor");
	     directory.addPatient(anotherAmara);

	     // Check that capitalization and spaces do not affect the search.
	     System.out.println("Search for amara okafor:");

	     for (Patient patient :
	             directory.findPatientsByName("  amara okafor  ")) {
	         System.out.println(patient);
	     }

	     // Both patients with the same name should be found.
	     System.out.println("Matching patients: "
	             + directory.findPatientsByName("Amara Okafor").size());

	     // A name that is not in the directory should return an empty list.
	     System.out.println("Missing name matches: "
	             + directory.findPatientsByName("Elena Petrova").size());

	     // This method matches full names, not first names alone.
	     System.out.println("First name only matches: "
	             + directory.findPatientsByName("Amara").size());
	}
}