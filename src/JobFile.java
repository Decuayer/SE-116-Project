public class JobFile {
    private String jobName;
    private JobType jobType;
    private int startTime;
    private int duration;
    private int currentTaskIndex;
    private int completionTime;

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
    public int getCompletionTime() {return  completionTime;}

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
    public void setCompletionTime(int completionTime) {this.completionTime = completionTime;}

    public void incrementTaskIndex() {
        currentTaskIndex++;
    }

    public boolean isCompleted() {
        return currentTaskIndex >= getJobType().getTaskList().size();
    }

}
