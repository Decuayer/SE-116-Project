import java.util.ArrayList;
import java.util.List;
public class WorkflowSimulationProgram {
    public static void main(String[] args) {

        ReadTextFile workflowFile = new ReadTextFile("workflow.txt", 0);



        // FILE'dan çekilen ArrayListler 
        ArrayList<TaskType> fileTaskTypesList = workflowFile.getTaskTypesList();
        ArrayList<JobType> fileJobTypeList = workflowFile.getJobTypeList();
        ArrayList<Station> fileStationList = workflowFile.getStationList();

        ReadTextFile jobFile = new ReadTextFile("jobfile.txt",1);

        ArrayList<JobFile> fileJobFileList = jobFile.getJobFileList();

        workflowFile.taskTypesPrint();
        workflowFile.jobTypesPrint();
        workflowFile.stationsPrint();
        jobFile.jobFilePrint();


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
