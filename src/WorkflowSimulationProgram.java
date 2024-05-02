import java.util.ArrayList;
import java.util.List;
public class WorkflowSimulationProgram {
    public static void main(String[] args) {

        TaskType T1 = new TaskType("T1", 1);
        TaskType T2 = new TaskType("T2", 2);
        TaskType T3 = new TaskType("T3", 2.5);
        TaskType T4 = new TaskType("T4");
        TaskType T5 = new TaskType("T5", 4);
        TaskType T_1 = new TaskType("T_1", 5);
        TaskType T21 = new TaskType("T21");

        // Bütün TaskTypeların bulunduğu bir liste

        List<Task> taskList1 = new ArrayList<>();
        List<Task> taskList2 = new ArrayList<>();
        List<Task> taskList3 = new ArrayList<>();
        List<Task> taskList4 = new ArrayList<>();


        taskList1.add(new Task(T1,1));
        taskList1.add(new Task(T2));
        taskList1.add(new Task(T3,3));

        taskList2.add(new Task(T2));
        taskList2.add(new Task(T3));
        taskList2.add(new Task(T4,1));

        taskList3.add(new Task(T2));

        taskList4.add(new Task(T21,5));
        taskList4.add(new Task(T1,2));

        // JobType'ların içerisinde bulunan Task'ların TaskType'larını içeren bir liste
        
        JobType J1 = new JobType("J1",taskList1);
        JobType J2 = new JobType("J2",taskList2);
        JobType J3 = new JobType("J3",taskList3);
        JobType J4 = new JobType("J4",taskList4);


        List<JobType> jobTypeList = new ArrayList<>();

        jobTypeList.add(J1);
        jobTypeList.add(J2);
        jobTypeList.add(J3);
        jobTypeList.add(J4);

        Job jobTypes = new Job("JobTypes",jobTypeList);

        System.out.println(jobTypes.getTaskSequence().get(0).getTaskList().get(2).getSize());
        System.out.println(jobTypes.getTaskSequence().get(0).getTaskList().get(1).getSize());







    }

}
