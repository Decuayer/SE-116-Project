import java.util.List;

public class StationWork extends Station{
    private String stationWorkName;
    private List<Station> stationList;


    public StationWork() {

    }

    public StationWork(String stationWorkName, List<Station> stationList) {
        setStationWorkName(stationWorkName);
        setStationList(stationList);
    }

    public void setStationWorkName(String stationWorkName) {
        this.stationWorkName = stationWorkName;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public String getStationWorkName() {return stationWorkName;}

    public List<Station> getStationList() {return stationList;}

}
