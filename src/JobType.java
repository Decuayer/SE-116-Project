import java.util.ArrayList;
import java.util.List;

public class JobType {
    private String jobID;
    private List<Task> taskList= new ArrayList<>();

    public JobType() {

    }

    public JobType(String jobID, List<Task> taskList) {
        setJobID(jobID);
        setTaskList(taskList);
    }


    public void setJobID(String jobID) {
        //taskID formatı doğru mu kontrol eden try catch. (J1 doğru / 1J yanlış)
        this.jobID = jobID;
    }
    public void setTaskList(List<Task> taskList) {
        // taskList içerisinde sadece bir adet aynı task olabilir. Bunu kontrol eden try catch.
        this.taskList = taskList;
    }

    public String getJobID() {return jobID;}

    public List<Task> getTaskList() {return taskList;}


}
