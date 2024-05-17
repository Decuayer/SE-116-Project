import java.util.ArrayList;
import java.util.PriorityQueue;

public class Simulator {
    private ArrayList<TaskType> taskTypes;
    private ArrayList<JobType> jobTypes;
    private ArrayList<Station> stations;
    private ArrayList<JobFile> jobFiles;
    private PriorityQueue<Event> eventQueue;

    public Simulator(ArrayList<TaskType> taskTypes, ArrayList<JobType> jobTypes, ArrayList<Station> stations, ArrayList<JobFile> jobFiles) {
        this.taskTypes = taskTypes;
        this.jobTypes = jobTypes;
        this.stations = stations;
        this.jobFiles = jobFiles;
        this.eventQueue = new PriorityQueue<>();
    }

    public void initialize() {
        for(JobFile jf : jobFiles) {
            eventQueue.add(new Event(jf.getStartTime(), "start", jf));
        }
    }

    public void run() {
        while(!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            handleEvent(event);
        }

        printResults();
    }

    private void handleEvent(Event event) {
        JobFile jf = event.getJobFile();
        switch (event.getType()) {
            case "start":
                startJob(jf);
                break;
            case "complete":
                completeTask(jf);
                break;
        }
    }

    private void startJob(JobFile jobFile) {
        Task currentTask = jobFile.getJobType().getTaskList().get(jobFile.getCurrentTaskIndex());
        Station station = findStationForTask(currentTask);
        if(station != null) {
            double speed = 0;
            for(StationType s : station.getStationTypeList()) {
                if(s.getTaskType() == currentTask) {
                    speed = s.getCalculatedSpeed();
                }
            }
            int taskDuration = (int) (currentTask.getSize() / speed);
            eventQueue.add(new Event(eventQueue.peek().getTime() + taskDuration,"complete", jobFile));
            System.out.println(jobFile.getJobName() + " started at station " + station.getStationID());

        }
    }

    private void completeTask(JobFile jobFile) {
        jobFile.incrementTaskIndex();
        if(jobFile.isCompleted()) {
            jobFile.setCompletionTime(eventQueue.peek().getTime());
            System.out.println(jobFile.getJobName() + " completed at time " + jobFile.getCompletionTime());
        } else {
            startJob(jobFile);
        }
    }

    private Station findStationForTask(Task task) {
        for (Station station : stations) {
            for(StationType s : station.getStationTypeList()) {
                if(s.getTaskType() == task.getTaskType()) {
                    return station;
                }
            }
        }
        return null;
    }

    private void printResults() {
        for (JobFile jf : jobFiles) {
            if (jf.getCompletionTime() > jf.getStartTime() + jf.getDuration()) {
                System.out.println(jf.getJobName() + " is late by " + (jf.getCompletionTime() - (jf.getStartTime() + jf.getDuration())) + " minutes.");
            } else {
                System.out.println(jf.getJobName() + " completed on time.");
            }
        }

        for (Station station : stations) {
            // İstasyon kullanım oranlarını hesaplayın ve yazdırın
        }
    }


}
