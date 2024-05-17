import java.util.*;

public class SimulationProgram {
    public static void main(String[] args) {

        // READ CLASS CONSTRUCTOR
        // CONSTRUCTOR içerisindeki list'ler static olduğu için birlikte olmadığı zaman hata verir.
        // workflow tek başına çalışır, jobfile tek başına çalışamaz.
        ReadTextFile workflowFile = new ReadTextFile("workflow.txt", 0);
        ReadTextFile jobFile = new ReadTextFile("jobfile.txt",1);

        // FILE'dan çekilen ArrayListler 
        ArrayList<TaskType> fileTaskTypesList = workflowFile.getTaskTypesList();
        ArrayList<JobType> fileJobTypeList = workflowFile.getJobTypeList();
        ArrayList<Station> fileStationList = workflowFile.getStationList();
        ArrayList<JobFile> fileJobFileList = jobFile.getJobFileList();

        // LIST PRINTS
        workflowFile.taskTypesPrint();
        workflowFile.jobTypesPrint();
        workflowFile.stationsPrint();
        jobFile.jobFilePrint();


        Simulator simulator = new Simulator(fileTaskTypesList, fileJobTypeList, fileStationList, fileJobFileList);
        simulator.initialize();
        simulator.run();






        /*
        int time = 0;
        int endtime = -1;

        for(JobFile job : fileJobFileList) {
            if(job.getDuration() >= endtime) {
                endtime = job.getDuration()+job.getStartTime();
            }
        }

        Map<JobFile, Map<Task, ArrayList<Station>>> jobSequence = new HashMap<JobFile, Map<Task, ArrayList<Station>>>();
        for(JobFile job : fileJobFileList) {
            Map<Task, ArrayList<Station>> taskSequence = new HashMap<Task, ArrayList<Station>>();
            for(Task task : job.getJobType().getTaskList()) {
                ArrayList<Station> stationSequence = new ArrayList<Station>();
                for(Station station : fileStationList) {
                    for(StationType stationType : station.getStationTypeList()) {
                        if(stationType.getTaskID() == task.getTaskID()) {
                            stationSequence.add(station);
                        }
                    }
                }
                taskSequence.put(task,compareMethod(task,stationSequence));
            }
            jobSequence.put(job, taskSequence);
        }
        for (Map.Entry<JobFile, Map<Task, ArrayList<Station>>> job : jobSequence.entrySet()) {
            System.out.println(job.getKey().getJobName() + " => ");
            for (Map.Entry<Task, ArrayList<Station>> task : job.getValue().entrySet()) {
                System.out.print("\t"+task.getKey().getTaskID() + "("+task.getKey().getSize()+") ");
                for(Station station : task.getValue()) {
                    station.addStaticStation(job.getKey());
                    System.out.print(station.getStationID() + "(");
                    for(StationType stationType : station.getStationTypeList()) {
                        if(task.getKey().getTaskID() == stationType.getTaskID()) {
                            System.out.printf("%.1f) ", stationType.getCalculatedSpeed());
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        for (Map.Entry<JobFile, Map<Task, ArrayList<Station>>> job : jobSequence.entrySet()) {
            for (Map.Entry<Task, ArrayList<Station>> task : job.getValue().entrySet()) {

            }
        }


        ArrayList<Boolean> jobsContinue = new ArrayList<Boolean>();
        for (JobFile j : fileJobFileList) {
            jobsContinue.add(false);
        }
        System.out.println();
        for(; time < endtime; time++) {
            for(int i = 0; i < fileJobFileList.size(); i++) {
                if(fileJobFileList.get(i).getStartTime() == time) {
                    jobsContinue.set(i,true);
                }
            }
        }
        for (Station x : fileStationList) {
            x.printStaticList();
            System.out.println();
        }
        */
    }


    public static ArrayList<Station> compareMethod(Task task, ArrayList<Station> station) {
        ArrayList<Station> newArr = new ArrayList<Station>();
        ArrayList<StationType> stationTypeList = new ArrayList<StationType>();
        for(Station x : station) {
            for(StationType y : x.getStationTypeList()) {
                if(y.getTaskID() == task.getTaskID()) {
                    stationTypeList.add(y);
                }
            }
        }
        Collections.sort(stationTypeList, new SpeedComparator());
        for(StationType x : stationTypeList) {
            for(Station y : station) {
                for(StationType z : y.getStationTypeList()) {
                    if(x == z) {
                        newArr.add(y);
                    }
                }
            }
        }
        return newArr;
    }
}
