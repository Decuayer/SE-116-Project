import java.util.*;

public class SimulationProgram {
    public static void main(String[] args) {

        // Command Line Control Statement
        if(args.length < 2) {
            System.err.println("Usage: java Main <workflow_file> <job_file>");
            System.exit(1);
        }
        String workflowFileName = args[0];
        String jobFileName  = args[1];

        // Read Class Initialization
        ReadTextFile workflowFile = new ReadTextFile(workflowFileName, 0);
        ReadTextFile jobFile = new ReadTextFile(jobFileName,1);

        // Shows program occur in which file
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

        // ArrayList parsed from files
        ArrayList<TaskType> fileTaskTypesList = workflowFile.getTaskTypesList();
        ArrayList<JobType> fileJobTypeList = workflowFile.getJobTypeList();
        ArrayList<Station> fileStationList = workflowFile.getStationList();
        ArrayList<JobFile> fileJobFileList = jobFile.getJobFileList();

        // Printing List
        workflowFile.taskTypesPrint();
        workflowFile.jobTypesPrint();
        workflowFile.stationsPrint();
        jobFile.jobFilePrint();

        // Simulation Methods
        Simulation simulation = new Simulation(fileTaskTypesList,fileJobTypeList,fileStationList,fileJobFileList);
        simulation.initialize();
        simulation.run();
        System.out.println("-------------------------------------------------");
        System.out.println();

        // Printing Results
        simulation.printResults();
        System.out.println();
        simulation.printWorkTime();

    }
}
