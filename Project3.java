import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * This program acts as a simulation of the CPU job scheduler. A list of jobs with the following properties:
 * JobID, Job Priority, Arrival Time, and Job Length are read in from a text file, made into 'Job' objects
 * and then placed into a queue. From the queue, the jobs are placed into a priorityQueue, where they are
 * sorted based on their priority level.
 * 
 * Systematically, jobs with the most urgent priority are removed from the priorityQueue and executed.
 * This process repeats until all jobs have been completely processed.
 * 
 * 
 * @author Steven Wojsnis
 * 			CS313
 * 			Due: May 23
 * 		  Dr. Svitak
 * Project 3 ("Job Scheduling Project")
 *
 */
public class Project3 {
	/**
	 * The main method of this program, which reads in the properties of 'jobs' from a textfile, and creates
	 * job objects accordingly, and finally adds them onto a queue, to be processed later in the program.
	 * 
	 * In the cases of the job properties not being formatted correctly, appropriate errors are thrown and caught,
	 * allowing for the continuation of the program.
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		Queue<Job> queue = new LinkedList<Job>(); //LinkedList queue to temporarily store the jobs
		int lineCount = 1; //Keeps track of what line is being processed, for error reporting reasons.
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader("project3.txt")))
		{
			String line = br.readLine();
			//While loop runs for as long their is a line with characters on it
			while(line != null){
				
				//Replaces all instances of one or more empty spaces with one empty space, and then tokenizes
				//the line by singular empty spaces. (allows for input with multiple empty spaces).
				line = line.replace("\\s+", " ");
				StringTokenizer token = new StringTokenizer(line," ");
				try{
					//Reads in all four of the needed properties and temporaily stores them as variables
					int id = Integer.parseInt(token.nextToken());
					int priority = Integer.parseInt(token.nextToken());
					int arrivalTime = Integer.parseInt(token.nextToken());
					int jobLength = Integer.parseInt(token.nextToken());
					
					//Prevents jobs with duplicate IDs. Note: If a job failed to be processed(for other reason)
					//the program acts as though that job wasn't valid, and therefore, its ID can be 
					//used again.
					if(idList.contains(id)){
						System.out.println("Job with ID: "+id+" was already processed, job on line "
								+lineCount+ " not processed");
					}
					//Prevents jobs with a negative arrivalTime
					else if(arrivalTime <0 ){
						System.out.println("Arrival Time must not be negative, job on line " 
								+lineCount+" not processed");
					}
					//Makes sure priority is between 1 and 50
					else if(priority <1 || priority > 50){
						System.out.println("Priority must be between 1 and 50, job on line " 
								+lineCount+" not processed");
					}
					//Makes sure job length is between 1 and 100
					else if(jobLength <1 || jobLength > 100){
						System.out.println("Length must be between 1 and 100, job on line " 
								+lineCount+" not processed");
					}
					//Otherwise a new job is created with the appropriate properties, and added to the queue.
					else{
						idList.add(id);
						Job newJob = new Job(id, priority, arrivalTime, jobLength);
						queue.add(newJob);
					}
				} 
				  //Catches exceptions where not all the properties were included or in appropriate format
				  catch(NumberFormatException e){
					System.out.println("Line "+lineCount+" is not in the correct format, job on line "+
							lineCount+" not added");
				} catch(NoSuchElementException e){
					System.out.println("Line "+lineCount+" doesn't contain all of the needed information, so line "
							+lineCount+" was not added");
				}
				
				//Move to next line. If an error was thrown and caught, program continues here, skipping the
				//adding of the faulty job onto the queue.
				line = br.readLine();
				lineCount++;
			}
			System.out.println("");
			//Instantiates a new scheduler object, and calls a method to execute all of the jobs on the queue.
			Scheduler schedule = new Scheduler(queue);
			schedule.startScheduling();
			
		}
		//If a file with an invalid name is used, an error message is shown and the program ends.
		catch(IOException e){
			System.out.println("Please use a text filed named 'project3.txt'");
		}
	}
}
