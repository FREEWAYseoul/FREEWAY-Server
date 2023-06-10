package team.free.freeway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CustomException extends RuntimeException {

    private FreeWayError freeWayError;
}
