package team.free.freeway.exception;

public class StationNotFoundException extends CustomException {

    public StationNotFoundException() {
        super(FreeWayError.STATION_NOT_FOUND);
    }
}
