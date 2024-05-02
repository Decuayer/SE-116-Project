public class StationType extends TaskType{
    private TaskType taskType;
    private double speed;
    private double plusMinus;
//CÜCÜ ADAM

    public StationType() {

    }

    public StationType(TaskType taskType, double speed) {
        setTaskType(taskType);
        setSpeed(speed);
        setPlusMinus(0);
    }

    public StationType(TaskType taskType, double speed, double plusMinus) {
        setTaskType(taskType);
        setSpeed(speed);
        setPlusMinus(plusMinus);
    }

    public void setTaskType(TaskType taskType) {
        // belirtilen tasktype var mı kontrol eden try catch. Aynı anda birden fazlasını yakalayabilmesi lazım.
        this.taskType = taskType;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPlusMinus(double plusMinus) {
        this.plusMinus = plusMinus;
    }

    public TaskType getTaskType() {return taskType;}
    public double getSpeed() {return speed;}
    public double getPlusMinus() {return plusMinus;}


    // verilen speed ve plusMinus değerine göre aralıktaki rastgele bir hız değeri verecek fonksiyon.

}
