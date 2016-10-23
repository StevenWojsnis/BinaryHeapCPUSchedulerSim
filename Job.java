/**
 * The Job class acts as a way of representing CPU jobs that have multiple properties, such as ID number,
 * priority, etc.
 * 
 * This class takes 4 properties and creates uses them as Job properties, to be used to create a Job object.
 * 
 * Job implements Comparable so that the priorityQueue can properly sort the jobs. Based on the compareTo
 * method in this class, jobs are compared based on their priority level.
 * 
 * @author Steven Wojsnis
 *
 */
public class Job implements Comparable<Job> {
	int idNumber, priority, arrivalTime, length;
	
	//Constructor takes four inputs and sets them equal to an object's idNumber, priority, arrivalTime, and length.
	public Job(int i, int p, int a, int l){
		idNumber = i;
		priority = p;
		arrivalTime = a;
		length = l;
	}
	
	//Compares Jobs based on their priority level.
	public int compareTo(Job secondJob){
		if(priority < secondJob.getPriority())
			return -1;
		else if(priority == secondJob.getPriority())
			return 0;
		else
			return 1;
	}
	
	public int getidNumber(){
		return idNumber;
	}
	public void setidNumber(int i){
		idNumber = i;
	}
	
	public int getPriority(){
		return priority;
	}
	public void setPriority(int p){
		priority = p;
	}
	
	public int getArrivalTime(){
		return arrivalTime;
	}
	public void setArrivalTime(int a){
		arrivalTime = a;
	}
	
	public int getLength(){
		return length;
	}
	public void setLength(int l){
		length = l;
	}
}
