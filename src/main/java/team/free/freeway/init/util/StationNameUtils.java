package team.free.freeway.init.util;

public class StationNameUtils {

    public static String getPureStationName(String stationName) {
        if (stationName.contains("(")) {
            int index = stationName.indexOf("(");
            stationName = stationName.substring(0, index);
        }

        return stationName;
    }
}
