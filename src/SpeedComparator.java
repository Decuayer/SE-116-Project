import java.util.Comparator;

public class SpeedComparator implements Comparator<StationType> {
    public int compare(StationType s1, StationType s2) {
        if(s1.getCalculatedSpeed()== s2.getCalculatedSpeed()) {
            return 0;
        }else if (s1.getCalculatedSpeed() > s2.getCalculatedSpeed()) {
            return -1;
        } else {
            return 1;
        }
    }
}
