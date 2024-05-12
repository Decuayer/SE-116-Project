public class JobFile {
    private String jobName;
    private JobType jobTypeID;
    private int startTime;
    private int duration;

    public JobFile(String jobName, JobType jobTypeID, int startTime, int duration) {
        setJobName(jobName);
        setJobTypeID(jobTypeID);
        setStartTime(startTime);
        setDuration(duration);
    }

    public String getJobName() {return jobName;}
    public JobType getJobTypeID() {return jobTypeID;}
    public int getStartTime() {return startTime;}
    public int getDuration() {return duration;}

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public void setJobTypeID(JobType jobTypeID) {
        this.jobTypeID = jobTypeID;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
