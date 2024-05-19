import java.util.*;

public class Simulation {
    public ArrayList<TaskType> taskTypes;
    public ArrayList<JobType> jobTypes;
    public ArrayList<Station> stations;
    public ArrayList<JobFile> jobFiles;
    public PriorityQueue<Event> eventQueue;
    public static Map<Station, Double> stationWorkTimeCounter = new HashMap<Station, Double>();

    public Simulation() { }

    public Simulation(ArrayList<TaskType> taskTypes, ArrayList<JobType> jobTypes, ArrayList<Station> stations, ArrayList<JobFile> jobFiles) {
        this.taskTypes = taskTypes;
        this.jobTypes = jobTypes;
        this.stations = stations;
        this.jobFiles = jobFiles;
        this.eventQueue = new PriorityQueue<>();
    }

    // Initialize eventQueue
    public void initialize() {
        for (JobFile jf : jobFiles) {
            eventQueue.add(new Event(jf.getStartTime(), "start", jf));
        }
    }

    // Get elements from eventQueue
    public void run() {
        while(!eventQueue.isEmpty()) {
            Event getTime = eventQueue.peek();
            Event event = eventQueue.poll();
            Event afterTime = eventQueue.peek();
            double time = getTime.getTime();
            System.out.println();
            System.out.println("------------------ TIME: " +String.format("%.2f", event.getTime()) +  " ------------------");
            System.out.println();
            System.out.println("Time: " +String.format("%.2f", event.getTime()) + " - Process: " +event.getType() + " - Job: " + event.getJob().getJobName() );
            handleEvent(event, time);
            System.out.println();
            System.out.println("STATION INFO");
            for(Station s : stations) {
                System.out.print(s.getStationID() +"(Max:"+ s.getMaxCapacity()+ ",MF: " +s.isMultiFlag() + ") =>  ");
                for(JobFile jf : s.getWorkingJobs()) {
                    System.out.print(jf.getJobName()+ " ");
                }
                System.out.println();
            }
            System.out.println();
            for (JobFile jf : jobFiles) {
                if(jf.getCurrentStation() != null) {
                    System.out.println(jf.getJobName() + " => Working on " + jf.getCurrentStation().getStationID());
                } else {
                    if(time < jf.getStartTime()) {
                        System.out.println(jf.getJobName() + " => Waiting to start. (Start time: " + jf.getStartTime()+ ")");
                    } else {
                        if (jf.getJobType().getTaskList().size() > jf.getCurrentTaskIndex()) {
                            Task currentTask = jf.getJobType().getTaskList().get(jf.getCurrentTaskIndex());
                            Map<JobFile, Map<Task, ArrayList<Station>>> jobSequence = new HashMap<JobFile, Map<Task, ArrayList<Station>>>();
                            for(JobFile job : jobFiles) {
                                Map<Task, ArrayList<Station>> taskSequence = new HashMap<Task, ArrayList<Station>>();
                                for(Task task : job.getJobType().getTaskList()) {
                                    ArrayList<Station> stationSequence = new ArrayList<Station>();
                                    for(Station station : stations) {
                                        for(StationType stationType : station.getStationTypeList()) {
                                            if(stationType.getTaskID() == task.getTaskID()) {
                                                stationSequence.add(station);
                                            }
                                        }
                                    }
                                    taskSequence.put(task,compareMethod(task,stationSequence));
                                }
                                jobSequence.put(job, taskSequence);
                            }
                            Station station = jobSequence.get(jf).get(currentTask).get(0);
                            if(station != null) {
                                System.out.println(jf.getJobName() + " => Waiting for to " + station.getStationID());
                            } else {
                                System.out.println(jf.getJobName() + " => - ?");
                            }
                        } else {
                            System.out.println(jf.getJobName() + " => - *");
                        }
                    }
                }
            }
            System.out.println();
            for(Station s : stations) {
                if(s.getWorkingJobs().size() > 0) {
                    try {
                        stationWorkTimeCounter.put(s, stationWorkTimeCounter.get(s)+afterTime.getTime()-getTime.getTime());
                    } catch (NullPointerException e) {
                        if(afterTime != null) {
                            stationWorkTimeCounter.put(s, afterTime.getTime()-getTime.getTime());
                        }
                    }
                }
            }

        }
    }

    // Process eventQueue state of type events
    private void handleEvent(Event event, double time) {
        JobFile jf = event.getJob();
        switch (event.getType()) {
            case "start":
                startJob(jf, time);
                break;
            case "complete":
                completeTask(jf, time);
                break;
            case "wait":
                waitEmptyStation(jf, time);
                break;
        }
    }

    // Starting the job
    private void startJob(JobFile jf,double time) {
        Task currentTask = jf.getJobType().getTaskList().get(jf.getCurrentTaskIndex());
        Station station = findStationForTask(currentTask, jf);
        if(station != null) {
            double speed = 0;
            for(StationType s : station.getStationTypeList()) {
                if(s.getTaskID().equals(currentTask.getTaskID())) {
                    speed = s.getCalculatedSpeed();
                }
            }
            double taskDuration = (double) (currentTask.getSize() / speed);
            System.out.println("Job               : " + jf.getJobName());
            System.out.println("Station           : "  + station.getStationID());
            System.out.println("Current Task      : " + currentTask.getTaskID());
            try {
                System.out.println("Next Task         : " + jf.getJobType().getTaskList().get(jf.getCurrentTaskIndex()+1).getTaskID());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Next Task         : -");
            }
            System.out.println("Current Task Size : " + currentTask.getSize());
            System.out.println("Speed             : " + String.format("%.2f",speed));
            System.out.println("Task Duration     : " + String.format("%.2f",(currentTask.getSize() / speed)));
            eventQueue.add(new Event((double)time + taskDuration, "complete", jf));
            station.addWorkingJobs(jf);
            jf.setCurrentStation(station);
        } else {
            if (eventQueue.isEmpty()) {
                eventQueue.add(new Event(time+1, "wait", jf));
            } else {
                eventQueue.add(new Event(eventQueue.peek().getTime(), "wait", jf));
            }
            System.out.println("There is no any empty station for " + currentTask.getTaskID()+ ". Waitting... " );
        }
    }

    // Control tasks done
    private void completeTask(JobFile jf,double time) {
        Station station = jf.getCurrentStation();
        station.removeWorkingJobs(jf);
        jf.setCurrentStation(null);
        jf.incrementTaskIndex();
        if(jf.isCompleted()) {
            jf.setCompletionTime(time);
            System.out.println(jf.getJobName() + " completed at time " + String.format("%.2f",jf.getCompletionTime()));
        } else {
            startJob(jf, time);
        }
    }

    // If station is occupied waits for it
    private void waitEmptyStation(JobFile jf, double time) {
        Task currentTask = jf.getJobType().getTaskList().get(jf.getCurrentTaskIndex());
        Station station = findStationForTask(currentTask, jf);
        if(station != null) {
            startJob(jf, time);
        } else {
            if (eventQueue.isEmpty()) {
                eventQueue.add(new Event(time+1, "wait", jf));
            } else {
                if(time == eventQueue.peek().getTime() ) {
                    eventQueue.add(new Event(time+1, "wait", jf));
                } else {
                    eventQueue.add(new Event(eventQueue.peek().getTime(), "wait", jf));
                }
            }
            System.out.println("There is no any empty station for " + currentTask.getTaskID()+ ". Waitting... " );
        }
    }

    // Find available and best option for a task among stations
    protected Station findStationForTask(Task t, JobFile jf) {
        Map<JobFile, Map<Task, ArrayList<Station>>> jobSequence = new HashMap<JobFile, Map<Task, ArrayList<Station>>>();
        for(JobFile job : jobFiles) {
            Map<Task, ArrayList<Station>> taskSequence = new HashMap<Task, ArrayList<Station>>();
            for(Task task : job.getJobType().getTaskList()) {
                ArrayList<Station> stationSequence = new ArrayList<Station>();
                for(Station station : stations) {
                    for(StationType stationType : station.getStationTypeList()) {
                        if(stationType.getTaskID() == task.getTaskID()) {
                            stationSequence.add(station);
                        }
                    }
                }
                taskSequence.put(task,compareMethod(task,stationSequence));
            }
            jobSequence.put(job, taskSequence);
        }
        int index = 0;
        while (true) {
            if (index >= jobSequence.get(jf).get(t).size()) {
                return null;
            }
            Station rStation = jobSequence.get(jf).get(t).get(index);
            if (rStation.isMultiFlag()) {
                if (rStation.getWorkingJobs().isEmpty()) {
                    break;
                } else {
                    if (rStation.getMaxCapacity() == rStation.getWorkingJobs().size()) {
                        index++;
                        continue;
                    }
                }
            } else {
                if(rStation.getWorkingJobs().isEmpty()) {
                    break;
                } else {
                    if (rStation.getWorkingJobs().size() >= 1) {
                        index++;
                        continue;
                    }
                }
            }
            break;
        }
        return jobSequence.get(jf).get(t).get(index);
    }

    // Print eventQueue for developer control (not used)
    public void printQueue() {
        System.out.println(eventQueue);
        while(eventQueue.iterator().hasNext()) {
            Event e = eventQueue.poll();
            System.out.println(e.getTime() + " " +e.getType()+ " " + e.getJob().getJobName());
        }
    }

    // Print Jobs start and completion time
    public void printResults() {
        for (JobFile jf : jobFiles) {
            if (jf.getCompletionTime() > jf.getStartTime() + jf.getDuration()) {
                System.out.println(jf.getJobName() + " is late by " + String.format("%.2f",jf.getCompletionTime() - (jf.getStartTime() + jf.getDuration())) + " minutes." + " - Start Time: " + jf.getStartTime() + " | Deadline: " +(jf.getStartTime()+jf.getDuration()) + " | Completion Time: " + String.format("%.2f",jf.getCompletionTime()));
            } else {
                System.out.println(jf.getJobName() + " completed on time. - Start Time: " + jf.getStartTime() + " | Deadline: " +(jf.getStartTime()+jf.getDuration()) + " | Completion Time: " + String.format("%.2f",jf.getCompletionTime()));
            }
        }
    }

    // Print stations utilization time
    public void printWorkTime() {
        for (Station s : stations) {
            System.out.println(s.getStationID() + " station work time : " + String.format("%.2f",stationWorkTimeCounter.get(s)));
        }
    }

    // Compare Task with Station Subclass Elements
    private static ArrayList<Station> compareMethod(Task task, ArrayList<Station> station) {
        ArrayList<Station> newArr = new ArrayList<Station>();
        ArrayList<StationType> stationTypeList = new ArrayList<StationType>();
        for(Station x : station) {
            for(StationType y : x.getStationTypeList()) {
                if(y.getTaskID() == task.getTaskID()) {
                    stationTypeList.add(y);
                }
            }
        }
        Collections.sort(stationTypeList, new SpeedComparator());
        for(StationType x : stationTypeList) {
            for(Station y : station) {
                for(StationType z : y.getStationTypeList()) {
                    if(x == z) {
                        newArr.add(y);
                    }
                }
            }
        }
        return newArr;
    }
}
