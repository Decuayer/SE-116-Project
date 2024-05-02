public class Task extends TaskType{
    private TaskType taskType;
    private double size;

    public Task() {
        //Herhangi bir parametre girilmediği zaman uyarı verecek try catch.
    }


    public Task(TaskType taskType) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        // defualtSize var mı yok mu kontrol eden try catch. Eğer defualtSize yoksa uyarı verecek.
        if(getDefualtSize() == 0) {

        }
        //--
        setTaskType(taskType);
    }


    public Task(TaskType taskType, double size) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        setTaskType(taskType);
        setSize(size);
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public void setSize(double size) {
        // size negatif mi değil mi kontrol edecek try catch.
        // size variable type doğru mu kontrol eden  try catch.
        this.size = size;
    }

    public TaskType getTaskType() {return taskType;}

    public double getSize() {
        if (size == 0) {
            return getDefualtSize();
        }
        return size;
    }

}
