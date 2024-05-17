public class Event implements Comparable<Event> {
    private int time;
    private String type; // start, complete
    private JobFile jobFile;

    public Event(int time, String type, JobFile jobFile) {
        setTime(time);
        setType(type);
        setJobFile(jobFile);
    }

    public int getTime() {return time;}
    public String getType() {return type;}
    public JobFile getJobFile() {return jobFile;}

    public void setTime(int time) {this.time = time;}
    public void setType(String type) {this.type = type;}
    public void setJobFile(JobFile jobFile) {this.jobFile = jobFile;}

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.getTime(),other.getTime());
    }
}
