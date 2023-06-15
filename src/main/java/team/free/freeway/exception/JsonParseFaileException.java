package team.free.freeway.exception;

public class JsonParseFaileException extends CustomException {

    public JsonParseFaileException() {
        super(FreeWayError.JSON_PARSE_FAIL);
    }
}
