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


        taskList1.add(new Task(T1, 1));
        taskList1.add(new Task(T2));
        taskList1.add(new Task(T3, 3));

        taskList2.add(new Task(T2));
        taskList2.add(new Task(T3));
        taskList2.add(new Task(T4, 1));

        taskList3.add(new Task(T2));

        taskList4.add(new Task(T21, 5));
        taskList4.add(new Task(T1, 2));

        // JobType'ların içerisinde bulunan Task'ların TaskType'larını içeren bir liste

        JobType J1 = new JobType("J1", taskList1);
        JobType J2 = new JobType("J2", taskList2);
        JobType J3 = new JobType("J3", taskList3);
        JobType J4 = new JobType("J4", taskList4);


        List<JobType> jobTypeList = new ArrayList<>();

        jobTypeList.add(J1);
        jobTypeList.add(J2);
        jobTypeList.add(J3);
        jobTypeList.add(J4);

        Job jobTypes = new Job("JobTypes", jobTypeList);

        StationType T1_S1 = new StationType(T1, 2);
        StationType T2_S1 = new StationType(T2, 3, 0.20);
        StationType T1_S2 = new StationType(T1, 2);
        StationType T2_S2 = new StationType(T2, 4);
        StationType T3_S3 = new StationType(T3, 1);
        StationType T4_S4 = new StationType(T4, 1);
        StationType T21_S4 = new StationType(T21, 2, 0.50);

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

        Station S1 = new Station("S1", 1, false, false, S1List);
        Station S2 = new Station("S2", 2, false, true, S2List);
        Station S3 = new Station("S3", 2, false, true, S3List);
        Station S4 = new Station("S4", 3, true, true, S4List);

        List<Station> stationList = new ArrayList<Station>();

        stationList.add(S1);
        stationList.add(S2);
        stationList.add(S3);
        stationList.add(S4);

        StationWork stations = new StationWork("Stations", stationList);

        JobFile Job1 = new JobFile("Job1", J1, 1, 30);
        JobFile Job2 = new JobFile("Job2", J1, 2, 29);
        JobFile Job3 = new JobFile("Job3", J2, 5, 40);
        JobFile Job4 = new JobFile("Job4", J2, 7, 35);
        JobFile Job5 = new JobFile("Job5", J3, 10, 30);

        //getStartTime()
        //getDuration()
        //getJobTypeID()


        ArrayList<JobFile> jobFileList = new ArrayList<JobFile>();

        jobFileList.add(Job1);
        jobFileList.add(Job2);
        jobFileList.add(Job3);
        jobFileList.add(Job4);
        jobFileList.add(Job5);

        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getSpeed());
        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getPlusMinus());
        System.out.println(stations.getStationList().get(0).getStationTypeList().get(0).getCalculatedSpeed());


        ReadTextFile workflowFile = new ReadTextFile("workflow.txt", 0);

        workflowFile.taskTypesPrint();
        workflowFile.jobTypesPrint();
        workflowFile.stationsPrint();


        // FILE'dan çekilen ArrayListler 
        ArrayList<TaskType> fileTaskTypesList = workflowFile.getTaskTypesList();
        ArrayList<JobType> fileJobTypeList = workflowFile.getJobTypeList();
        ArrayList<Station> fileStationList = workflowFile.getStationList();



        /*
        --jobfile.txt
        Job1 J1  1  30
        Job2 J1  2  29
        Job3 J2  5  40
        Job4 J2  7  35
        Job5 J3  10  30

        --workflow.txts
        (TASKTYPES T1 1 T2 2 T3 2.5 T4 T5 4 T_1 5 T21)
        (JOBTYPES
            (J1  T1 1 T2  T3 3)
            (J2  T2  T3 T4 1 )
            (J3  T2)
            (J4  T21 5 T1 2) )
        (STATIONS
            (S1 1 N N T1 2 T2 3 0.20)
            (S2  2 N Y T1 2 T2 4)
            (S3   2 N Y T3 1)
            (S4  3 Y Y T4 1 T21 2 0.50)
        )
        Önce JobNameden Jobtype seç-> tobtypetaki tasktypelara sırayla bak
        */
        ArrayList<Station> Applicable = new ArrayList<>();

        /*for (int j = 0; j < jobFileList.size(); j++) {
            System.out.println("A");
            for (int k = 0; k < jobFileList.get(j).getJobTypeID().getTaskList().size(); k++) {
                System.out.println("B");
                for (int i = 0; i < stationList.get(i).getStationTypeList().size(); i++) {

                    System.out.println("C");
                    if (jobFileList.get(j).getJobTypeID().getTaskList().get(k).getTaskType().equals(stationList.get(n).getStationTypeList().get(m).getTaskType())) {
                        n++;
                        m++;
                        System.out.println(jobFileList.get(j).getJobTypeID().getTaskList().get(k).getTaskID() + " == " + stationList.get(i).getStationTypeList().get(i).getTaskType().getTaskID());
                        System.out.println(stationList.get(i).getStationID());
                       // System.out.println("abc");
                       // Applicable.add(stationList.get(i));
                       // work(stationList.get(i),jobFileList.get(j).getJobTypeID().getTaskList().get(k).getTaskType());
                    }
                }

            }
        }
        /
         */
        /*int a=0;
        int m=0;
        for(int i=0; i<stationList.size();i++){
            for(int j=0; j<stationList.get(i).getStationTypeList().size();j++){
                System.out.println("a");

                    if(stationList.get(i).getStationTypeList().get(j).getTaskType().equals(jobFileList.get(m).getJobTypeID().getTaskList().get(a).getTaskType())){
                        a++;
                        m++;
                        System.out.println("b");
                    }


            }
        }
        for (int k = 0; k < jobFileList.get(k).getJobTypeID().getTaskList().size(); k++) {

            for (int i = 0; i < stationList.get(i).getStationTypeList().size(); i++) {
                System.out.println();
            }
        }
        */

    }
}
