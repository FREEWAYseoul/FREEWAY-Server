package team.free.freeway.exception;

public class SubwayLineNotFound extends CustomException {

    public SubwayLineNotFound() {
        super(FreeWayError.LINE_NOT_FOUND);
    }
}
