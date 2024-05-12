public class JobFile {
    private String jobName;
    private JobType jobType;
    private int startTime;
    private int duration;

    public JobFile(String jobName, JobType jobTypeID, int startTime, int duration) {
        setJobName(jobName);
        setJobType(jobTypeID);
        setStartTime(startTime);
        setDuration(duration);
    }

    public String getJobName() {return jobName;}
    public JobType getJobType() {return jobType;}
    public int getStartTime() {return startTime;}
    public int getDuration() {return duration;}

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
}
