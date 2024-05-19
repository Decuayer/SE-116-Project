 public class TaskType {
    private String taskID;
    private double defualtsize;

    public TaskType() {}

    public TaskType(String taskID){
        setTaskID(taskID);
        setDefualtsize(0);
    }

    public TaskType(String taskID, double defualtsize) {
        setTaskID(taskID);
        setDefualtsize(defualtsize);
    }

    public void setTaskID(String taskID) {this.taskID=taskID;}
    public void setDefualtsize(double defualtsize) {
        try {
            if (defualtsize < 0) {
                throw new IllegalArgumentException("Error: (size) cannot be negative.");
            }
            this.defualtsize = defualtsize;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid data type for (defaultsize).");
        }
    }

    public double getDefualtSize() {return defualtsize;}
    public String getTaskID() {return taskID;}
}
