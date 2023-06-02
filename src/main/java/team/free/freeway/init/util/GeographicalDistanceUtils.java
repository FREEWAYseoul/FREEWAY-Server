package team.free.freeway.init.util;

import org.springframework.stereotype.Component;

@Component
public class GeographicalDistanceUtils {

    /**
     * @Unit: Meter
     */
    public static double calculateDistance(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
        int earthRadius = 6371;

        double deltaLatitude = Math.toRadians(latitudeB - latitudeA);
        double deltaLongitude = Math.toRadians(longitudeB - longitudeA);
        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) +
                Math.cos(Math.toRadians(latitudeA)) * Math.cos(Math.toRadians(latitudeB)) *
                        Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance * 1000;
    }
}
