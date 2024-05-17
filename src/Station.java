import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Station extends StationType {
    private String stationID;
    private int maxCapacity;
    private boolean multiFlag;
    private boolean fifoFlag;
    private List<StationType> stationTypeList;
    private List<JobFile> staticStationTypeContinue = new ArrayList<JobFile>();

    public Station() {}

    public Station(String stationID, int maxCapacity, boolean multiFlag, boolean fifoFlag, List<StationType> stationTypeList) {
        setStationID(stationID);
        setMaxCapacity(maxCapacity);
        setMultiFlag(multiFlag);
        setFifoFlag(fifoFlag);
        setStationTypeList(stationTypeList);
    }


    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
    public void setMaxCapacity(int maxCapacity) {
        // değerin integer şekilde olduğunu kontrol eden try catch.
        try {
            Integer.parseInt(String.valueOf(maxCapacity));
            this.maxCapacity = maxCapacity;
        } catch (NumberFormatException e) {

            System.out.println(stationID + "Enter a integer value.");
        }
    }
    public void setMultiFlag(boolean multiFlag) {
        this.multiFlag = multiFlag;
    }
    public void setFifoFlag(boolean fifoFlag) {
        this.fifoFlag = fifoFlag;
    }
    public void setStationTypeList(List<StationType> stationTypeList) {
        this.stationTypeList = stationTypeList;
    }

    public String getStationID() {return stationID;}
    public int getMaxCapacity() {return maxCapacity;}
    public boolean isMultiFlag() {return multiFlag;}
    public boolean isFifoFlag() {return fifoFlag;}
    public List<StationType> getStationTypeList() {return stationTypeList;}

    public void addStaticStation(JobFile jobFile) {
        if(staticStationTypeContinue.isEmpty()) {
            staticStationTypeContinue.add(jobFile);
        } else {
            int count = 0;
            for(int i = 0; i < staticStationTypeContinue.size(); i++) {
                if(staticStationTypeContinue.get(i) == jobFile) {
                    count++;
                }
            }
            if(count == 0) {
                staticStationTypeContinue.add(jobFile);
                Collections.sort(staticStationTypeContinue, new JobFileComparator());
            }
        }

    }
    public void removeStaticStation(JobFile jobFile) {
        staticStationTypeContinue.remove(jobFile);
    }

    public void printStaticList() {
        for(int i = 0; i < staticStationTypeContinue.size(); i++) {
            System.out.println(stationID+ " " +(i+1) + ". - " + staticStationTypeContinue.get(i).getJobName());
        }
    }
}
