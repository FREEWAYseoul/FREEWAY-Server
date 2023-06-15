package team.free.freeway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum FreeWayError {

    STATION_NOT_FOUND(LocalDateTime.now(), "해당 역이 존재하지 않습니다."),
    LINE_NOT_FOUND(LocalDateTime.now(), "해당 노선이 존재하지 않습니다."),
    JSON_PARSE_FAIL(LocalDateTime.now(), "서버 내부 오류 [JSON 파싱 오류]");

    private final LocalDateTime timeStamp;
    private final String message;
}
