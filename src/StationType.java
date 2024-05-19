import java.util.Random;
public class StationType extends TaskType {
    private TaskType taskType;
    private double speed;
    private double plusMinus;

    public StationType() {}

    public StationType(TaskType taskType, double speed) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        setSpeed(speed);
        setPlusMinus(0);
    }

    public StationType(TaskType taskType, double speed, double plusMinus) {
        super(taskType.getTaskID(), taskType.getDefualtSize());
        setSpeed(speed);
        setPlusMinus(plusMinus);
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public void setSpeed(double speed) {
        try {
            if (speed < 0) {
                throw new IllegalArgumentException("Error: (speed) value can't be negative.");
            }
            this.speed = speed;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void setPlusMinus(double plusMinus) {
        try {
            if (plusMinus < 0) {
                throw new IllegalArgumentException("Error: (plusMinus) value can't be negative.");
            }
            this.plusMinus = plusMinus;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

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

    public double getCalculatedSpeed(){
        Random random = new Random(System.currentTimeMillis());
        if(getPlusMinus() == 0) {
            return getSpeed();
        }
        double max = speed + (speed * plusMinus);
        double min = speed - (speed * plusMinus);
        return random.nextDouble(min,max);
    }
}
