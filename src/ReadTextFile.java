import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.lang.StringBuilder;


public class ReadTextFile {
    private static Scanner input;
    private int choice = -1;
    private static ArrayList<String> txtList = new ArrayList<String>();
    private static ArrayList<String> taskTypeString = new ArrayList<String>();
    private static ArrayList<String> taskTypeControl = new ArrayList<String>();
    private static ArrayList<TaskType> taskTypesList = new ArrayList<TaskType>();
    private static ArrayList<String> jobTypeString = new ArrayList<String>();
    private static ArrayList<ArrayList<Task>> jobTaskList = new ArrayList<>();
    private static ArrayList<ArrayList<JobType>> jobTypeList = new ArrayList<>();


    // 0 => workfflow.txt
    // 1 => jobfile.txt


    public ReadTextFile(String txt, int choice) {
        switch (choice) {
            case 0:
                openFile(txt);
                readWorkflow();
                closeFile();
                break;
            case 1:

                break;
        }
    }

    public static void main(String[] args) {
        openFile("workflow.txt");
        readWorkflow();
        closeFile();

        boolean taskStart = false;
        for (int i = 0; i < txtList.size(); i++) {
            //System.out.println(txtList.get(i));
            if (txtList.get(i).endsWith(")")) {
                StringBuilder buffer = new StringBuilder();
                buffer.insert(0, txtList.get(i));
                if (buffer.indexOf(")") != -1) {
                    buffer.deleteCharAt(buffer.indexOf(")"));
                }
                taskTypeString.add(buffer.toString());
                break;
            }
            if (txtList.get(i).startsWith("(TASKTYPES")) {
                taskStart = true;
            }
            if (taskStart) {
                taskTypeString.add(txtList.get(i));
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
                            taskTypesList.add(new TaskType(taskTypeString.get(i), Double.parseDouble(taskTypeString.get(i + 1))));
                            taskTypeControl.add(taskTypeString.get(i));
                            taskTypeControl.add(taskTypeString.get(i+1));
                        } catch(NumberFormatException eNumber) {
                            System.err.println(taskTypeString.get(i) + " your input size is incorrect (" + taskTypeString.get(i + 1) + ") is wrong. Enter correct version.");
                        } catch(Exception e) {
                            System.err.println(e);
                        }
                    }
                } else {
                    taskTypeControl.add(taskTypeString.get(i));
                    taskTypesList.add(new TaskType(taskTypeString.get(i)));
                }
            }
        }
        for (int i = 0; i < taskTypeString.size(); i++) {
            if(!taskTypeString.get(i).equals("(TASKTYPES")) {
                if(!taskTypeControl.contains(taskTypeString.get(i))) {
                    System.err.println(taskTypeString.get(i) + " is invalid input. Change it.");
                }
            }
        }


        for (TaskType i : taskTypesList) {
            System.out.println(i.getTaskID() + " -- " + i.getDefualtSize());
        }

        boolean jobStart = false;
        boolean jobRest = false;

        for (int i = 0; i < txtList.size(); i++) {
            if (txtList.get(i).startsWith("(JOBTYPES")) {
                jobStart = true;
            }
            if (jobStart) {
                if(txtList.get(i).startsWith("(")) {
                    jobRest = true;
                }
                if(jobRest) {
                    jobTypeString.add(txtList.get(i));
                    if(txtList.get(i).endsWith(")")) {
                        jobRest = false;
                    }
                }else {
                    break;
                }
            }
        }
        boolean jobResume = false;
        int jobCount = 0;
        int jobIndex = 0;
        ArrayList<ArrayList<String>> jobs = new ArrayList<>();
        for(int i = 0; i < jobTypeString.size() ; i++) {
            if(jobTypeString.get(i).equals("(JOBTYPES")) {
                jobTypeString.remove(i);
            }
            if(jobTypeString.get(i).startsWith("(") && Character.isLetter(jobTypeString.get(i).charAt(1))) {
                jobs.add(new ArrayList());
                jobCount++;
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
                System.out.println(i +"- " + jobs.get(i).get(j));
                if(Character.isLetter(jobs.get(i).get(j).charAt(0))) {
                    if(jobs.get(i).get(j) != jobs.get(i).get(jobs.get(i).size() - 1)) {
                        if(Character.isDigit(jobs.get(i).get(j+1).charAt(0))){
                            for(TaskType x : taskTypesList) {
                                if(jobs.get(i).get(j).equals(x.getTaskID())) {
                                    jobTaskList.get(i).add(new Task(x,Double.parseDouble(jobs.get(i).get(j+1))));
                                }
                            }
                        }else {
                            for(TaskType x : taskTypesList) {
                                if(jobs.get(i).get(j).equals(x.getTaskID())) {
                                    jobTaskList.get(i).add(new Task(x));
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i < jobTaskList.size(); i++) {
            jobTypeList.add(new ArrayList<>());

            for (int j = 0 ; j < jobTaskList.get(i).size(); j++) {
                System.out.println(jobTaskList.get(i).get(j).getTaskID());
                jobTypeList.get(i).add(new JobType(jobs.get(i).get(0), jobTaskList.get(i)));
            }
        }
        

        for(TaskType i : taskTypesList) {
            //System.out.println(i.getTaskID());
        }
        for(String i : jobTypeString) {
            //System.out.println(i);
        }
    }



    public static void openFile(String txt) {
        try {
            input = new Scanner(Paths.get("src/"+txt));
        } catch(IOException ioException) {
            System.err.println("Error opening file. Terminating.");
            System.out.println(ioException);
            System.exit(1);
        }
    }

    public static void readWorkflow() {

        try{
            while(input.hasNext()) {
                txtList.add(input.next());
            }
        } catch(NoSuchElementException elementException) {
            System.err.println("File improperly formed. Terminating.");
        } catch(IllegalStateException stateException) {
            System.err.println("Error reading from file. Terminating.");
        }
    }

    public static void closeFile() {
        if(input != null) {
            input.close();
        }
    }
}
