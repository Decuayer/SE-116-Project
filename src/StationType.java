public class StationType extends TaskType{
    private TaskType taskType;
    private double speed;
    private double plusMinus;

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
        // Speed değerinin negatif olup olmadığını kontrol eden try catch.
        this.speed = speed;
    }

    public void setPlusMinus(double plusMinus) {
        // plusMinus değeri negatif olup olmadığını kontrol eden try catch.
        this.plusMinus = plusMinus;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPlusMinus() {
        return plusMinus;
    }

    Random r=new Random();
    public double getCalculatedSpeed(){
        if(getPlusMinus() == 0) {
            return getSpeed();
        }
        double max=speed+(speed*plusMinus);
        double min=speed-(speed*plusMinus);
        return r.nextDouble(min,max);

    }



}
