public class TaskType {
    private String taskID;
    private double defualtsize;

    public TaskType() {
        //Herhangi bir parametre girilmediği zaman uyarı verecek try catch.
    }
    public TaskType(String taskID){
        setTaskID(taskID);
        setDefualtsize(0);
    }
    public TaskType(String taskID, double defualtsize) {
        setTaskID(taskID);
        setDefualtsize(defualtsize);
    }

    public void setTaskID(String taskID) {
        //taskID formatı doğru mu kontrol eden try catch. (T1 doğru / 1T yanlış)
        this.taskID = taskID;
    }
    public void setDefualtsize(double defualtsize) {
        // size negatif mi değil mi kontrol edecek try catch.
        // size variable type doğru mu kontrol eden  try catch.
        this.defualtsize = defualtsize;
    }

    public double getDefualtSize() {return defualtsize;}
    public String getTaskID() {return taskID;}
}
