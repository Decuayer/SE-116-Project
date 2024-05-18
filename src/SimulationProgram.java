import java.util.*;

public class SimulationProgram {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.err.println("Usage: java Main <workflow_file> <job_file>");
            System.exit(1);
        }
        String workflowFileName = args[0];
        String jobFileName  = args[1];


        // READ CLASS CONSTRUCTOR
        // CONSTRUCTOR içerisindeki list'ler static olduğu için birlikte olmadığı zaman hata verir.
        // workflow tek başına çalışır, jobfile tek başına çalışamaz.
        ReadTextFile workflowFile = new ReadTextFile(workflowFileName, 0);
        ReadTextFile jobFile = new ReadTextFile(jobFileName,1);


        boolean w = false;
        boolean j = false;
        if(workflowFile.getEscape()) {
            System.err.println("Problem occur in workflow.txt file");
            w = true;
        }
        if (jobFile.getEscape()) {
            System.err.println("Problem occur in jobfile.txt file");
            j = true;
        }
        if(w || j ) {
            System.exit(1);
        }


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

        System.out.println("---------------------------------------");
        Simulation simulation = new Simulation(fileTaskTypesList,fileJobTypeList,fileStationList,fileJobFileList);
        simulation.initialize();
        simulation.run();
        System.out.println("---------------------------------------");
        simulation.printResults();





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
