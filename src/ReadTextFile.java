import java.io.IOException;
import java.util.*;
import java.nio.file.Paths;
import java.lang.StringBuilder;

public class ReadTextFile {
    private static Scanner input;
    private String fileName;
    private static ArrayList<String> txtList = new ArrayList<String>();
    private static ArrayList<TaskType> taskTypesList = new ArrayList<TaskType>();
    private static ArrayList<JobType> jobTypeList = new ArrayList<JobType>();
    private static ArrayList<Station> stationList = new ArrayList<Station>();
    private static ArrayList<JobFile> jobFileList = new ArrayList<JobFile>();
    private boolean escape = false;

    public ReadTextFile(String txt, int choice) {
        switch (choice) {
            // 0 => workfflow.txt
            case 0:
                openFile(txt);
                readFile(2);
                closeFile();
                workflowProcess();
                break;
            // 1 => jobfile.txt
            case 1:
                openFile(txt);
                readFile(2);
                closeFile();
                jobfileProcess();
                break;
        }
    }

    public ArrayList<TaskType> getTaskTypesList() {return taskTypesList;}
    public ArrayList<JobType> getJobTypeList() {return jobTypeList;}
    public ArrayList<Station> getStationList() {return stationList;}
    public ArrayList<JobFile> getJobFileList() {return jobFileList;}

    // JOBFILE PROCESS METHOD
    private void jobfileProcess() {

        ArrayList<String> jobFileString = new ArrayList<String>();
        ArrayList<String> jobFileControl = new ArrayList<String>();

        boolean jobFileResume = false;
        int jobFileIndex = 0;
        int jobfileCount = 0;

        for(int i = 0; i < txtList.size(); i++) {
            String[] jobFileWords = txtList.get(i).split(" ");
            for(String j : jobFileWords) {
                jobFileString.add(j.trim());
            }
        }
        Iterator<String> jobFileIterator = jobFileString.iterator();
        while(jobFileIterator.hasNext()) {
            String jobFileElement = jobFileIterator.next();
            if(jobFileElement.isEmpty()) {
                jobFileIterator.remove();
            }
        }
        ArrayList<ArrayList<String>> jobFile = new ArrayList<>();
        for(int i = 0;  i < jobFileString.size(); i++) {
            if(jobfileCount == 4) {
                jobFileIndex++;
                jobfileCount = 0;
                jobFileResume = false;
            }
            if (jobFileString.get(i).startsWith("Job") && Character.isDigit(jobFileString.get(i).charAt(3))) {
                jobFile.add(new ArrayList<>());
                jobFileResume = true;
            }
            if(jobFileResume) {
                try {
                    jobFileControl.add(jobFileString.get(i));
                    jobFile.get(jobFileIndex).add(jobFileString.get(i));
                    jobfileCount++;
                } catch (IndexOutOfBoundsException eIndex) {
                    System.out.println("Line " + jobFileIndex + " : " + jobFileString.get(i) + " invalid value. Delete value.");
                }
            }
        }
        int startTime = 0;
        int duration = 0;
        for(int i = 0; i < jobFile.size(); i++) {
            startTime = 0;
            duration = 0;
            for(int j = 0; j < jobFile.get(i).size(); j++) {
                if (jobFile.get(i).get(j).contains("Job")) {
                    if(j != jobFile.get(i).size()-3) {
                        for (JobType x: jobTypeList) {
                            if(x.getJobID().equals(jobFile.get(i).get(j+1))) {
                                if(Character.isLetter(jobFile.get(i).get(j+1).charAt(0))) {
                                    try {
                                        if(!(Integer.parseInt(jobFile.get(i).get(j+2)) <= 0)) {
                                            startTime = Integer.parseInt(jobFile.get(i).get(j+2));
                                        } else {
                                            escape = true;
                                            System.out.println("Line " + i+1 + " : " + jobFile.get(i).get(j+2) + " (startTime) has negative value. Change it" );
                                        }
                                    } catch (NumberFormatException eNumber) {
                                        escape = true;
                                        System.out.println("Line " + i+1 + " : " + jobFile.get(i).get(j+2) + " startTime is invalid type value. Change it." );
                                    }
                                    try {
                                        if (!(Integer.parseInt(jobFile.get(i).get(j+3)) <= 0)) {
                                            duration = Integer.parseInt(jobFile.get(i).get(j+3));
                                        } else {
                                            escape = true;
                                            System.out.println("Line " + i+1 + " : " + jobFile.get(i).get(j+3) + " (duration) has negative value. Change it" );
                                        }
                                    } catch (NumberFormatException eNumber) {
                                        escape = true;
                                        System.out.println("Line " + i+1 + " : " + jobFile.get(i).get(j+3) +  "  duration is invalid type value. Change it." );
                                    }
                                    jobFileList.add(new JobFile(jobFile.get(i).get(j), x , startTime, duration));
                                } else {
                                    escape = true;
                                    System.out.println("Line " + i+1 + " : " + jobFile.get(i).get(j+1) + " (JOBTYPE) is not in correct type. Change it." );
                                }
                            }
                        }
                    }
                } else {
                    //?
                }
            }
        }
    }

    // WORKFLOW PROCESS METHOD
    private void workflowProcess() {
        // TASKTYPES ARRAYS
        ArrayList<String> taskTypeControl = new ArrayList<String>();
        ArrayList<Integer> taskTypeLines = new ArrayList<Integer>();
        ArrayList<String> taskTypeStringLines = new ArrayList<String>();
        ArrayList<String> taskTypeString = new ArrayList<String>();

        // JOBTYPES ARRAYS
        ArrayList<String> jobTypeControl = new ArrayList<String>();
        ArrayList<ArrayList<Task>> jobTaskList = new ArrayList<>();
        ArrayList<Integer> jobTypeLines = new ArrayList<Integer>();
        ArrayList<String> jobTypeStringLines = new ArrayList<String>();
        ArrayList<String> jobTypeString = new ArrayList<String>();

        // STATIONS ARRAYS
        ArrayList<ArrayList<StationType>> stationSpeedList = new ArrayList<>();
        ArrayList<String> stationTypeControl = new ArrayList<String>();
        ArrayList<Integer> stationTypeLines = new ArrayList<Integer>();
        ArrayList<String> stationTypeStringLines = new ArrayList<String>();
        ArrayList<String> stationTypeString = new ArrayList<>();

        //------------------------------- TASKTYPES --------------------------------------

        boolean taskStart = false;

        for(int i = 0; i < txtList.size(); i++) {
            if (txtList.get(i).contains("JOBTYPES")) {
                break;
            }
            if(txtList.get(i).contains("TASKTYPES")) {
                taskStart = true;
            }
            if(taskStart) {
                taskTypeLines.add(i+1);
                taskTypeStringLines.add(txtList.get(i));
            }
        }
        for(int i = 0; i < taskTypeStringLines.size(); i++) {
            String[] taskTypeWords = taskTypeStringLines.get(i).split(" ");
            for(String j : taskTypeWords) {
                taskTypeString.add(j.trim());
            }
        }
        Iterator<String> taskTypeIterator = taskTypeString.iterator();
        while(taskTypeIterator.hasNext()) {
            String taskTypeElement = taskTypeIterator.next();
            if(taskTypeElement.isEmpty()) {
                taskTypeIterator.remove();
            }
        }

        for(int i = 0; i < taskTypeString.size(); i++) {
            if(taskTypeString.get(i).equals("(TASKTYPES")) {
                taskTypeString.remove(i);
            }
            if(taskTypeString.get(i).contains("(")) {
                if(taskTypeString.get(i).length() == 1) {
                    taskTypeString.remove(i);
                }else {
                    StringBuilder buffer = new StringBuilder();
                    buffer.insert(0,taskTypeString.get(i));
                    if(buffer.indexOf("(") != -1) {
                        buffer.deleteCharAt(buffer.indexOf("("));
                    }
                    taskTypeString.set(i,buffer.toString());
                }
            }
            if(taskTypeString.get(i).contains(")")) {
                if(taskTypeString.get(i).length() == 1) {
                    taskTypeString.remove(i);
                }else {
                    StringBuilder buffer = new StringBuilder();
                    buffer.insert(0,taskTypeString.get(i));
                    if(buffer.indexOf(")") != -1) {
                        buffer.deleteCharAt(buffer.indexOf(")"));
                    }
                    taskTypeString.set(i,buffer.toString());
                }
            }
        }

        for (int i = 0; i < taskTypeString.size(); i++) {
            if (Character.isLetter(taskTypeString.get(i).charAt(0))) {
                if (taskTypeString.get(i) != taskTypeString.get(taskTypeString.size() - 1)) {
                    if (!Character.isDigit(taskTypeString.get(i + 1).charAt(0))) {
                        taskTypesList.add(new TaskType(taskTypeString.get(i)));
                        taskTypeControl.add(taskTypeString.get(i));
                    } else {
                        try {
                            if(Double.parseDouble(taskTypeString.get(i + 1)) < 0 ) {
                                System.out.println(taskTypeString.get(i) + "has a negative task size" + taskTypeString.get(i+1));
                                escape = true;
                            }else {
                                taskTypesList.add(new TaskType(taskTypeString.get(i), Double.parseDouble(taskTypeString.get(i + 1))));
                                taskTypeControl.add(taskTypeString.get(i));
                                taskTypeControl.add(taskTypeString.get(i+1));
                            }
                        } catch(NumberFormatException eNumber) {
                            if(taskTypeLines.getFirst().equals(taskTypeLines.getLast())) {
                                System.out.print("Line " + taskTypeLines.getFirst() + " : ");
                            } else {
                                System.out.print("Line " + taskTypeLines.getFirst() + "-" + taskTypeLines.getLast() + " : ");
                            }
                            escape = true;
                            System.out.print(taskTypeString.get(i) + " your input size is incorrect (" + taskTypeString.get(i + 1) + ") is wrong. Enter correct version.");
                            System.out.println();
                        } catch(Exception e) {
                            if(taskTypeLines.getFirst().equals(taskTypeLines.getLast())) {
                                System.out.print("Line " + taskTypeLines.getFirst() + " : ");
                            } else {
                                System.out.print("Line " + taskTypeLines.getFirst() + "-" + taskTypeLines.getLast() + " : ");
                            }
                            escape = true;
                            System.err.print(e);
                            System.out.println();
                        }
                    }
                } else {
                    taskTypeControl.add(taskTypeString.get(i));
                    taskTypesList.add(new TaskType(taskTypeString.get(i)));
                }
            }
        }
        for (int i = 0; i < taskTypeString.size(); i++) {
            if(!taskTypeString.get(i).equals("TASKTYPES")) {
                if(!taskTypeControl.contains(taskTypeString.get(i))) {
                    if(taskTypeLines.getFirst().equals(taskTypeLines.getLast())) {
                        System.out.print("Line " + taskTypeLines.getFirst() + " : ");
                    } else {
                        System.out.print("Line " + taskTypeLines.getFirst() + "-" + taskTypeLines.getLast() + " : ");
                    }
                    try {
                        if(Double.parseDouble(taskTypeString.get(i)) < 0) {
                            if(Character.isLetter(taskTypeString.get(i-1).charAt(0))) {
                                escape = true;
                                System.out.print(taskTypeString.get(i-1) + " has a negative task size " + taskTypeString.get(i));
                            } else {
                                escape = true;
                                System.out.print(taskTypeString.get(i) + " is an invalid input. Change it");
                            }
                        } else {
                            escape = true;
                            System.out.print(taskTypeString.get(i) + " is an invalid input. Change it");
                        }
                    } catch (NumberFormatException eNumber) {
                        escape = true;
                        System.out.print(taskTypeString.get(i) + " is an invalid tasktypeID.");

                    }
                    System.out.println();
                }
            }
        }
        int taskTemp=-1;
        for(int i = 0; i < taskTypesList.size(); i++) {
            for(int j = 0; j < taskTypesList.size(); j++) {
                if(taskTemp!=i){
                    if(i != j) {
                        if (taskTypesList.get(i).getTaskID().equals(taskTypesList.get(j).getTaskID())) {
                            if(taskTypeLines.getFirst().equals(taskTypeLines.getLast())) {
                                System.out.print("Line " + taskTypeLines.getFirst() + " : ");
                            } else {
                                System.out.print("Line " + taskTypeLines.getFirst() + "-" + taskTypeLines.getLast() + " : ");
                            }
                            escape = true;
                            System.out.print(taskTypesList.get(i).getTaskID() + " is listed twice.");
                            System.out.println();
                            taskTemp=j;
                        }
                    }
                }
            }
        }

        //------------------------------- JOBTYPES --------------------------------------

        boolean jobStart = false;
        boolean jobResume = false;
        int jobIndex = 0;

        for (int i = 0; i < txtList.size(); i++) {
            if(txtList.get(i).contains("STATIONS")) {
                break;
            }
            if(txtList.get(i).contains("JOBTYPES")) {
                jobStart = true;
            }
            if(jobStart) {
                jobTypeLines.add(i+1);
                jobTypeStringLines.add(txtList.get(i));
            }
        }
        for(int i = 0; i < jobTypeStringLines.size(); i++) {
            String[] jobTypeWords = jobTypeStringLines.get(i).split(" ");
            for(String j : jobTypeWords) {
                jobTypeString.add(j.trim());
            }
        }
        Iterator<String> jobTypeIterator = jobTypeString.iterator();
        while(jobTypeIterator.hasNext()) {
            String jobTypeElement = jobTypeIterator.next();
            if(jobTypeElement.isEmpty()) {
                jobTypeIterator.remove();
            }
        }
        ArrayList<ArrayList<String>> jobs = new ArrayList<>();
        for(int i = 0; i < jobTypeString.size() ; i++) {
            if(jobTypeString.get(i).equals("(JOBTYPES")) {
                jobTypeString.remove(i);
            }
            if(jobTypeString.get(i).startsWith("(") && Character.isLetter(jobTypeString.get(i).charAt(1))) {
                jobs.add(new ArrayList());
                jobResume = true;
            }
            if(jobResume) {
                jobs.get(jobIndex).add(jobTypeString.get(i));
                if(jobTypeString.get(i).endsWith(")")) {
                    jobIndex++;
                    jobResume = false;
                }
            }
        }
        for(int i = 0; i < jobs.size(); i++) {
            for(int j = 0; j < jobs.get(i).size(); j++){
                if(jobs.get(i).get(j).startsWith("(")) {
                    if(jobs.get(i).get(j).length() == 1) {
                        jobs.get(i).remove(j);
                    }else {
                        StringBuilder buffer = new StringBuilder();
                        buffer.insert(0,jobs.get(i).get(j));
                        if(buffer.indexOf("(") != -1) {
                            buffer.deleteCharAt(buffer.indexOf("("));
                        }
                        jobs.get(i).set(j,buffer.toString());
                    }
                }
                if(jobs.get(i).get(j).endsWith(")")) {
                    if(jobs.get(i).get(j).length() == 1) {
                        jobs.get(i).remove(j);
                    }else {
                        StringBuilder buffer = new StringBuilder();
                        buffer.insert(0,jobs.get(i).get(j));
                        if(buffer.indexOf(")") != -1) {
                            buffer.deleteCharAt(buffer.indexOf(")"));
                        }
                        jobs.get(i).set(j,buffer.toString());
                    }
                }
            }
        }
        for(int i = 0; i < jobs.size(); i++) {
            jobTaskList.add(new ArrayList());
            for(int j = 1; j < jobs.get(i).size(); j++){
                if(Character.isLetter(jobs.get(i).get(j).charAt(0))) {
                    if(jobs.get(i).get(j) != jobs.get(i).get(jobs.get(i).size() - 1)) {
                        if(Character.isDigit(jobs.get(i).get(j+1).charAt(0))){
                            for(TaskType x : taskTypesList) {
                                if(jobs.get(i).get(j).equals(x.getTaskID())) {
                                    try{
                                        if(x.getDefualtSize() <= 0 && Double.parseDouble(jobs.get(i).get(j+1)) <= 0) {
                                            escape = true;
                                            System.out.print(x.getTaskID() +" has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job. ");
                                            System.out.println();
                                        } else {
                                            jobTaskList.get(i).add(new Task(x,Double.parseDouble(jobs.get(i).get(j+1))));
                                            jobTypeControl.add(jobs.get(i).get(j));
                                            jobTypeControl.add(jobs.get(i).get(j+1));
                                        }
                                    } catch(NumberFormatException eNumber) {
                                        if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                                            System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                                        } else {
                                            try {
                                                System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                                            } catch(IndexOutOfBoundsException e) {
                                                System.out.print("Line " + jobTypeLines.get(i-1) + " : ");
                                            }
                                        }
                                        escape = true;
                                        System.out.print(jobs.get(i).get(j) + " your input size is incorrect (" + jobs.get(i).get(j+1) + ") is wrong. Enter correct version.");
                                        System.out.println();
                                    } catch(Exception e) {
                                        if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                                            System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                                        } else {
                                            try {
                                                System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                                            } catch(IndexOutOfBoundsException IndexE) {
                                                System.out.print("Line " + jobTypeLines.get(i-1) + " : ");
                                            }
                                        }
                                        escape = true;
                                        System.err.print(jobs.get(i).get(0) + " => " + e);
                                        System.out.println();
                                    }
                                }
                            }
                        }else {
                            for(TaskType x : taskTypesList) {
                                if(jobs.get(i).get(j).equals(x.getTaskID())) {
                                    if(x.getDefualtSize() <= 0) {
                                        if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                                            System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                                        } else {
                                            try {
                                                System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                                            } catch(IndexOutOfBoundsException IndexE) {
                                                System.out.print("Line " + jobTypeLines.get(i-1) + " : ");
                                            }
                                        }
                                        escape = true;
                                        System.out.print(x.getTaskID() +" has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job. ");
                                        System.out.println();
                                    } else {
                                        jobTaskList.get(i).add(new Task(x));
                                        jobTypeControl.add(jobs.get(i).get(j));
                                    }
                                }
                            }
                        }
                    }else {
                        for(TaskType x : taskTypesList) {
                            if(jobs.get(i).get(j).equals(x.getTaskID())) {
                                if(x.getDefualtSize() <= 0) {
                                    if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                                        System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                                    } else {
                                        try {
                                            System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                                        } catch(IndexOutOfBoundsException IndexE) {
                                            System.out.print("Line " + jobTypeLines.get(i-1) + " : ");
                                        }
                                    }
                                    escape = true;
                                    System.out.print(x.getTaskID() +" has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job. ");
                                    System.out.println();
                                } else {
                                    jobTaskList.get(i).add(new Task(x));
                                    jobTypeControl.add(jobs.get(i).get(j));
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i < jobTaskList.size(); i++) {
            jobTypeControl.add(jobs.get(i).get(0));
            jobTypeList.add(new JobType(jobs.get(i).get(0), jobTaskList.get(i)));
        }

        for (int i = 0; i < jobs.size(); i++) {
            for(int j = 0; j < jobs.get(i).size(); j++) {
                if(!jobTypeControl.contains(jobs.get(i).get(j))) {
                    if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                        System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                    } else {
                        try {
                            System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                        } catch(IndexOutOfBoundsException e) {
                            System.out.print("Line " + jobTypeLines.get(i-1) + " : ");
                        }
                    }
                    try {
                        if(Double.parseDouble(jobs.get(i).get(j)) <= 0) {
                            if(Character.isLetter(jobs.get(i).get(j-1).charAt(0))) {
                                escape = true;
                                System.out.print(jobs.get(i).get(j -1) + " has a negative task size of " + jobs.get(i).get(j));
                            } else {
                                escape = true;
                                System.out.print(jobs.get(i).get(j) + " is invalid input. Delete it.");
                            }
                        }else {
                            if(Character.isLetter(jobs.get(i).get(j-1).charAt(0))) {
                                escape = true;
                                System.out.print(jobs.get(i).get(j) + " not declered because of " + jobs.get(i).get(j-1));
                            } else {
                                escape = true;
                                System.out.print(jobs.get(i).get(j) + " is invalid input. Delete it.");
                            }
                        }
                    } catch (NumberFormatException eNumber) {
                        escape = true;
                        System.out.print(jobs.get(i).get(j) + " is not one of the declared task types.");
                    }
                    System.out.println();
                }
            }
        }
        int jobTemp=-1;
        for(int i = 0; i < jobTypeList.size(); i++) {
            for(int j = 0; j < jobTypeList.size(); j++) {
                if(jobTemp!=i){
                     if (i != j) {
                        if (jobTypeList.get(i).getJobID().equals(jobTypeList.get(j).getJobID())) {
                            if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                                System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                            } else {
                                try {
                                    System.out.print("Line " + jobTypeLines.get(i+1) + " : ");
                                } catch(IndexOutOfBoundsException e) {
                                    System.out.print("Line " + jobTypeLines.get(i) + " : ");
                                }
                            }
                            escape = true;
                            System.out.print("One JobType should be declared once. (" + jobTypeList.get(i).getJobID() + ") => Line " + jobTypeLines.get(j+1));
                            System.out.println();
                            jobTemp=j;
                        }
                    }
                }
            }
        }

        //------------------------------- STATIONS --------------------------------------

        boolean stationStart = false;
        boolean stationResume = false;
        int stationIndex = 0;

        for(int i = 0; i < txtList.size(); i ++) {
            if(txtList.get(i).contains("STATIONS")) {
                stationStart = true;
            }
            if(stationStart) {
                stationTypeLines.add(i+1);
                stationTypeStringLines.add(txtList.get(i));
            }
        }
        for(int i = 0; i < stationTypeStringLines.size(); i++) {
            String[] stationTypeWords = stationTypeStringLines.get(i).split(" ");
            for(String j : stationTypeWords) {
                stationTypeString.add(j.trim());
            }
        }
        Iterator<String> stationTypeIterator = stationTypeString.iterator();
        while(stationTypeIterator.hasNext()) {
            String stationTypeElement = stationTypeIterator.next();
            if(stationTypeElement.isEmpty()) {
                stationTypeIterator.remove();
            }
        }
        ArrayList<ArrayList<String>> stations = new ArrayList<>();
        for(int i = 0; i<stationTypeString.size(); i++) {
            if(stationTypeString.get(i).equals("(STATIONS")) {
                stationTypeString.remove(i);
            }
            if(stationTypeString.get(i).startsWith("(") && Character.isLetter(stationTypeString.get(i).charAt(1))) {
                stations.add(new ArrayList<>());
                stationResume = true;
            }
            if(stationResume) {
                stations.get(stationIndex).add(stationTypeString.get(i));
                if(stationTypeString.get(i).endsWith(")")) {
                    stationIndex++;
                    stationResume = false;
                }
            }
        }

        for(int i = 0; i < stations.size(); i++) {
            for(int j = 0; j < stations.get(i).size(); j++) {
                if(stations.get(i).get(j).startsWith("(")) {
                    if(stations.get(i).get(j).length() == 1) {
                        stations.get(i).remove(j);
                    }else {
                        StringBuilder buffer = new StringBuilder();
                        buffer.insert(0, stations.get(i).get(j));
                        if(buffer.indexOf("(") != -1) {
                            buffer.deleteCharAt(buffer.indexOf("("));
                        }
                        stations.get(i).set(j,buffer.toString());
                    }
                }
                if(stations.get(i).get(j).endsWith(")")) {
                    if(stations.get(i).get(j).length() == 1) {
                        stations.get(i).remove(j);
                    }else {
                        StringBuilder buffer = new StringBuilder();
                        buffer.insert(0, stations.get(i).get(j));
                        if(buffer.indexOf(")") != -1) {
                            buffer.deleteCharAt(buffer.indexOf(")"));
                        }
                        stations.get(i).set(j,buffer.toString());
                    }
                }
            }
        }
        for(int i = 0 ; i < stations.size(); i++) {
            stationSpeedList.add(new ArrayList<>());
            int counter = 4;
            if (Character.isLetter(stations.get(i).get(3).charAt(0))) {
                if(stations.get(i).get(1).equalsIgnoreCase("Y") || stations.get(i).get(1).equalsIgnoreCase("N")) {
                    if(stations.get(i).get(2).equalsIgnoreCase("Y") || stations.get(i).get(2).equalsIgnoreCase("N")) {
                        counter = 3;
                    }
                }
            }
            for(int j = counter; j < stations.get(i).size(); j++) {
                if(Character.isLetter(stations.get(i).get(j).charAt(0))) {
                    if (stations.get(i).get(j) != stations.get(i).get(stations.get(i).size() - 1)) {
                        if (stations.get(i).get(j) != stations.get(i).get(stations.get(i).size() - 2)) {
                            if (Character.isDigit(stations.get(i).get(j + 1).charAt(0))) {
                                if (Character.isDigit(stations.get(i).get(j + 2).charAt(0))) {
                                    for(TaskType x : taskTypesList) {
                                        if(stations.get(i).get(j).equals(x.getTaskID())) {
                                            try {
                                                stationSpeedList.get(i).add(new StationType(x, Double.parseDouble(stations.get(i).get(j+1)),Double.parseDouble(stations.get(i).get(j+2))));
                                                stationTypeControl.add(stations.get(i).get(j));
                                                stationTypeControl.add(stations.get(i).get(j+1));
                                                stationTypeControl.add(stations.get(i).get(j+2));
                                            } catch(NumberFormatException eNumber) {
                                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                                } else {
                                                    try {
                                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                    } catch(IndexOutOfBoundsException e) {
                                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                    }
                                                }
                                                escape = true;
                                                System.out.print(stations.get(i).get(j) + " your input size is incorrect (" + stations.get(i).get(j+1) + ", "+stations.get(i).get(j+2)  +") is wrong. Enter correct version.");
                                                System.out.println();
                                            } catch(Exception e) {
                                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                                } else {
                                                    try {
                                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                    } catch(IndexOutOfBoundsException IndexE) {
                                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                    }
                                                }
                                                escape = true;
                                                System.out.print(stations.get(i).get(j) + " => " + e);
                                                System.out.println();
                                            }

                                        }
                                    }
                                }else {
                                    for(TaskType x : taskTypesList) {
                                        if(stations.get(i).get(j).equals(x.getTaskID())) {
                                            try {
                                                stationSpeedList.get(i).add(new StationType(x, Double.parseDouble(stations.get(i).get(j+1))));
                                                stationTypeControl.add(stations.get(i).get(j));
                                                stationTypeControl.add(stations.get(i).get(j+1));
                                            } catch(NumberFormatException eNumber) {
                                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                                } else {
                                                    try {
                                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                    } catch(IndexOutOfBoundsException e) {
                                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                    }
                                                }
                                                escape = true;
                                                System.out.print(stations.get(i).get(j) + " your input size is incorrect (" + stations.get(i).get(j+1) + ") is wrong. Enter correct version.");
                                                System.out.println();
                                            } catch (Exception e) {
                                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                                } else {
                                                    try {
                                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                    } catch(IndexOutOfBoundsException IndexE) {
                                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                    }
                                                }
                                                escape = true;
                                                System.out.print(stations.get(i).get(j) + " => " + e);
                                                System.out.println();
                                            }
                                        }
                                    }
                                }
                            }else {
                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                } else {
                                    try {
                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                    } catch(IndexOutOfBoundsException e) {
                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                    }
                                }
                                escape = true;
                                System.out.print(stations.get(i).get(j) + " your input size is empty. Enter value => (speed) - (speed, plusMinus);");
                                System.out.println();
                            }
                        } else {
                            if (Character.isDigit(stations.get(i).get(j + 1).charAt(0))) {
                                for(TaskType x : taskTypesList) {
                                    if(stations.get(i).get(j).equals(x.getTaskID())) {
                                        try {
                                            stationSpeedList.get(i).add(new StationType(x, Double.parseDouble(stations.get(i).get(j+1))));
                                            stationTypeControl.add(stations.get(i).get(j));
                                            stationTypeControl.add(stations.get(i).get(j+1));
                                        } catch(NumberFormatException eNumber) {
                                            if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                            } else {
                                                try {
                                                    System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                } catch(IndexOutOfBoundsException e) {
                                                    System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                }
                                            }
                                            escape = true;
                                            System.out.print(stations.get(i).get(j) + " your input size is incorrect (" + stations.get(i).get(j+1) + ") is wrong. Enter correct version.");
                                            System.out.println();
                                        } catch (Exception e) {
                                            if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                                System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                            } else {
                                                try {
                                                    System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                                } catch(IndexOutOfBoundsException IndexE) {
                                                    System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                                }
                                            }
                                            escape = true;
                                            System.out.print(stations.get(i).get(j) + " => " + e);
                                            System.out.println();
                                        }
                                    }
                                }
                            }else {
                                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                                } else {
                                    try {
                                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                    } catch(IndexOutOfBoundsException e) {
                                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                    }
                                }
                                escape = true;
                                System.out.print(stations.get(i).get(j) + " your input size is empty. Enter value => (speed) - (speed, plusMinus);");
                                System.out.println();
                            }
                        }
                    } else {
                        if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                            System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                        } else {
                            try {
                                System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                            } catch(IndexOutOfBoundsException e) {
                                System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                            }
                        }
                        escape = true;
                        System.out.print(stations.get(i).get(j) + " your input size is empty. Enter value => (speed) - (speed, plusMinus);");
                        System.out.println();
                    }
                }
            }
        }
        boolean multiFlag = false;
        boolean fifoFlag = false;
        int maxCapacity = -1;
        boolean maxCapacityStatus = true;
        for(int i = 0; i < stationSpeedList.size(); i++) {
            int multiFlagIndex = 2;
            int fifoFlagIndex = 3;
            stationTypeControl.add(stations.get(i).get(0));
            try {
                if(Integer.parseInt(stations.get(i).get(1)) <= 0) {
                    if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                        System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                    } else {
                        try {
                            System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                        } catch(IndexOutOfBoundsException e) {
                            System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                        }
                    }
                    escape = true;
                    System.out.print(stations.get(i).get(1) + " your MaxCapacity size is negative, enter correct version.");
                    System.out.println();
                } else {
                    maxCapacity = Integer.parseInt(stations.get(i).get(1));
                }
            }catch (NumberFormatException eNumber) {
                if (stations.get(i).get(1).equalsIgnoreCase("Y") || stations.get(i).get(1).equalsIgnoreCase("N")) {
                    maxCapacityStatus = false;
                    multiFlagIndex = 1;
                    fifoFlagIndex = 2;
                } else {
                    if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                        System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                    } else {
                        try {
                            System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                        } catch(IndexOutOfBoundsException e) {
                            System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                        }
                    }
                    escape = true;
                    System.out.print("maxCapacity "+stations.get(i).get(1) + " your input size is incorrect is wrong. Enter integer value.");
                    System.out.println();
                }
            }
            if(stations.get(i).get(multiFlagIndex).equalsIgnoreCase("Y") || stations.get(i).get(multiFlagIndex).equalsIgnoreCase("N")) {
                if (stations.get(i).get(multiFlagIndex).equalsIgnoreCase("Y")) {
                    multiFlag = true;
                } else {
                    multiFlag = false;
                }
            } else {
                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                } else {
                    try {
                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                    } catch(IndexOutOfBoundsException e) {
                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                    }
                }
                escape = true;
                System.out.print(stations.get(i).get(2) + " your MULTIFLAG input is incorrect enter (Y|N)");
                System.out.println();
            }
            if(stations.get(i).get(fifoFlagIndex).equalsIgnoreCase("Y") || stations.get(i).get(fifoFlagIndex).equalsIgnoreCase("N")) {
                if(stations.get(i).get(fifoFlagIndex).equalsIgnoreCase("Y")) {
                    fifoFlag = true;
                } else {
                    fifoFlag = false;
                }
            } else {
                if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                    System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                } else {
                    try {
                        System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                    } catch(IndexOutOfBoundsException e) {
                        System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                    }
                }
                escape = true;
                System.out.print(stations.get(i).get(3) + " your FIFOFLAG input is incorrect enter (Y|N)");
                System.out.println();
            }
            if(maxCapacityStatus) {
                stationList.add(new Station(stations.get(i).get(0), maxCapacity, multiFlag , fifoFlag, stationSpeedList.get(i)));
                stationTypeControl.add(stations.get(i).get(3));
            } else {
                stationList.add(new Station(stations.get(i).get(0), multiFlag, fifoFlag, stationSpeedList.get(i)));
                stationTypeControl.add(stations.get(i).get(1));
                stationTypeControl.add(stations.get(i).get(2));
            }
        }
        for(int i = 0; i < stations.size(); i++) {
            for(int j = 0; j < stations.get(i).size();j++) {
                if(!stationTypeControl.contains(stations.get(i).get(j))) {
                    if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                        System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                    } else {
                        try {
                            System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                        } catch(IndexOutOfBoundsException e) {
                            System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                        }
                    }
                    escape = true;
                    try {
                        if(Double.parseDouble(stations.get(i).get(j)) <= 0) {
                            if(Character.isLetter(stations.get(i).get(j-1).charAt(0))) {
                                System.out.print(stations.get(i).get(j -1) + " has a negative speed size. ");
                            } else if (Character.isLetter(stations.get(i).get(j-2).charAt(0))) {
                                System.out.print(stations.get(i).get(j -1) + " has a negative plusMinus size. ");
                            }else {
                                System.out.print(stations.get(i).get(j) + " is invalid input. Delete it.");
                            }
                        } else {
                            if(Character.isLetter(stations.get(i).get(j-1).charAt(0))) {
                                System.out.print(stations.get(i).get(j) + " not declered because of " + stations.get(i).get(j-1));
                            } else if (Character.isLetter(stations.get(i).get(j-2).charAt(0))) {
                                System.out.print(stations.get(i).get(j) + " not declered because of " + stations.get(i).get(j-2));
                            }else {
                                System.out.print(stations.get(i).get(j) + " is invalid input. Delete it.");
                            }
                        }
                    } catch(NumberFormatException eNumber){
                        System.out.print(stations.get(i).get(j) + " is not one of the declared task types.");
                    }
                    System.out.println();
                }
            }
        }
        int stationTemp = -1;
        for(int i =0; i < stationList.size(); i++) {
            for(int j = 0; j <stationList.size(); j++) {
                if(stationTemp != i) {
                    if (i != j) {
                        if (stationList.get(i).getStationID().equals(stationList.get(j).getStationID())) {
                            if(stationTypeLines.getFirst().equals(stationTypeLines.getLast())) {
                                System.out.print("Line " + stationTypeLines.getFirst() + " : ");
                            } else {
                                try {
                                    System.out.print("Line " + stationTypeLines.get(i+1) + " : ");
                                } catch(IndexOutOfBoundsException e) {
                                    System.out.print("Line " + stationTypeLines.get(i-1) + " : ");
                                }
                            }
                            escape = true;
                            System.out.print("One Station should be declared once. (" + stationList.get(i).getStationID() + ") => Line " + stationTypeLines.get(j+1));
                            System.out.println();
                            stationTemp=j;
                        }
                    }
                }
            }
        }

        for (int j = 0; j < jobTypeList.size(); j++) {
            for(Task t : jobTypeList.get(j).getTaskList()) {
                boolean isTaskAvaliable = false;
                for(int i =0; i < stationList.size(); i++) {
                    for (StationType stationType : stationList.get(i).getStationTypeList()) {
                        if(t.getTaskID().equals(stationType.getTaskID())) {
                            isTaskAvaliable = true;
                        }
                    }
                }
                if(!isTaskAvaliable) {
                    if(jobTypeLines.getFirst().equals(jobTypeLines.getLast())) {
                        System.out.print("Line " + jobTypeLines.getFirst() + " : ");
                    } else {
                        try {
                            System.out.print("Line " + jobTypeLines.get(j+1) + " : ");
                        } catch(IndexOutOfBoundsException e) {
                            System.out.print("Line " + jobTypeLines.get(j-1) + " : ");
                        }
                    }
                    escape = true;
                    System.out.print(t.getTaskID() + " Task is not declared in stations. Change or add it." );
                    System.out.println();
                }
            }
        }

    }

    // TASKTYPES PRINT
    public void taskTypesPrint() {
        System.out.println();
        System.out.println(fileName);
        System.out.println("TaskTypes");
        for (TaskType i : taskTypesList) {
            System.out.println(i.getTaskID() + " -- " + i.getDefualtSize());
        }
        System.out.println();
    }

    // JOBTYPES PRINT
    public void jobTypesPrint() {
        System.out.println();
        System.out.println(fileName);
        System.out.println("JobTypes");
        for(int i = 0; i < jobTypeList.size(); i++) {
            System.out.print(jobTypeList.get(i).getJobID() + " - ");
            for (Task x : jobTypeList.get(i).getTaskList()) {
                System.out.print("(" + x.getTaskID() + ", " + x.getDefualtSize() + ", " + x.getSize() + ")  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // STATIONS PRINT
    public void stationsPrint() {
        System.out.println();
        System.out.println(fileName);
        System.out.println("Stations");
        for(Station x: stationList) {
            System.out.print(x.getStationID()+ " ");
            System.out.print(x.getMaxCapacity()+ " ");
            System.out.print(x.isMultiFlag()+ " ");
            System.out.print(x.isFifoFlag()+ " ");
            for(StationType y : x.getStationTypeList()) {
                System.out.print(y.getTaskID() + " ");
                System.out.print(y.getSpeed() + " ");
                System.out.print(y.getPlusMinus() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // JOBFILE PRINT
    public void jobFilePrint() {
        System.out.println();
        System.out.println(fileName);
        for (JobFile x : jobFileList) {
            System.out.print(x.getJobName() + " ");
            System.out.print(x.getJobType().getJobID() + " ");
            System.out.print(x.getStartTime() + " ");
            System.out.print(x.getDuration() + " ");
            System.out.println();
        }
        System.out.println();
    }

    // RETURN ERROR REPORTS
    public boolean getEscape() {return escape;}

    // OPEN FILE
    private void openFile(String txt) {
        try {
            fileName = txt;
            input = new Scanner(Paths.get(txt));
        } catch(IOException ioException) {
            System.err.println("Error opening file. Terminating.");
            System.out.println(ioException);
            System.exit(1);
        }
    }

    // READ FILE
    private static void readFile(int choice) {
        try{
            switch (choice) {
                // reads one by one
                case 1:
                    while(input.hasNext()) {
                        txtList.add(input.next());
                    }
                    break;
                // reads line by line
                case 2:
                    while(input.hasNextLine()) {
                        txtList.add(input.nextLine());
                    }
                    break;
            }

        } catch(NoSuchElementException elementException) {
            System.err.println("File improperly formed. Terminating.");
            System.exit(1);
        } catch(IllegalStateException stateException) {
            System.err.println("Error reading from file. Terminating.");
            System.exit(1);
        }
    }

    // CLOSE FILE
    public static void closeFile() {
        if(input != null) {
            input.close();
        }
    }
}
