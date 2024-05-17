import java.util.Comparator;

public class JobFileComparator implements Comparator<JobFile> {
    public int compare(JobFile j1, JobFile j2) {

        return j1.getJobName().compareTo(j2.getJobName());
    }
}
