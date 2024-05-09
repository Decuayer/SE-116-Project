import java.util.List;

public class StationWork extends Station{
    private String stationWorkName;
    private List<Station> stationList;
    private static List<StationType> stationTypeList;


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


    // stationTypeList içerisine stationList'ten aldığı getStationTypeList() değerlerini teker teker yazdıran ve aynısından varsa yazdırmayan fonksiyon yazılacak.
}
