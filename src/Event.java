public class Event implements Comparable<Event>{
    private double time;
    private String type; // "start", "complete"
    private JobFile jobFile;

    public Event(double time, String type, JobFile jobFile) {
        this.time = time;
        this.type = type;
        this.jobFile = jobFile;
    }

    public double getTime() {return time;}
    public String getType() {return type;}
    public JobFile getJob() {return jobFile;}

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}
