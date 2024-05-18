import java.util.*;

public class Simulation {
    ArrayList<TaskType> taskTypes;
    ArrayList<JobType> jobTypes;
    ArrayList<Station> stations;
    ArrayList<JobFile> jobFiles;
    PriorityQueue<Event> eventQueue;

    public Simulation(ArrayList<TaskType> taskTypes, ArrayList<JobType> jobTypes, ArrayList<Station> stations, ArrayList<JobFile> jobFiles) {
        this.taskTypes = taskTypes;
        this.jobTypes = jobTypes;
        this.stations = stations;
        this.jobFiles = jobFiles;
        this.eventQueue = new PriorityQueue<>();
    }

    public void initialize() {
        for (JobFile jf : jobFiles) {
            eventQueue.add(new Event(jf.getStartTime(), "start", jf));
        }
    }

    public void run() {
        while(!eventQueue.isEmpty()) {
            Event getTime = eventQueue.peek();
            Event event = eventQueue.poll();
            double time = getTime.getTime();
            System.out.println("Time: " +event.getTime() + " - Process: " +event.getType() + " - Job: " + event.getJob().getJobName() );
            handleEvent(event, time);
            System.out.println();
            System.out.println("JOBS AT STATIONS");
            for(Station s : stations) {
                System.out.print(s.getStationID() +"(Max:"+ s.getMaxCapacity()+ ",MF: " +s.isMultiFlag() + ") =>  ");
                for(JobFile jf : s.getWorkingJobs()) {
                    System.out.print(jf.getJobName()+ " ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();

        }
    }

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
            System.out.println("job : " + jf.getJobName());
            System.out.println("station : "  + station.getStationID());
            System.out.println("currentTask : " + currentTask.getTaskID());
            System.out.println("currentTask.getSize : " + currentTask.getSize());
            System.out.println("Speed : " + speed);
            System.out.println("taskDuration : " + (currentTask.getSize() / speed));


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

    private void completeTask(JobFile jf,double time) {
        Station station = jf.getCurrentStation();
        station.removeWorkingJobs(jf);
        jf.incrementTaskIndex();
        if(jf.isCompleted()) {
            jf.setCompletionTime(time);
            System.out.println(jf.getJobName() + " completed at time " + jf.getCompletionTime());
        } else {
            startJob(jf, time);
        }
    }

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

    private Station findStationForTask(Task t, JobFile jf) {
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

    public void printQueue() {
        System.out.println(eventQueue);
        while(eventQueue.iterator().hasNext()) {
            Event e = eventQueue.poll();
            System.out.println(e.getTime() + " " +e.getType()+ " " + e.getJob().getJobName());
        }
    }

    public void printResults() {
        for (JobFile jf : jobFiles) {
            if (jf.getCompletionTime() > jf.getStartTime() + jf.getDuration()) {
                System.out.println(jf.getJobName() + " is late by " + (jf.getCompletionTime() - (jf.getStartTime() + jf.getDuration())) + " minutes.");
            } else {
                System.out.println(jf.getJobName() + " completed on time. - Start Time: " + jf.getStartTime() + " Completion Time: " + jf.getCompletionTime());
            }
        }
    }


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
