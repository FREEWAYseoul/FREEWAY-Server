package team.free.freeway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum FreeWayError {

    STATION_NOT_FOUND(LocalDateTime.now(), "해당 역이 존재하지 않습니다.");

    private final LocalDateTime timeStamp;
    private final String message;
    }
