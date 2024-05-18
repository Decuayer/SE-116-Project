public class JobFile {
    private String jobName;
    private JobType jobType;
    private int startTime;
    private int duration;
    private int currentTaskIndex;
    private double completionTime;
    private Station currentStation;

    public JobFile(String jobName, JobType jobTypeID, int startTime, int duration) {
        setJobName(jobName);
        setJobType(jobTypeID);
        setStartTime(startTime);
        setDuration(duration);
        setCurrentTaskIndex(0);
        setCompletionTime(0);
    }

    public String getJobName() {return jobName;}
    public JobType getJobType() {return jobType;}
    public int getStartTime() {return startTime;}
    public int getDuration() {return duration;}
    public int getCurrentTaskIndex() {return currentTaskIndex;}
    public double getCompletionTime() {return  completionTime;}
    public Station getCurrentStation() {return currentStation;}

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setCurrentTaskIndex(int currentTaskIndex) {this.currentTaskIndex = currentTaskIndex;}
    public void setCompletionTime(double completionTime) {this.completionTime = completionTime;}
    public void setCurrentStation(Station currentStation) {this.currentStation = currentStation;}


    public void incrementTaskIndex() {
        currentTaskIndex++;
    }

    public boolean isCompleted() {
        return currentTaskIndex >= getJobType().getTaskList().size();
    }

}
