# BinaryHeapCPUSchedulerSim
Simulates a very crude CPU scheduler using a Binary Heap

 This program acts as a simulation of the CPU job scheduler. A list of jobs with the following properties:
 JobID, Job Priority, Arrival Time, and Job Length are read in from a text file, made into 'Job' objects
 and then placed into a queue. From the queue, the jobs are placed into a priorityQueue, where they are
 sorted based on their priority level.
  
 Systematically, jobs with the most urgent priority are removed from the priorityQueue and executed.
 This process repeats until all jobs have been completely processed.
  
 It should be noted that I did not write the BinarySearchTree.java class. That was provided for the project by 
 the professor, Dr. Joseph Svitak
