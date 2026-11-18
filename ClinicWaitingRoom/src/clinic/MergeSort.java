package clinic;

import java.util.ArrayList;
import java.util.List;


//this sort the waiting patients by arrival time using Merge sort algo
public class MergeSort 
{
	public List<Patient> sortByArrivalTime(List<Patient> patients)
	{
		if(patients == null) //if there is no patient
		{
			throw new IllegalArgumentException("Patient list cannot be null.");
		}//end if
		
		List<Patient> sortedPatients = new ArrayList<>(patients);
		
		//validating the patients status before sorting
		for(Patient patient : sortedPatients)
		{
			if(patient == null || !patient.getStatus().equals("WAITING") || patient.getArrivalTime() == null)
			{
				throw new IllegalArgumentException("Patients must have WAITING status and an arrival time."); 
			}//end if
		
		}//end enhance for
		
		//starting the merge sort
		if(sortedPatients.size() > 1)
		{
			mergeSort(sortedPatients, 0, sortedPatients.size() - 1);			
			
		}//end if
		
		return sortedPatients;

	}//end sortbyarrival
	
	//merge sort recursive, this devices the list into left and right partitions
	private void mergeSort(List<Patient> patients, int i, int k)
	{
		int j = 0; 
		if(i < k)
		{
			//finding the midpoint
			j = (i + k) / 2;
			
			//recursively sroting the left and right partition
			mergeSort(patients, i, j);
			mergeSort(patients, j + 1, k);
			
			//merging both the partitions in sorted order
			merge(patients, i, j, k);
			
		}//end if
		
	}//end mergeSort
	
	//mergint he two sorted partitions into one sorted partition
	private void merge(List<Patient> patients, int start, int mid, int end)
	{
		//temp list for merge patient
		List<Patient> mergedPatients = new ArrayList<>();
		
		int left = start;
		int right = mid + 1;
		
		//comparing the elements from both partitions
		while(left <= mid && right <= end)
		{
			Patient leftPatient = patients.get(left);
			Patient rightPatient = patients.get(right);
			
			//comparing the arrival times
			int comparison = leftPatient.getArrivalTime().compareTo(rightPatient.getArrivalTime());
			
			//cases where patient ID if arrival times are identical
			if(comparison == 0)
			{
				comparison = Integer.compare(leftPatient.getPatientId(), rightPatient.getPatientId());
			}//end if
			
			if(comparison <= 0)
			{
				mergedPatients.add(leftPatient);
				left++;
			}//end if
			else
			{
				mergedPatients.add(rightPatient);
				right++;
			}//end else
			
		}//end while
		
		//adding the remaining elements form the left partitions
		while(left <= mid)
		{
			mergedPatients.add(patients.get(left));
			left++;
		}//end while
		
		//adding the remaining elements from the right
		while(right <= end)
		{
			mergedPatients.add(patients.get(right));
			right++;
		}//end while
		
		//copying the temp list into the original list
		for(int i = 0; i < mergedPatients.size(); i++)
		{
			patients.set(start + i,  mergedPatients.get(i));
		}//end for
		
	}//end merge
		

}//end mergesort
