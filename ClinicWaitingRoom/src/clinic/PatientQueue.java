package clinic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PatientQueue{
	private Queue<Patient> waitingQueue;
	
	//Declare Queue list
	public PatientQueue() {
		this.waitingQueue = new LinkedList<>();
	}
	
	//Exception throw for empty list
	public void enqueue(Patient patient) {
		if(patient == null) {
			throw new IllegalArgumentException("Patient cannot be null");
		}
		waitingQueue.add(patient);
	}
	
	//Dequeue waiting list
	public Patient dequeue() {
		return waitingQueue.poll();
	}
	
	//Copy of list
	public List<Patient> getWaitingPatients(){
		return new ArrayList<>(waitingQueue);
	}
	
	//boolean if empty check
	public boolean isEmpty() {
		return waitingQueue.isEmpty();
	}
}