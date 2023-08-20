package team.free.freeway.service;

import team.free.freeway.controller.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> getNotificationsWithinLast90Days();
}
