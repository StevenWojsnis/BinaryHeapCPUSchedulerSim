import java.util.Queue;

/**
 * The Scheduler class contains a series of methods that facilitate the simulation of a "Job Scheduling" 
 * system on a CPU.
 * 
 * The basic flow of this class is the reception of a queue containing zero or more jobs with details such
 * as an ID, priority, arrival time, and length. These jobs are then placed onto a priorityQueue based on
 * their given arrival times. From there, jobs are taken off of the priorityQueue depending on their priority
 * level, and 'executed'. This process continues until all of the jobs are taken off of the queue, off of
 * the priorityQueue, and completely executed.
 * 
 * @author Steven Wojsnis
 *
 */
public class Scheduler {
	
	//Variables detailing the state of the machine, keeping track of the time slice, whether a job has finished
	//during the current time slice, when a job will finish, whether a job is currently running, and whether a job was added.
	static int timeSlice = 0, jobComplete, timeJobFinish = -1;
	static boolean isJobRunning = false, jobWasAdded = false;
	
	static Job runningJob; //The job that is currently being executed by the machine
	static Queue<Job> queue; //The queue from which the priorityQueue will extract jobs from
	
	//Constructor sets our queue equal to a queue, "q", which presumably contains jobs
	public Scheduler(Queue<Job> q){
		queue = q;
	}
	
	/**
	 * startScheduling is the driving force of the Scheduler class, calling on methods to add new jobs
	 * to the priorityQueue (taking into account arrivalTime), and to choose which job to execute next (taking
	 * into account priority).
	 * 
	 * Aside from calling other methods, startScheduling helps keep track of whether or not a job is currently
	 * running, as well as whether a job had been added previously in the current iteration. This method will
	 * iterate until the queue we extract jobs from, along with our own priorityQueue, is empty, and when
	 * there are no jobs running.
	 */
	public void startScheduling(){
		BinaryHeap<Job> priorityQueue = new BinaryHeap<Job>();
		
		//As long as there is a job to be extracted, executed, or currently being executed, while loop iterates.
		while(queue.peek() != null || !priorityQueue.isEmpty() || isJobRunning){

			addJobToPQ(priorityQueue); //method that will add a job from the queue to the priorityQueue at correct time.
			
			//If no job was added on addJobToPQ, the machine says that no jobs arrived.
			if(!jobWasAdded)
				System.out.println("No new job arrived during this time slice.");
			
			//If a job is running (flag triggered in selectJobToRun method), the machine reports the job ID.
			if(isJobRunning){
				System.out.println("Job with ID: '"+runningJob.getidNumber()+"' is running.");
			}
			
			//If a currently running job's calculated finish time is equal to the current time, machine ticks the isJobRunning flag off.
			if(timeJobFinish == timeSlice){
				isJobRunning = false;
			}
			
			selectJobToRun(priorityQueue); //method that chooses which job from the priorityQueue to run next
			
			timeSlice++; //moves to next time slice
			jobWasAdded = false; //resets flag for whether job was added on current time slice
			System.out.println("");
		}
	}
	
	/**
	 * addJobToPQ checks to see if the queue from which we extract jobs from is empty, and if not,
	 * whether or not the arrivalTime of the next job is equal to the current timeSlice. This ensures
	 * that jobs will only be added to our machine (priorityQueue) at their arrival time.
	 * 
	 * If it's an appropriate time to add a job to our machine, it is removed from the queue, and added
	 * to our priorityQueue (pq). This method then reports (prints out) the details of the job that was
	 * just added (its ID number, length, and priority), and sets the flag that indicates whether a job
	 * was added to 'true'
	 * 
	 * Note that the while loop in this method will loop as long as the queue is not empty, and the next
	 * item on the queue has an arrival time equal to the current timeSlice. This allows for more than one
	 * job to be added on a given timeSlice, if they have the same arrivalTime.
	 * 
	 * @param pq : our priorityQueue
	 */
	private void addJobToPQ(BinaryHeap<Job> pq){
		
		//Iterates as long as queue is non-empty, and next job on queue has appropriate arrival time
		while(queue.peek() != null && queue.peek().getArrivalTime() == timeSlice){
			Job removedJob = queue.poll(); //extracts job from the queue
			pq.insert(removedJob); //places extracted job from the queue into the priorityQueue
			
			//Prints out the details of the recently added job
			System.out.println("Added job with ID "+removedJob.getidNumber()+", length "+removedJob.getLength()+
					" and priority "+removedJob.getPriority());
			jobWasAdded = true; //Sets the flag that indicates if a job was added to the priorityQueue to true
		}
	}
	
	/**
	 * selectJobToRun does nothing if a job is currently being executed, or the priorityQueue is empty.
	 * If no job is being executed and the priorityQueue has jobs remaining, then selectJobToRun takes a 
	 * job from the top of the priorityQueue (which happens to be the job with the most urgent priority), and
	 * 'runs' it.
	 * 
	 * If a new job is chosen to be the runningJob, the timeSlice at which the new job will finish
	 * executing is calculated by adding the current timeSlice with the new job's length. Subsequently,
	 * since a job has just been set to execute, the "isJobRunning" flag, which indicates whether a job
	 * is being executed, is set to true.
	 * 
	 * In the case of a job having a length of 0, selectJobToRun calls a method to properly handle the
	 * job (which would otherwise cause an infinite loop if not handled).
	 * 
	 * @param pq : our priorityQueue
	 */
	private void selectJobToRun(BinaryHeap<Job> pq){	
		//Only executes if no job is running and jobs are remaining on the priorityQueue.
		if(!isJobRunning && !pq.isEmpty()){
			runningJob = pq.deleteMinimum(); //Selects the new job to be executed.
			timeJobFinish = timeSlice + runningJob.getLength(); //Calculates the timeSlice at which the job will end.
			isJobRunning = true; //Sets flag that indicates whether a job is being executed to true.
			
		}
	}
}
