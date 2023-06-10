package team.free.freeway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> customErrorHandler(CustomException exception) {
        FreeWayError freeWayError = exception.getFreeWayError();
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .timeStamp(freeWayError.getTimeStamp())
                .message(freeWayError.getMessage()).build());
    }
}
