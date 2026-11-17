package clinic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PatientQueue{
	private Queue<Patient> waitingQueue;
	
	
	public PatientQueue() {
		this.waitingQueue = new LinkedList<>();
	}
	
	public void enqueue(Patient patient) {
		if(patient == null) {
			throw new IllegalArgumentException("Patient cannot be null");
		}
		waitingQueue.add(patient);
	}
	
	public Patient dequeue() {
		reutrn waitingQueue.poll();
	}
	
	public List<Patient> getWaitingPatients(){
		return new ArrayList<>(waitingQueue);
	}
	
	public boolean isEmpty() {
		return waitingQueue.isEmpty();
	}
}