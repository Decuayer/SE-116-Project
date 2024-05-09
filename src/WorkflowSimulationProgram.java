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

        StationType T1_S1 = new StationType(T1,2);
        StationType T2_S1 = new StationType(T2,3,0.20);
        StationType T1_S2 = new StationType(T1,2);
        StationType T2_S2 = new StationType(T2,4);
        StationType T3_S3 = new StationType(T3,1);
        StationType T4_S4 = new StationType(T4,1);
        StationType T21_S4 = new StationType(T21,2,0.50);

        List<StationType> S1List = new ArrayList<StationType>();
        List<StationType> S2List = new ArrayList<StationType>();
        List<StationType> S3List = new ArrayList<StationType>();
        List<StationType> S4List = new ArrayList<StationType>();

        S1List.add(T1_S1);
        S1List.add(T2_S1);
        S2List.add(T1_S2);
        S2List.add(T2_S2);
        S3List.add(T3_S3);
        S4List.add(T4_S4);
        S4List.add(T21_S4);

        Station S1 = new Station("S1",1,false,false, S1List);
        Station S2 = new Station("S2",2,false,true, S2List);
        Station S3 = new Station("S3",2,false,true, S3List);
        Station S4 = new Station("S4",3,true,true, S4List);

        List<Station> stationList = new ArrayList<Station>();

        stationList.add(S1);
        stationList.add(S2);
        stationList.add(S3);
        stationList.add(S4);

        StationWork stations = new StationWork("Stations",stationList);




        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getSpeed());
        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getPlusMinus());
        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getCalculatedSpeed());







    }

}
