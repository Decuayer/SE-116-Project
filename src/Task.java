public class Task extends TaskType{
    private TaskType taskType;
    private double size;

    public Task() {
        //Herhangi bir parametre girilmediği zaman uyarı verecek try catch.

    }


    public Task(TaskType taskType) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        try{
            if(getDefualtSize() == 0.0) {
            throw new IllegalArgumentException("Error: Default size is not set.");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


    public Task(TaskType taskType, double size) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        setSize(size);
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public void setSize(double size) {
        // size negatif mi değil mi kontrol edecek try catch.
        // size variable type doğru mu kontrol eden  try catch.
        try {

            if (size < 0) {
                throw new IllegalArgumentException("Error: (size) cannot be negative.");
            }

            this.size = size;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid data type for (size).");
        }
    }

    public TaskType getTaskType() {return taskType;}

    public double getSize() {
        if (size == 0) {
            return getDefualtSize();
        }
        return size;
    }

}
