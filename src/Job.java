import java.util.List;
public class Job extends JobType{
    private String jobName;
    private List<JobType> taskSequence;

    public Job() {

    }
    public Job(String jobName, List<JobType> taskSequence) {
        setJobName(jobName);
        setTaskSequence(taskSequence);
    }


    public void setTaskSequence(List<JobType> taskSequence) {
        // List'enin içersinde aynı JobID'ye sahip olanları yakalayan try catch.
        this.taskSequence = taskSequence;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {return jobName;}
    public List<JobType> getTaskSequence() {return taskSequence;}

}
