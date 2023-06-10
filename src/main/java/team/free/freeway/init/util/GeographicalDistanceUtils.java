package team.free.freeway.init.util;

import org.springframework.stereotype.Component;
import team.free.freeway.domain.value.Coordinate;

@Component
public class GeographicalDistanceUtils {

    /**
     * @Unit: Meter
     */
    public static double calculateDistance(Coordinate coordinateA, Coordinate coordinateB) {
        int earthRadius = 6371;

        double latitudeA = Double.parseDouble(coordinateA.getLatitude());
        double latitudeB = Double.parseDouble(coordinateB.getLatitude());
        double deltaLatitude = Math.toRadians(latitudeB - latitudeA);
        double deltaLongitude = Math.toRadians(Double.parseDouble(coordinateB.getLongitude())
                - Double.parseDouble(coordinateA.getLongitude()));
        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) +
                Math.cos(Math.toRadians(latitudeB)) * Math.cos(Math.toRadians(latitudeB)) *
                        Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance * 1000;
    }
}
