import java.util.ArrayList;
import java.util.List;

public class JobType {
    private String jobID;
    private List<Task> taskList= new ArrayList<>();

    public JobType() {}

    public JobType(String jobID, List<Task> taskList) {
        setJobID(jobID);
        setTaskList(taskList);
    }

    public void setJobID(String jobID) {this.jobID = jobID;}
    public void setTaskList(List<Task> taskList) {this.taskList = taskList;}

    public String getJobID() {return jobID;}
    public List<Task> getTaskList() {return taskList;}

}
